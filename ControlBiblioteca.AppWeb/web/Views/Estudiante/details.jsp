<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlbiblioteca.entidadesdenegocio.Estudiante"%>
<% Estudiante estudiante = (Estudiante) request.getAttribute("estudiante");%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle de Estudiante</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle de Estudiante</h5>
             <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" value="<%=estudiante.getNombre()%>" disabled>
                        <label for="txtNombre">Nombre</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtApellido" type="text" value="<%=estudiante.getApellido()%>" disabled>
                        <label for="txtApellido">Apellido</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtCarrera" type="text" value="<%=estudiante.getCarrera()%>" disabled>
                        <label for="txtCarrera">Carrera</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtCarnet" type="text" value="<%=estudiante.getCarnet()%>" disabled>
                        <label for="txtCarnet">Carnet</label>
                    </div> 
                          </div>
                    <div class="row">
                    <div class="col l12 s12">
                         <a href="Estudiante?accion=edit&id=<%=estudiante.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>            
                        <a href="Estudiante" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />         
    </body>
</html>
