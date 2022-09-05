<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlbiblioteca.entidadesdenegocio.Libro"%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Crear ibro</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Crear Libro</h5>
            <form action="Libro" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">                
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtTitulo" type="text" name="titulo" required class="validate" maxlength="30">
                        <label for="txtTitulo">Titulo</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtAnyo" type="text" name="anyo" required class="validate" maxlength="30">
                        <label for="txtAnyo">Anyo</label>
                    </div>                
                    <div class="input-field col l4 s12"> 
                       <input  id="txtEdicion" type="text" name="edicion" required class="validate" maxlength="30">
                        <label for="txtEdicion">Edicion</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                              

                   
                </div>

                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Libro" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />   
        <script>
            function validarFormulario() {
                var result = true;
                var txtTitulo = document.getElementById("txtTitulo");
                var txtConfirm_Titulo = document.getElementById("txtConfirmTitulo_aux");
                var txtConfirm_Titulo_error = document.getElementById("txtConfirmTitulo_aux_error");
                var slAnyo = document.getElementById("slAnyo ");
                var slAnyo _error = document.getElementById("slAnyo _error");
                var slEdicion = document.getElementById("slEdicion ");
                var slEdicion _error = document.getElementById("slEdicion _error");
                var slLibro = document.getElementById("slLibro");
                var slLIbro_error = document.getElementById("slLibro_error");
                
                } else {
                    txtConfirm_password_error.innerHTML = "";
                }
                if (slTitulo.value == 0) {
                    slEstatus_error.innerHTML = "El  titulo  es obligatorio";
                    result = false;
                } else {
                    slEstatus_error.innerHTML = "";
                }
                if (slLibro.value == 0) {
                    slLibro_error.innerHTML = "El Anyo es obligatorio";
                    result = false;
                } else {
                    slLibro_error.innerHTML = "";
                }

                return result;
            }
        </script>
    </body>
</html>
