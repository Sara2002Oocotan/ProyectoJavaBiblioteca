package controlbiblioteca.accesoadatos;

import java.util.*;
import java.sql.*;
import controlbiblioteca.entidadesdenegocio.*;

public class AutorDAL {
    static String obtenerCampos() {
        return "a.Id, a.Nombre, a.Pais" ;
    }
    
    private static String obtenerSelect(Autor pAutor) {
        String sql;
        sql = "SELECT ";
        if (pAutor.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {            
            sql += "TOP " + pAutor.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Autor a");
        return sql;
    }
    
    private static String agregarOrderBy(Autor pAutor) {
        String sql = " ORDER BY a.Id DESC";
        if (pAutor.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pAutor.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Autor pAutor) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "INSERT INTO Nombre(Nombre) VALUES(?)";
            sql = "INSERT INTO Pais(Pais) VALUES(?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pAutor.getNombre());
                ps.setString(1, pAutor.getPais());
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
    
    public static int modificar(Autor pAutor) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Autor SET Nombre=? WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pAutor.getNombre());
                 ps.setString(1, pAutor.getPais());
                ps.setInt(2, pAutor.getId());
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
    
    public static int eliminar(Autor pAutor) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM Autor WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pAutor.getId());
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
    
    static int asignarDatosResultSet(Autor pAutor, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pAutor.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pAutor.setNombre(pResultSet.getString(pIndex));
         pAutor.setPais(pResultSet.getString(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Autor> pAutor) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Autor autor = new Autor(); 
                asignarDatosResultSet(autor, resultSet, 0);
                pAutor.add(autor);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Autor obtenerPorId(Autor pAutor) throws Exception {
        Autor autor = new Autor();
        ArrayList<Autor> autores= new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { 
            String sql = obtenerSelect(pAutor);
            sql += " WHERE a.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pAutor.getId());
                obtenerDatos(ps, autores);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (autores.size() > 0) {
            autor= autores.get(0);
        }
        return autor;
    }
    
    public static ArrayList<Autor> obtenerTodos() throws Exception {
        ArrayList<Autor> autores;
        autores= new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Autor());
            sql += agregarOrderBy(new Autor());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, autores);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        return autores;
    }
    
    static void querySelect(Autor pAutor, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pAutor.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" a.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pAutor.getId()); 
            }
        }

        if (pAutor.getNombre() != null && pAutor.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" a.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pAutor.getNombre() + "%"); 
            }
        }
    }
    
    public static ArrayList<Autor> buscar(Autor pAutor) throws Exception {
        ArrayList<Autor> autores = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pAutor);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pAutor, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pAutor);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pAutor, utilQuery);
                obtenerDatos(ps, autores);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return autores;
    }
}


