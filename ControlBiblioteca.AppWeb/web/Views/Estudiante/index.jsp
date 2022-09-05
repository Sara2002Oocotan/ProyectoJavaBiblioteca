<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlbiblioteca.entidadesdenegocio.Estudiante"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<Estudiante> estudiantes = (ArrayList<Estudiante>) request.getAttribute("estudiantes");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (estudiantes == null) {
        estudiantes = new ArrayList();
    } else if (estudiantes.size() > numReg) {
        double divNumPage = (double) estudiantes.size() / (double) numReg;
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
        <title>Buscar Estudiante</title>

    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Buscar Estudiante</h5>
            <form action="Estudiante" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    <div class="input-field col l6 s12">
                        <input  id="txtNombre" type="text" name="nombre">
                        <label for="txtNombre">Nombre</label>
                    </div>      
                    <div class="input-field col l6 s12">
                        <input  id="txtApellido" type="text" name="apellido">
                        <label for="txtApellido">Apellido</label>
                    </div>                    
                    <div class="input-field col l6 s12">
                        <input  id="txtCarrera" type="text" name="carrera">
                        <label for="txtCarrera">Carrera</label>
                    </div>         
                    <div class="input-field col l6 s12">
                        <input  id="txtCarnet" type="text" name="carnet">
                        <label for="txtCarnet">Carnet</label>
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
                        <a href="Estudiante?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>

            <div class="row">
                <div class="col l12 s12">
                    <div style="overflow: auto">
                        <table class="paginationjs">
                            <thead>
                                <tr>
                                    <th>Nombre</th>    
                                     <th>Apellido</th> 
                                      <th>Carrera</th> 
                                    <th>Carnet</th>
                                </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (Estudiante estudiante : estudiantes) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                %>
                                <tr data-page="<%= tempNumPage%>">
                                    <td><%=estudiante.getNombre()%></td> 
                                   <td><%=estudiante.getApellido()%></td>
                                     <td><%=estudiante.getCarrera()%></td>  
                                       <td><%=estudiante.getCarnet()%></td>  
                                    <td>
                                        <div style="display:flex">
                                            <a href="Estudiante?accion=edit&id=<%=estudiante.getId()%>" title="Modificar" class="waves-effect waves-light btn green">
                                                <i class="material-icons">edit</i>
                                            </a>
                                            <a href="Estudiante?accion=details&id=<%=estudiante.getId()%>" title="Ver" class="waves-effect waves-light btn blue">
                                                <i class="material-icons">description</i>
                                            </a>
                                            <a href="Estudiante?accion=delete&id=<%=estudiante.getId()%>" title="Eliminar" class="waves-effect waves-light btn red">
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


