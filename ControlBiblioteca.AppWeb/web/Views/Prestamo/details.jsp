<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlbiblioteca.entidadesdenegocio.Prestamo"%>
<% Prestamo prestamo = (Prestamo) request.getAttribute("prestamo");%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle de Usuario</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle de Prestamo</h5>
             <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtFecha" type="text" value="<%=prestamo.getFecha()%>" disabled>
                        <label for="txtFecha">Fecha</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtFechadevolucion" type="text" value="<%=prestamo.getFechadevolucion()%>" disabled>
                        <label for="txtFechadevolucion">Fechadevolucion</label>
                    </div> 
                  </div>

                <div class="row">
                    <div class="col l12 s12">
                         <a href="Prestamo?accion=edit&id=<%=prestamo.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>            
                        <a href="Prestamo" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />         
    </body>
</html>

