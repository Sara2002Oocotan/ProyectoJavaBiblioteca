package controlbiblioteca.appweb.controllers;


import controlbiblioteca.accesoadatos.EditorialDAL;
import controlbiblioteca.appweb.utils.*;
import controlbiblioteca.entidadesdenegocio.Editorial;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "EditorialServlet", urlPatterns = {"/Editorial"})
public class EditorialServlet extends HttpServlet {
    
    private Editorial obtenerEditorial(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Editorial editorial = new Editorial();
        if (accion.equals("create") == false) { editorial.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));}

        editorial.setNombre(Utilidad.getParameter(request, "nombre", ""));
        if (accion.equals("index")) {
            editorial.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            editorial.setTop_aux(editorial.getTop_aux() == 0 ? Integer.MAX_VALUE : editorial.getTop_aux());
        }
        
        editorial.setPais(Utilidad.getParameter(request, "pais", ""));
        if (accion.equals("index")) {
            editorial.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            editorial.setTop_aux(editorial.getTop_aux() == 0 ? Integer.MAX_VALUE : editorial.getTop_aux());
        }
        return editorial;
    }
    
    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Editorial editorial = new  Editorial();
            editorial.setTop_aux(10);
            ArrayList< Editorial> editoriales =  EditorialDAL.buscar(editorial);
            request.setAttribute("editoriales", editoriales);
            request.setAttribute("top_aux", editorial.getTop_aux());             
            request.getRequestDispatcher("Views/Rol/index.jsp").forward(request, response);
        } catch (Exception ex) { Utilidad.enviarError(ex.getMessage(), request, response);}
    }
    
    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
             Editorial editorial = obtenerEditorial(request);
            ArrayList<Editorial> editoriales = EditorialDAL.buscar(editorial);
            request.setAttribute("editorial", editoriales);
            request.setAttribute("top_aux", editorial.getTop_aux());
            request.getRequestDispatcher("Views/Rol/index.jsp").forward(request, response);
        } catch (Exception ex) { 
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    
    
    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Editorial/create.jsp").forward(request, response);
    }
    
    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Editorial editorial = obtenerEditorial(request);
            int result = EditorialDAL.crear(editorial);
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
            Editorial editorial = obtenerEditorial(request);
            Editorial editorial_result = EditorialDAL.obtenerPorId(editorial);
            if (editorial_result.getId() > 0) {
                request.setAttribute("editorial", editorial_result);
            } else {
                Utilidad.enviarError("El Id:" + editorial.getId() + " no existe en la tabla de Editorial", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Editorial/edit.jsp").forward(request, response);
    }
    
    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Editorial editorial = obtenerEditorial(request);
            int result = EditorialDAL.modificar(editorial);
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
        request.getRequestDispatcher("Views/Editorial/details.jsp").forward(request, response);
    }
    
    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Editorial/delete.jsp").forward(request, response);
    }
    
    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
           Editorial editorial = obtenerEditorial(request);
            int result = EditorialDAL.eliminar(editorial);
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
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
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
    //</editor-fold>
   
}
