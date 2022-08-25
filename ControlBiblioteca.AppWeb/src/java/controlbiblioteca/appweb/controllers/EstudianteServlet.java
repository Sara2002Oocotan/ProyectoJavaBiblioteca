package controlbiblioteca.appweb.controllers;


import controlbiblioteca.accesoadatos.EstudianteDAL;
import controlbiblioteca.appweb.utils.*;
import controlbiblioteca.entidadesdenegocio.Estudiante;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "EstudianteServlet", urlPatterns = {"/Estudiante"})
public class EstudianteServlet extends HttpServlet {
    
    private Estudiante obtenerEstudiante(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Estudiante estudiante = new Estudiante();
        if (accion.equals("create") == false) { estudiante.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));}

        estudiante.setNombre(Utilidad.getParameter(request, "nombre", ""));
        if (accion.equals("index")) {
            estudiante.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            estudiante.setTop_aux(estudiante.getTop_aux() == 0 ? Integer.MAX_VALUE : estudiante.getTop_aux());
        }
        estudiante.setApellido(Utilidad.getParameter(request, "apellido", ""));
        if (accion.equals("index")) {
            estudiante.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            estudiante.setTop_aux(estudiante.getTop_aux() == 0 ? Integer.MAX_VALUE : estudiante.getTop_aux());
        }
        estudiante.setCarrera(Utilidad.getParameter(request, "carrera", ""));
        if (accion.equals("index")) {
            estudiante.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            estudiante.setTop_aux(estudiante.getTop_aux() == 0 ? Integer.MAX_VALUE : estudiante.getTop_aux());
        }
        estudiante.setCarnet(Utilidad.getParameter(request, "carnet", ""));
        if (accion.equals("index")) {
            estudiante.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            estudiante.setTop_aux(estudiante.getTop_aux() == 0 ? Integer.MAX_VALUE : estudiante.getTop_aux());
        }
        
        return estudiante;
    }
    
    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Estudiante estudiante = new  Estudiante();
            estudiante.setTop_aux(10);
            ArrayList<Estudiante> estudiantes =  EstudianteDAL.buscar(estudiante);
            request.setAttribute("autores", estudiantes);
            request.setAttribute("top_aux", estudiante.getTop_aux());             
            request.getRequestDispatcher("Views/Estudiante/index.jsp").forward(request, response);
        } catch (Exception ex) { Utilidad.enviarError(ex.getMessage(), request, response);}
    }
    
    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Estudiante estudiante = obtenerEstudiante(request);
            ArrayList<Estudiante> estudiantes = EstudianteDAL.buscar(estudiante);
            request.setAttribute("estudiante", estudiantes);
            request.setAttribute("top_aux", estudiante.getTop_aux());
            request.getRequestDispatcher("Views/Estudiante/index.jsp").forward(request, response);
        } catch (Exception ex) { 
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    
    
    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Estudiante/create.jsp").forward(request, response);
    }
    
    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Estudiante estudiante = obtenerEstudiante(request);
            int result = EstudianteDAL.crear(estudiante);
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
       
            Estudiante estudiante = obtenerEstudiante(request);
            Estudiante estudiante_result = EstudianteDAL.obtenerPorId(estudiante);
            if (estudiante_result.getId() > 0) {
                request.setAttribute("autor", estudiante_result);
            } else {
                Utilidad.enviarError("El Id:" + estudiante.getId() + " no existe en la tabla de Autor", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Autor/edit.jsp").forward(request, response);
    }
    
    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Estudiante estudiante = obtenerEstudiante(request);
            int result = EstudianteDAL.modificar(estudiante);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Estudiante/details.jsp").forward(request, response);
    }
    
    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Estudiante/delete.jsp").forward(request, response);
    }
    
    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
           Estudiante estudiante = obtenerEstudiante(request);
            int result =EstudianteDAL.eliminar(estudiante);
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
   
      
    // <editor-fold defaultstate="collapsed" desc="Métodos para procesar las peticiones Get y Post">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doGetRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doGetRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doGetRequestDelete(request, response);
                    break;
                case "details":
                    request.setAttribute("accion", accion);
                    doGetRequestDetails(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }
}