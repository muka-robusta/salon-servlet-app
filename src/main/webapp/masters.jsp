<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html xmlns:jsp="http://java.sun.com/JSP/Page"
      xmlns:c="http://java.sun.com/jsp/jstl/core"
      version="2.0">
<head>
    <title>Masters</title>
</head>
<body>
    <h1>Master list</h1>
    <table border="1px solid" cellspacing="0" cellpadding="5px">
        <tr>
            <td>First Name</td>
            <td>Last Name</td>
        </tr>
        <c:forEach var="master" items="${masterList}">
            <tr>
                <td>${master.firstName}</td>
                <td>${master.lastName}</td>
            </tr>
        </c:forEach>
    </table>
    <p>You can write in comments to appointment application, which master you want to be handled</p>
</body>
</html>
