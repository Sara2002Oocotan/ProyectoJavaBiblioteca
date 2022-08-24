package controlbiblioteca.accesoadatos;

import java.util.*;
import java.sql.*;
import controlbiblioteca.entidadesdenegocio.*;

public class EditorialDAL {
    static String obtenerCampos() {
        return "e.Id, e.Nombre, e.Pais" ;
    }
    
    private static String obtenerSelect(Editorial pEditorial) {
        String sql;
        sql = "SELECT ";
        if (pEditorial.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {            
            sql += "TOP " + pEditorial.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Editorial e");
        return sql;
    }
    
    private static String agregarOrderBy(Editorial pEditorial) {
        String sql = " ORDER BY e.Id DESC";
        if (pEditorial.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pEditorial.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Editorial pEditorial) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "INSERT INTO Editorial(Nombre) VALUES(?)";
            sql = "INSERT INTO Pais(Pais) VALUES(?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pEditorial.getNombre());
                ps.setString(1, pEditorial.getPais());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    
    public static int modificar(Editorial pEditorial) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Editorial SET Nombre=? WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pEditorial.getNombre());
                 ps.setString(1, pEditorial.getPais());
                ps.setInt(2, pEditorial.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    
    public static int eliminar(Editorial pEditorial) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM Editorial WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pEditorial.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    } 
    
    static int asignarDatosResultSet(Editorial pEditorial, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pEditorial.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pEditorial.setNombre(pResultSet.getString(pIndex));
         pEditorial.setPais(pResultSet.getString(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Editorial> pEditoriales) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Editorial editorial = new Editorial(); 
                asignarDatosResultSet(editorial, resultSet, 0);
                pEditoriales.add(editorial);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Editorial obtenerPorId(Editorial pEditorial) throws Exception {
        Editorial editorial = new Editorial();
        ArrayList<Editorial> editoriales= new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { 
            String sql = obtenerSelect(pEditorial);
            sql += " WHERE e.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pEditorial.getId());
                obtenerDatos(ps, editoriales);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (editoriales.size() > 0) {
            editorial= editoriales.get(0);
        }
        return editorial;
    }
    
    public static ArrayList<Editorial> obtenerTodos() throws Exception {
        ArrayList<Editorial> editoriales;
        editoriales= new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Editorial());
            sql += agregarOrderBy(new Editorial());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, editoriales);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        return editoriales;
    }
    
    static void querySelect(Editorial pEditorial, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pEditorial.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" e.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pEditorial.getId()); 
            }
        }

        if (pEditorial.getNombre() != null && pEditorial.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEditorial.getNombre() + "%"); 
            }
        }
    }
    
    public static ArrayList<Editorial> buscar(Editorial pEditorial) throws Exception {
        ArrayList<Editorial> editoriales = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pEditorial);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pEditorial, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pEditorial);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pEditorial, utilQuery);
                obtenerDatos(ps, editoriales);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return editoriales;
    }
}

