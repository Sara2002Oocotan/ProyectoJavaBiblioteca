<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlbiblioteca.entidadesdenegocio.Prestamo"%>
<%@page import="controlbiblioteca.accesoadatos.PrestamoDAL"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<Prestamo> prestamos = PrestamoDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slPrestamo" name="idPrestamo">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Prestamo prestamo : prestamos) {%>
    <option <%=(id == prestamo.getId()) ? "selected" : ""%>  value="<%=prestamo.getId()%>"><%= prestamo.getFecha()%><%= prestamo.getFechadevolucion()%></option>
    <%}%>
</select>
<label for="idPrestamo">Prestamo</label>
