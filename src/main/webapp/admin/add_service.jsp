<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Service Options</title>
</head>
<body>
    <h2>Add Service Options</h2>
    <form action="create_service_option" method="post">
        <p>
            Service Option name:
            <input type="text" name="service_option_name"><br>
        </p>
        <p>
            Service Option description: <br>
            <textarea type="text" name="service_option_description"></textarea><br>
        </p>
        <p>
            Price:
            <input type="text" name="service_option_price">
        </p>
        <input type="submit">
        ${errorMessage}
    </form>
</body>
</html>
