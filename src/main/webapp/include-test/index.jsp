<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index page of displaying include action</title>
</head>
<body>
    <jsp:useBean id="calendar" scope="page" class="java.util.GregorianCalendar"/>
    Directive
    <%@ include file="time.jsp"%>
    <br/>
    Action tag
    <jsp:include page="time.jsp"></jsp:include>
</body>
</html>
