<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlbiblioteca.entidadesdenegocio.Prestamo"%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Crear Prestamo</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Crear Prestamo</h5>
            <form action="Prestamo" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">                
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtFecha" type="text" name="fecha" required class="validate" maxlength="30">
                        <label for="txtFecha">Fecha</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtFechadevolucion" type="text" name="fechadevolucion" required class="validate" maxlength="30">
                        <label for="txtFechadevolucion">Fechadevolucion</label>
                    </div>                
                    <div class="input-field col l4 s12">   
                              

                   
                </div>

                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Prestamo" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />   
        <script>
            function validarFormulario() {
                var result = true;
                var txtFecha = document.getElementById("txtFecha");
                var txtConfirm_Fecha = document.getElementById("txtConfirmFecha_aux");
                var txtConfirm_Fecha_error = document.getElementById("txtConfirmFecha_aux_error");
                var slFechadevolucion = document.getElementById("slFechadevolucion ");
                var slFechadevolucion _error = document.getElementById("slFechadevolucion _error");
                var slPrestamo = document.getElementById("slPrestamo");
                var slPrestamo_error = document.getElementById("slPrestamo_error");
                
                } else {
                    txtConfirm_password_error.innerHTML = "";
                }
                if (slFecha.value == 0) {
                    slEstatus_error.innerHTML = "La fecha  es obligatoria";
                    result = false;
                } else {
                    slEstatus_error.innerHTML = "";
                }
                if (slPrestamo.value == 0) {
                    slPrestamo_error.innerHTML = "La fechadevolucion es obligatoria";
                    result = false;
                } else {
                    slPrestamo_error.innerHTML = "";
                }

                return result;
            }
        </script>
    </body>
</html>

