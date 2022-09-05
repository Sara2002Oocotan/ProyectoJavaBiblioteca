<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlbiblioteca.entidadesdenegocio.Estudiante"%>
<% Estudiante estudiante = (Estudiante) request.getAttribute("estudiante");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Estudiante</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Estudiante</h5>
            <form action="Estudiante" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">   
                <input type="hidden" name="id" value="<%=estudiante.getId()%>">   
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" value="<%=estudiante.getNombre()%>" required class="validate" maxlength="30">
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
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Estudiante" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>
