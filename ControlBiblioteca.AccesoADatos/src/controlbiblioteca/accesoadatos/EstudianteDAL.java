package controlbiblioteca.accesoadatos;

import java.util.*;
import java.sql.*;
import controlbiblioteca.entidadesdenegocio.*;

public class EstudianteDAL {
    static String obtenerCampos() {
        return "e.Id, e.Nombre, e.Apellido, e.Carrera, e.Carnet";
    }
    
    private static String obtenerSelect(Estudiante pEstudiante) {
        String sql;
        sql = "SELECT ";
        if (pEstudiante.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {            
            sql += "TOP " + pEstudiante.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Rol r");
        return sql;
    }
    
    private static String agregarOrderBy(Estudiante pEstudiante) {
        String sql = " ORDER BY e.Id DESC";
        if (pEstudiante.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pEstudiante.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Estudiante pEstudiante) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "INSERT INTO Estudiante(Nombre) VALUES(?)";
            sql = "INSERT INTO Estudiante(Apellido) VALUES(?)";
            sql = "INSERT INTO Estudiante(Carrera) VALUES(?)";
            sql = "INSERT INTO Estudiante(Carnet) VALUES(?)";
            
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pEstudiante.getNombre());
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
    
    public static int modificar(Estudiante pEstudiante) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Estudiante SET Nombre=? WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pEstudiante.getNombre());
                ps.setInt(2, pEstudiante.getId());
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
    
    public static int eliminar(Estudiante pEstudiante) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM Estudiante WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pEstudiante.getId());
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
    
    static int asignarDatosResultSet(Estudiante pEstudiante, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pEstudiante.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pEstudiante.setNombre(pResultSet.getString(pIndex));
         pEstudiante.setApellido(pResultSet.getString(pIndex));
          pEstudiante.setCarrera(pResultSet.getString(pIndex));
           pEstudiante.setCarnet(pResultSet.getString(pIndex));
        return pIndex;
        
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Estudiante> pEstudiantes) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Estudiante estudiante = new Estudiante(); 
                asignarDatosResultSet(estudiante, resultSet, 0);
                pEstudiantes.add(estudiante);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Estudiante obtenerPorId(Estudiante pEstudiante) throws Exception {
        Estudiante estudiantes = new Estudiante();
        ArrayList<Estudiante> estudiante = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { 
            String sql = obtenerSelect(pEstudiante);
            sql += " WHERE e.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pEstudiante.getId());
                obtenerDatos(ps, estudiante);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (estudiante.size() > 0) {
            estudiantes = estudiante.get(0);
        }
        return estudiantes;
    }
    
    public static ArrayList<Estudiante> obtenerTodos() throws Exception {
        ArrayList<Estudiante> estudiantes;
        estudiantes = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Estudiante());
            sql += agregarOrderBy(new Estudiante());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, estudiantes);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        return estudiantes;
    }
    
    static void querySelect(Estudiante pEstudiante, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pEstudiante.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" e.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pEstudiante.getId()); 
            }
        }

        if (pEstudiante.getNombre() != null && pEstudiante.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEstudiante.getNombre() + "%"); 
            }
        }
    }
    
    public static ArrayList<Estudiante> buscar(Estudiante pEstudiante) throws Exception {
        ArrayList<Estudiante> estudiantes = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pEstudiante);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pEstudiante, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pEstudiante);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pEstudiante, utilQuery);
                obtenerDatos(ps, estudiantes);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return estudiantes;
    }
}
