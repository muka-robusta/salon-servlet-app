<%--
  Created by IntelliJ IDEA.
  User: microchel
  Date: 1/29/21
  Time: 1:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Information page</title>
</head>
<body>
    <p>Context path: ${pageContext.request.contextPath}</p>
    <p>Host name: ${header["host"]}</p>
    <p>Type and content encoding: ${pageContext.response.contentType}</p>
    <p>Id session: ${pageContext.request.session.id}</p>
    <p>Servlet name: ${pageContext.servletConfig.servletName}</p>
</body>
</html>
