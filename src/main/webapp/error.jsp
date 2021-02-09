<%--
  Created by IntelliJ IDEA.
  User: microchel
  Date: 2/1/21
  Time: 12:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error page</title>
</head>
<body>
    Request from ${pageContext.errorData.requestURI} is failed
    <br/>
    Servlet name: ${pageContext.errorData.servletName}
    <br/>
    Status code: ${pageContext.errorData.statusCode}
    <br/>
    Exception: ${pageContext.exception}
    <br/>
    Message from exception: ${pageContext.exception.message}
</body>
</html>
