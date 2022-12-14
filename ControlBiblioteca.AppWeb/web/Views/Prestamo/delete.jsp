<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlbiblioteca.entidadesdenegocio.Prestamo"%>
<% Prestamo prestamo = (Prestamo) request.getAttribute("prestamo");%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Eliminar Prestamo</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Eliminar Prestamo</h5>
            <form action="Prestamo" method="post">  
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=prestamo.getId()%>">  
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtFecha" type="text" value="<%=prestamo.getFecha()%>" disabled>
                        <label for="txtFechae">Fecha</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtFechadevolucion" type="text" value="<%=prestamo.getFechadevolucion()%>" disabled>
                        <label for="txtFechadevolucion">Fechadevolucion</label>
                    </div> 
              
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">delete</i>Eliminar</button>
                        <a href="Usuario" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />         
    </body>
</html>
