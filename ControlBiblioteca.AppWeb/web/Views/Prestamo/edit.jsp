<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlbiblioteca.entidadesdenegocio.Prestamo"%>
<% Prestamo prestamo = (Prestamo) request.getAttribute("prestamo");%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Prestamo</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Prestamo</h5>
            <form action="Prestamo" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=prestamo.getId()%>">  
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtFecha" type="text" name="fecha" value="<%=prestamo.getFecha()%>" required class="validate" maxlength="30">
                        <label for="txtFecha">Fecha</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtFechadevolucion" type="text" name="fechadevolucion" value="<%=prestamo.getFechadevolucion()%>" required class="validate" maxlength="30">
                        <label for="txtFechadevolucion">Fechadevolucion</label>
                    </div> 
                    
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/EL/select.jsp">                           
                            <jsp:param name="id" value="<%=prestamo.getIdLibro() %>" />  
                            <jsp:param name="id" value="<%=prestamo.getIdEstudiante() %>" /> 
                        </jsp:include>  
                        <span id="slRol_error" style="color:red" class="helper-text"></span>
                    </div>
                </div>

                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Usuario" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />   
        <script>
            function validarFormulario() {
                var result = true;
                var slFecha = document.getElementById("slFecha");
                var slFecha_error = document.getElementById("slFecha_error");
                var slFechadevolucion = document.getElementById("slFechadevolucion");
                var slFechadevolucion_error = document.getElementById("slFechadevolucion_error");
                var slEL = document.getElementById("slEL");
                var slEL_error = document.getElementById("slEL_error");
                if (slEstatus.value == 0) {
                    slEstatus_error.innerHTML = "La fecha  es obligatorio";
                    result = false;
                } else {
                    slEstatus_error.innerHTML = "";
                }
                if (slEL.value == 0) {
                    slEL_error.innerHTML = "El EL es obligatorio";
                    result = false;
                } else {
                    slEL_error.innerHTML = "";
                }

                return result;
            }
        </script>
    </body>
</html>

