<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlbiblioteca.entidadesdenegocio.Editorial"%>
<% Editorial editorial = (Editorial) request.getAttribute("editorial");%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle de Editorial</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle de Editorial</h5>
             <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" value="<%=editorial.getNombre()%>" disabled>
                        <label for="txtNombre">Nombre</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtPais" type="text" value="<%=editorial.getPais()%>" disabled>
                        <label for="txtPais">Pais</label>
                    </div> 
                  </div>

                <div class="row">
                    <div class="col l12 s12">
                         <a href="Editorial?accion=edit&id=<%=editorial.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>            
                        <a href="Editorial" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />         
    </body>
</html>
