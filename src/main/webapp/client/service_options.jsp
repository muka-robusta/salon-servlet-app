<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html xmlns:jsp="http://java.sun.com/JSP/Page"
      xmlns:c="http://java.sun.com/jsp/jstl/core"
      version="2.0">
<head>
    <title>Title</title>
</head>
<body>
    <h1>Service Option List</h1>
    <table border="1px solid" cellspacing="0" cellpadding="5px">
        <tr>
            <td>Service Option</td>
            <td>Description</td>
            <td>Price ₴</td>
        </tr>
        <c:forEach var="option" items="${serviceOptionList}">
            <tr>
                <td>${option.name}</td>
                <td>${option.description}</td>
                <td>${option.price}</td>
            </tr>
        </c:forEach>
    </table>
    <p>
        <a href="/client/apply">Apply for an appointment</a>
    </p>
</body>
</html>
