package controlbiblioteca.accesoadatos;

import java.util.*;
import java.sql.*;
import controlbiblioteca.entidadesdenegocio.*;
import java.time.LocalDate;

public class LibroDAL {
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
        return "l.Id, l.IdAutor,l.IdEditorial, l.IdCategoria, l.Titulo, l.Anyo, l.Edicion,";
    }
    
    private static String obtenerSelect(Libro pLibro) {
        String sql;
        sql = "SELECT ";
        if (pLibro.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
             sql += "TOP " + pLibro.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Libro l");
        return sql;
    } 
    
    private static String agregarOrderBy(Libro pLibro) {
        String sql = " ORDER BY l.Id DESC";
        if (pLibro.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pLibro.getTop_aux() + " ";
        }
        return sql;
    }
    
  
    
    public static int crear(Libro pLibro) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
                sql = "INSERT INTO Usuario(IdRol,Nombre,Apellido,Login,Pass,Estatus,FechaRegistro) VALUES(?,?,?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pLibro.getIdAutor());
                    ps.setInt(1, pLibro.getIdEditorial());
                    ps.setInt(1, pLibro.getIdCategoria());
                    ps.setString(2, pLibro.getTitulo());
                    ps.setString(3, pLibro.getAnyo()); 
                    ps.setString(4, pLibro.getEdicion());
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
    
    public static int modificar(Libro pLibro) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {                
                sql = "UPDATE Usuario SET IdAutor=?,IdEditorial=?, IdCategoria=? Titulo=?, Anyo=?, Edicion=?, WHERE Id=?";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pLibro.getIdAutor());
                    ps.setInt(1, pLibro.getIdEditorial());
                    ps.setInt(1, pLibro.getIdCategoria());
                    ps.setString(2, pLibro.getTitulo());  
                    ps.setString(3, pLibro.getAnyo());
                    ps.setString(4, pLibro.getEdicion());
                    ps.setInt(6, pLibro.getId());
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
    
    public static int eliminar(Libro pLibro) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "DELETE FROM Libro WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pLibro.getId());
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
    
    static int asignarDatosResultSet(Libro pLibro, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pLibro.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pLibro.setIdAutor(pResultSet.getInt(pIndex)); 
        pIndex++;
        pLibro.setIdEditorial(pResultSet.getInt(pIndex)); 
        pIndex++;
        pLibro.setIdCategoria(pResultSet.getInt(pIndex)); 
        pIndex++;
        pLibro.setTitulo(pResultSet.getString(pIndex)); 
        pIndex++;
        pLibro.setAnyo(pResultSet.getString(pIndex)); 
        pIndex++;
        pLibro.setEdicion(pResultSet.getString(pIndex)); 
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Libro> pLibros) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { 
            while (resultSet.next()) {
                Libro libro = new Libro();
                asignarDatosResultSet(libro, resultSet, 0);
                pLibros.add(libro);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    private static void obtenerDatosIncluirAEC(PreparedStatement pPS, ArrayList<Libro> pLibros) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            HashMap<Integer, Autor> autorMap = new HashMap(); 
            HashMap<Integer, Editorial> editorialMap = new HashMap(); 
            HashMap<Integer, Categoria> categoriaMap = new HashMap(); 
            while (resultSet.next()) {
                Libro libro = new Libro();
                int index = asignarDatosResultSet(libro, resultSet, 0);
                if (autorMap.containsKey(libro.getIdAutor()) == false) {
                    Autor autor = new Autor();
                    AutorDAL.asignarDatosResultSet(autor, resultSet, index);
                    autorMap.put(autor.getId(), autor); 
                    libro.setAutor(autor); 
                } else {
                    libro.setAutor(autorMap.get(libro.getIdAutor())); 
                }
                
                if (editorialMap.containsKey(libro.getIdEditorial()) == false) {
                    Editorial editorial = new Editorial();
                    EditorialDAL.asignarDatosResultSet(editorial, resultSet, index);
                    editorialMap.put(editorial.getId(), editorial); 
                    libro.setEditorial(editorial); 
                } else {
                    libro.setEditorial(editorialMap.get(libro.getIdEditorial())); 
                }
                
                if (categoriaMap.containsKey(libro.getIdCategoria()) == false) {
                    Categoria categoria = new Categoria();
                    CategoriaDAL.asignarDatosResultSet(categoria, resultSet, index);
                    categoriaMap.put(categoria.getId(), categoria); 
                    libro.setCategoria(categoria); 
                } else {
                    libro.setCategoria(categoriaMap.get(libro.getIdCategoria())); 
                }
                
                pLibros.add(libro); 
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex; 
        }
    }
    
    public static Libro obtenerPorId(Libro pLibro) throws Exception {
        Libro libro = new Libro();
        ArrayList<Libro> libros = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pLibro);
            sql += " WHERE l.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pLibro.getId());
                obtenerDatos(ps, libros);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (libros.size() > 0) {
            libro = libros.get(0);
        }
        return libro;
    }
    
    public static ArrayList<Libro> obtenerTodos() throws Exception {
        ArrayList<Libro> libros;
        libros = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Libro()); 
            sql += agregarOrderBy(new Libro());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, libros);
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return libros;
    }
    
    static void querySelect(Libro pLibro, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pLibro.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" l.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pLibro.getId());
            }
        }

        if (pLibro.getIdAutor() > 0) {
            pUtilQuery.AgregarNumWhere(" u.IdRol=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pLibro.getIdAutor());
            }
        }
        
          if (pLibro.getIdEditorial() > 0) {
            pUtilQuery.AgregarNumWhere(" u.IdRol=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pLibro.getIdEditorial());
            }
        }
            if (pLibro.getIdCategoria() > 0) {
            pUtilQuery.AgregarNumWhere(" u.IdRol=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pLibro.getIdCategoria());
            }
        }
        
        if (pLibro.getTitulo() != null && pLibro.getTitulo().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" l.Nombre LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pLibro.getTitulo() + "%");
            }
        }

        if (pLibro.getAnyo() != null && pLibro.getAnyo().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" u.Apellido LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pLibro.getAnyo() + "%");
            }
        }

        if (pLibro.getEdicion() != null && pLibro.getEdicion().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" u.Login=? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), pLibro.getEdicion());
            }
        }

    }
    
    public static ArrayList<Libro> buscar(Libro pLibro) throws Exception {
        ArrayList<Libro> libros = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pLibro);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pLibro, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pLibro);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pLibro, utilQuery);
                obtenerDatos(ps, libros);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        return libros;
    }
    
 
    
  
    
    public static ArrayList<Libro> buscarIncluirAEC(Libro pLibro) throws Exception {
        ArrayList<Libro> libros = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT ";
            if (pLibro.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
                sql += "TOP " + pLibro.getTop_aux() + " "; 
            }
            sql += obtenerCampos();
            sql += ",";
            sql += AutorDAL.obtenerCampos();
             sql += EditorialDAL.obtenerCampos();
              sql += CategoriaDAL.obtenerCampos();
            sql += " FROM Libro l";
            sql += " JOIN AEC a on (l.IdAutor=, l.IdEditorial=, l.IdCategoria=l.Id)";
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pLibro, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pLibro);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pLibro, utilQuery);
                obtenerDatosIncluirAEC(ps, libros);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return libros;
    }
}

