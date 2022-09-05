<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlbiblioteca.entidadesdenegocio.Libro"%>
<% Libro libro = (Libro) request.getAttribute("libro");%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Libro</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Libro</h5>
            <form action="Libro" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=libro.getId()%>">  
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
                        <jsp:include page="/Views/EL/select.jsp">                           
                            <jsp:param name="id" value="<%=libro.getIdAutor() %>" />  
                            <jsp:param name="id" value="<%=libro.getIdEditorial() %>" /> 
                            <jsp:param name="id" value="<%=libro.getIdCategoria() %>" /> 
                        </jsp:include>  
                        <span id="slLibro_error" style="color:red" class="helper-text"></span>
                    </div>
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
                var slTitulo = document.getElementById("slTitulo");
                var slTitulo_error = document.getElementById("slTitulo_error");
                var slAnyo = document.getElementById("slAnyo ");
                var slAnyo _error = document.getElementById("slAnyo _error");
                var slEdicion = document.getElementById("slEdicion");
                var slEdicion_error = document.getElementById("slEdicion_error");
                var slAEC = document.getElementById("slAEC");
                var slAEC_error = document.getElementById("slAEC_error");
                if (slEstatus.value == 0) {
        
                    slTitulo_error.innerHTML = "El Titulo  es obligatorio";
                    result = false;
                } else {
                    slTitulo_error.innerHTML = "";
                }
                   slAnyo_error.innerHTML = "El Anyo  es obligatorio";
                    result = false;
                } else {
                    slAnyo_error.innerHTML = "";
                }
                   slEdicion_error.innerHTML = "La Edicion  es obligatoria";
                    result = false;
                } else {
                    slEdicion_error.innerHTML = "";
                }
                if (slAEC.value == 0) {
                    slAEC_error.innerHTML = "El AEC es obligatorio";
                    result = false;
                } else {
                    slAEC_error.innerHTML = "";
                }

                return result;
            }
        </script>
    </body>
</html>
