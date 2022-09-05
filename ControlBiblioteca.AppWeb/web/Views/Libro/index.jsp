<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlbiblioteca.entidadesdenegocio.Libro"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<Libro> libros = (ArrayList<Libro>) request.getAttribute("libros");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (libros == null) {
        libros = new ArrayList();
    } else if (libros.size() > numReg) {
        double divNumPage = (double) libros.size() / (double) numReg;
        numPage = (int) Math.ceil(divNumPage);
    }
    String strTop_aux = request.getParameter("top_aux");
    int top_aux = 10;
    if (strTop_aux != null && strTop_aux.trim().length() > 0) {
        top_aux = Integer.parseInt(strTop_aux);
    }
%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Buscar Libro</title>

    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Buscar Libro</h5>
            <form action="Libro" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    <div class="input-field col l6 s12">
                        <input  id="txtTitulo" type="text" name="Titulo">
                        <label for="txtTTitulo">Titulo</label>
                    </div>      
                    <div class="input-field col l6 s12">
                        <input  id="txtAnyo" type="text" name="Anyo">
                        <label for="txtAnyo">Anyo</label>
                    </div>     
                    
                     <div class="input-field col l6 s12">
                        <input  id="txtEdicion" type="text" name="Edicion">
                        <label for="txtAnyo">Edicion</label>
                    </div>
                    <div class="input-field col l3 s12">   
                        <jsp:include page="/Views/Shared/selectTop.jsp">
                            <jsp:param name="top_aux" value="<%=top_aux%>" />                        
                        </jsp:include>                        
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">search</i>Buscar</button>
                        <a href="Libro?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>

            <div class="row">
                <div class="col l12 s12">
                    <div style="overflow: auto">
                        <table class="paginationjs">
                            <thead>
                                <tr>
                                    <th>Titulo</th>    
                                     <th>Anyo</th> 
                                      <th>Edicion</th> 
                                </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (Libro libro : libros) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                %>
                                <tr data-page="<%= tempNumPage%>">
                                    <td><%=libro.getTitulo()%></td>       
                                    <td><%=libro.getAnyo()%></td>  
                                    <td><%=libro.getEdicion()%></td> 
                                    <td>
                                        <div style="display:flex">
                                            <a href="Libro?accion=edit&id=<%=libro.getId()%>" title="Modificar" class="waves-effect waves-light btn green">
                                                <i class="material-icons">edit</i>
                                            </a>
                                            <a href="Libro?accion=details&id=<%=libro.getId()%>" title="Ver" class="waves-effect waves-light btn blue">
                                                <i class="material-icons">description</i>
                                            </a>
                                            <a href="Libro?accion=delete&id=<%=libro.getId()%>" title="Eliminar" class="waves-effect waves-light btn red">
                                                <i class="material-icons">delete</i>
                                            </a>     
                                        </div>
                                    </td>                                   
                                </tr>
                                <%}%>                                                       
                            </tbody>
                        </table>
                    </div>                  
                </div>
            </div>
            <div class="row">
                <div class="col l12 s12">
                    <jsp:include page="/Views/Shared/paginacion.jsp">
                        <jsp:param name="numPage" value="<%= numPage%>" />                        
                    </jsp:include>
                </div>
            </div>
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />        
    </body>
</html>




