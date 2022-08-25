package controlbiblioteca.appweb.controllers;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import controlbiblioteca.accesoadatos.EstudianteDAL;
import controlbiblioteca.accesoadatos.LibroDAL;
import controlbiblioteca.accesoadatos.PrestamoDAL;
import controlbiblioteca.appweb.utils.*;
import controlbiblioteca.entidadesdenegocio.Estudiante;
import controlbiblioteca.entidadesdenegocio.Libro;
import controlbiblioteca.entidadesdenegocio.Prestamo;

@WebServlet(name = "PrestamoServlet", urlPatterns = {"/Prestamo"})
public class PrestamoServlet extends HttpServlet {
    // <editor-fold defaultstate="collapsed" desc="Métodos para procesar las solicitudes get o post del Servlet">

    private Prestamo obtenerPrestamo(HttpServletRequest request) {
       String accion = Utilidad.getParameter(request, "accion", "index");
        Prestamo prestamo = new Prestamo();
        prestamo.setFecha(Utilidad.getParameter(request, "Fecha", ""));
        prestamo.setFechadevolucion(Utilidad.getParameter(request, "Fechadevolucion", ""));
        prestamo.setIdEstudiante(Integer.parseInt(Utilidad.getParameter(request, "idEstudiante", "0")));
        prestamo.setIdLibro(Byte.parseByte(Utilidad.getParameter(request, "idLibro", "0")));
      

        
        if (accion.equals("index")) {
            prestamo.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            prestamo.setTop_aux(prestamo.getTop_aux() == 0 ? Integer.MAX_VALUE : prestamo.getTop_aux());
        }
        
      
        return prestamo;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Prestamo prestamo = new  Prestamo();
            prestamo.setTop_aux(10);
            ArrayList<Prestamo> prestamos =  PrestamoDAL.buscarIncluirAEC(prestamo);
            request.setAttribute("prestamos", prestamos);
            request.setAttribute("top_aux", prestamo.getTop_aux());
            request.getRequestDispatcher("Views/Prestamo/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Prestamo prestamo = obtenerPrestamo(request);
            ArrayList< Prestamo> prestamos =  PrestamoDAL.buscarIncluirAEC(prestamo);
            request.setAttribute("prestamos", prestamo);
            request.setAttribute("top_aux", prestamo.getTop_aux());
            request.getRequestDispatcher("Views/Prestamo/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Prestamo/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Prestamo prestamo = obtenerPrestamo(request);
            int result = PrestamoDAL.crear(prestamo);
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
            Prestamo prestamo = obtenerPrestamo(request);
            Prestamo prestamo_result = PrestamoDAL.obtenerPorId(prestamo);
            if (prestamo_result.getId() > 0) {
               Estudiante estudiante = new Estudiante();
                estudiante.setId(prestamo_result.getIdEstudiante());
                prestamo_result.setEstudiante(EstudianteDAL.obtenerPorId(estudiante));
                request.setAttribute("Prestamo", prestamo_result);
            } else {
                Utilidad.enviarError("El Id:" + prestamo_result.getId() + " no existe en la tabla de Prestamo", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
   
    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Prestamo/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Prestamo prestamo = obtenerPrestamo(request);
            int result = PrestamoDAL.modificar(prestamo);
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
        request.getRequestDispatcher("Views/Prestamo/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Prestamo/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Prestamo prestamo = obtenerPrestamo(request);
            int result = PrestamoDAL.eliminar(prestamo);
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
        request.getRequestDispatcher("Views/Prestamo/login.jsp").forward(request, response);
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
}
        