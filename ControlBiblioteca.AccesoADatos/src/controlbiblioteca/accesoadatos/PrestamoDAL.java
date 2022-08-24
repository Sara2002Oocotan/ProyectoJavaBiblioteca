package controlbiblioteca.accesoadatos;

import java.util.*;
import java.sql.*;
import controlbiblioteca.entidadesdenegocio.*;
import java.time.LocalDate;

public class PrestamoDAL {
    public static String encriptarMD5(String txt) throws Exception {
        try {
            StringBuffer sb;
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(txt.getBytes());
            sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ex) {
            throw ex;
        }
    }
    
    static String obtenerCampos() {
        return "p.Id, p.IdEstudiante,p.IdLibro, p.Fecha, p.FechaDevolucion,";
    }
    
    private static String obtenerSelect(Prestamo pPrestamo) {
        String sql;
        sql = "SELECT ";
        if (pPrestamo.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
             sql += "TOP " + pPrestamo.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Prestamo p");
        return sql;
    } 
    
    private static String agregarOrderBy(Prestamo pPrestamo) {
        String sql = " ORDER BY l.Id DESC";
        if (pPrestamo.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pPrestamo.getTop_aux() + " ";
        }
        return sql;
    }
    
  
    
    public static int crear(Prestamo pPrestamo) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
                sql = "INSERT INTO Prestamo(IdRol,Nombre,Apellido,Login,Pass,Estatus,FechaRegistro) VALUES(?,?,?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pPrestamo.getIdEstudiante());
                    ps.setInt(1, pPrestamo.getIdLibro());
                    ps.setString(2, pPrestamo.getFecha());
                    ps.setString(3, pPrestamo.getFechadevolucion()); 
                    ps.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            }
            catch (SQLException ex) {
                throw ex;
            }
        return result;
    }
    
    public static int modificar(Prestamo pPrestamo) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {                
                sql = "UPDATE Prestamo SET IdEstudiante=?, IdLibro=? Fecha=?, Fechadevolucion=?, WHERE Id=?";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pPrestamo.getIdEstudiante());
                    ps.setInt(1, pPrestamo.getIdLibro());
                    ps.setString(2, pPrestamo.getFecha());  
                    ps.setString(3, pPrestamo.getFechadevolucion());
                    ps.setInt(6, pPrestamo.getId());
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            } 
            catch (SQLException ex) {
                throw ex;
            }
        return result;
    }
    
    public static int eliminar(Prestamo pPrestamo) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "DELETE FROM Prestamo WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pPrestamo.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    
        static int asignarDatosResultSet(Prestamo pPrestamo, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pPrestamo.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pPrestamo.setIdEstudiante(pResultSet.getInt(pIndex)); 
        pIndex++;
        pPrestamo.setIdLibro(pResultSet.getInt(pIndex)); 
        pIndex++;
        pPrestamo.setFecha(pResultSet.getString(pIndex)); 
        pIndex++;
        pPrestamo.setFechadevolucion(pResultSet.getString(pIndex)); 
        pIndex++;
         
        return pIndex;

} 
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList< Prestamo> pPrestamos) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { 
            while (resultSet.next()) {
               Prestamo  prestamo = new  Prestamo();
                asignarDatosResultSet( prestamo, resultSet, 0);
                pPrestamos.add( prestamo);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    private static void obtenerDatosIncluirEL(PreparedStatement pPS, ArrayList<Prestamo> pPrestamos) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            HashMap<Integer, Estudiante> estudianteMap = new HashMap(); 
            HashMap<Integer, Libro> libroMap = new HashMap();  
            while (resultSet.next()) {
                Prestamo prestamo = new Prestamo();
                int index = asignarDatosResultSet(prestamo, resultSet, 0);
                if (estudianteMap.containsKey(prestamo.getIdEstudiante()) == false) {
                    Estudiante estudiante = new Estudiante();
                   EstudianteDAL.asignarDatosResultSet(estudiante, resultSet, index);
                    estudianteMap.put(estudiante.getId(), estudiante); 
                   prestamo.setEstudiante(estudiante); 
                } else {
                     prestamo.setEstudiante(estudianteMap.get(prestamo.getIdEstudiante())); 
                }
                
                if (libroMap.containsKey(prestamo.getIdLibro()) == false) {
                    Libro libro = new Libro();
                    LibroDAL.asignarDatosResultSet(libro, resultSet, index);
                    libroMap.put(libro.getId(), libro); 
                    prestamo.setLibro(libro); 
                } else {
                     prestamo.setLibro(libroMap.get(prestamo.getIdLibro())); 
                }
                
                pPrestamos.add(prestamo); 
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex; 
        }
    }
    
    public static  Prestamo obtenerPorId( Prestamo pPrestamo) throws Exception {
        Prestamo prestamo = new  Prestamo();
        ArrayList< Prestamo> prestamos = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pPrestamo);
            sql += " WHERE p.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pPrestamo.getId());
                obtenerDatos(ps, prestamos);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (prestamos.size() > 0) {
            prestamo = prestamos.get(0);
        }
        return prestamo;
    }
    
    public static ArrayList<Prestamo> obtenerTodos() throws Exception {
        ArrayList< Prestamo> prestamos;
        prestamos = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new  Prestamo()); 
            sql += agregarOrderBy(new Prestamo());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps,prestamos);
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return prestamos;
    }
    
    static void querySelect( Prestamo pPrestamo, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pPrestamo.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" p.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pPrestamo.getId());
            }
        }

        if (pPrestamo.getIdEstudiante() > 0) {
            pUtilQuery.AgregarNumWhere(" u.IdEstudiante=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pPrestamo.getIdEstudiante());
            }
        }
        
          if (pPrestamo.getIdLibro() > 0) {
            pUtilQuery.AgregarNumWhere(" p.IdLibro=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pPrestamo.getIdLibro());
            }
        
        }
        
        if (pPrestamo.getFecha() != null && pPrestamo.getFecha().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" p.Fecha LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pPrestamo.getFecha() + "%");
            }
        }

        if (pPrestamo.getFechadevolucion() != null && pPrestamo.getFechadevolucion().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" p.Fechadevolucion LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pPrestamo.getFechadevolucion() + "%");
            }
        }

    }
    
    public static ArrayList<Prestamo> buscar(Prestamo pPrestamo) throws Exception {
        ArrayList<Prestamo> prestamos = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pPrestamo);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pPrestamo, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pPrestamo);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pPrestamo, utilQuery);
                obtenerDatos(ps, prestamos);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        return prestamos;
    }
    
 
    
  
    
    public static ArrayList<Prestamo> buscarIncluirAEC(Prestamo pPrestamo) throws Exception {
        ArrayList<Prestamo> prestamos = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT ";
            if (pPrestamo.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
                sql += "TOP " + pPrestamo.getTop_aux() + " "; 
            }
            sql += obtenerCampos();
            sql += ",";
            sql += EstudianteDAL.obtenerCampos();
             sql += LibroDAL.obtenerCampos();
            sql += " FROM Prestamo l";
            sql += " JOIN AEC a on (p.IdEstudiante=, p.IdLibro=,p.Id)";
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pPrestamo, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pPrestamo);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pPrestamo, utilQuery);
                obtenerDatosIncluirEL(ps, prestamos);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return prestamos;
    }
}
