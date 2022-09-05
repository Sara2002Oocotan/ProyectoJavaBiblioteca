<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlbiblioteca.entidadesdenegocio.Libro"%>
<% Libro libro = (Libro) request.getAttribute("libro");%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle de Libro</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle de Libro</h5>
             <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtTitulo" type="text" value="<%=libro.getTitulo()%>" disabled>
                        <label for="txtTitulo">Titulo</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtAnyo" type="text" value="<%=libro.getAnyo()%>" disabled>
                        <label for="txtAnyo">Anyo</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtEdicion" type="text" value="<%=libro.getEdicion()%>" disabled>
                        <label for="txtEdicion">Edicion</label>
                    </div> 
                

                <div class="row">
                    <div class="col l12 s12">
                         <a href="Libro?accion=edit&id=<%=libro.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>            
                        <a href="Libro" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />         
    </body>
</html>
