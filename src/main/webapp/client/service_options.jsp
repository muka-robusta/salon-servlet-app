<%--
  Created by IntelliJ IDEA.
  User: microchel
  Date: 1/29/21
  Time: 7:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns:jsp="http://java.sun.com/JSP/Page"
      xmlns:c="http://java.sun.com/jsp/jstl/core"
      version="2.0">
<head>
    <title>Title</title>
</head>
<body>
    <h1>Service Option List</h1>
    <c:forEach var="option" items="${serviceOptionList}">
        <tr>
            <td>${option}</td>
            <td>${option.description}</td>
            <td>${option.price}</td>
            <td><a href="">Choose</a></td>

        </tr>
    </c:forEach>
</body>
</html>
