<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlbiblioteca.appweb.utils.*"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>

<% if (SessionUser.isAuth(request) == false) {
         response.sendRedirect("Usuario?accion=login");
    }
%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Home</title>
<!DOCTYPE html>
          
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Home</title>

    </head>
    <body style="background-image: url(Views/img/imagen.jpeg);background-size: 100% 100%;background-attachment: fixed">
    
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <jsp:include page="/Views/Shared/footerBody.jsp" />      
    </body>
</html>
