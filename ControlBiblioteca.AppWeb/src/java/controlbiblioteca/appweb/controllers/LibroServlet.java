package controlbiblioteca.appweb.controllers;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import controlbiblioteca.accesoadatos.AutorDAL;
import controlbiblioteca.accesoadatos.EditorialDAL;
import controlbiblioteca.accesoadatos.CategoriaDAL;
import controlbiblioteca.accesoadatos.LibroDAL;
import controlbiblioteca.appweb.utils.*;
import controlbiblioteca.entidadesdenegocio.Autor;
import controlbiblioteca.entidadesdenegocio.Editorial;
import controlbiblioteca.entidadesdenegocio.Categoria;
import controlbiblioteca.entidadesdenegocio.Libro;

@WebServlet(name = "LibroServlet", urlPatterns = {"/Libro"})
public class LibroServlet extends HttpServlet {
    // <editor-fold defaultstate="collapsed" desc="Métodos para procesar las solicitudes get o post del Servlet">

    private Libro obtenerLibro(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Libro libro = new Libro();
        libro.setTitulo(Utilidad.getParameter(request, "Titulo", ""));
        libro.setAnyo(Utilidad.getParameter(request, "anyo", ""));
        libro.setEdicion(Utilidad.getParameter(request, "edicion", ""));
        libro.setIdAutor(Integer.parseInt(Utilidad.getParameter(request, "idAutor", "0")));
        libro.setIdEditorial(Byte.parseByte(Utilidad.getParameter(request, "Editorial", "0")));
       libro.setIdCategoria(Byte.parseByte(Utilidad.getParameter(request, "Categoria", "0")));

        
        if (accion.equals("index")) {
            libro.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            libro.setTop_aux(libro.getTop_aux() == 0 ? Integer.MAX_VALUE : libro.getTop_aux());
        }
        
      
        return libro;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro libro = new  Libro();
            libro.setTop_aux(10);
            ArrayList< Libro> libros =  LibroDAL.buscarIncluirAEC(libro);
            request.setAttribute("libros", libros);
            request.setAttribute("top_aux", libro.getTop_aux());
            request.getRequestDispatcher("Views/Libro/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
             Libro libro = obtenerLibro(request);
            ArrayList< Libro> libros =  LibroDAL.buscarIncluirAEC(libro);
            request.setAttribute("libros", libro);
            request.setAttribute("top_aux", libro.getTop_aux());
            request.getRequestDispatcher("Views/Libro/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Libro/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro usuario = obtenerLibro(request);
            int result = LibroDAL.crear(usuario);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro registrar un nuevo registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }

    }

    private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro libro = obtenerLibro(request);
            Libro libro_result = LibroDAL.obtenerPorId(libro);
            if (libro_result.getId() > 0) {
               Autor autor = new Autor();
                autor.setId(libro_result.getIdAutor());
                libro_result.setAutor(AutorDAL.obtenerPorId(autor));
                request.setAttribute("libro", libro_result);
            } else {
                Utilidad.enviarError("El Id:" + libro_result.getId() + " no existe en la tabla de Libro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Libro/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro libro = obtenerLibro(request);
            int result = LibroDAL.modificar(libro);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Libro/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Libro/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro libro = obtenerLibro(request);
            int result = LibroDAL.eliminar(libro);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro eliminar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionUser.cerrarSession(request);
        request.getRequestDispatcher("Views/Libro/login.jsp").forward(request, response);
    }

   

   

   
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos para procesar las peticiones Get y Post">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = Utilidad.getParameter(request, "accion", "index");
        if (accion.equals("login")) {
            request.setAttribute("accion", accion);
            doGetRequestLogin(request, response);
        } else { 
            SessionUser.authorize(request, response, () -> {
                switch (accion) {
                    case "index" -> {
                        request.setAttribute("accion", accion);
                        doGetRequestIndex(request, response);
                    }
                    case "create" -> {
                        request.setAttribute("accion", accion);
                        doGetRequestCreate(request, response);
                    }
                    case "edit" -> {
                        request.setAttribute("accion", accion);
                        doGetRequestEdit(request, response);
                    }
                    case "delete" -> {
                        request.setAttribute("accion", accion);
                        doGetRequestDelete(request, response);
                    }
                    case "details" -> {
                        request.setAttribute("accion", accion);
                        doGetRequestDetails(request, response);
                    }
                    
                    
                    default -> {
                            request.setAttribute("accion", accion);
                            doGetRequestIndex(request, response);
                    }
                }
            });
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = Utilidad.getParameter(request, "accion", "index");
 { 
            SessionUser.authorize(request, response, () -> {
                switch (accion) {
                    case "index":
                        request.setAttribute("accion", accion);
                        doPostRequestIndex(request, response);
                        break;
                    case "create":
                        request.setAttribute("accion", accion);
                        doPostRequestCreate(request, response);
                        break;
                    case "edit":
                        request.setAttribute("accion", accion);
                        doPostRequestEdit(request, response);
                        break;
                    case "delete":
                        request.setAttribute("accion", accion);
                        doPostRequestDelete(request, response);
                        break;
                   
                    default:
                        request.setAttribute("accion", accion);
                        doGetRequestIndex(request, response);
                }
            });
        }
    }
   
}