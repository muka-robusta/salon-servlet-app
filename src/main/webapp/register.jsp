<%--
  Created by IntelliJ IDEA.
  User: microchel
  Date: 1/28/21
  Time: 1:35 PM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <title>Register</title>
</head>
<body>
    <a href="register?lang=en">Language</a>
    <a href="register?lang=ua">Мова</a>
    <h1>${register_register}</h1>

    <form action="register" method="get">
        <input type="text" name="register_first_name" placeholder="${register_first_name}"/>
        <input type="text" name="register_last_name" placeholder="${register_last_name}"><br/>
        <input type="text" name="register_email" placeholder="${register_email}"/>
        <input type="password" name="register_password" placeholder="${register_password}"/>
        <input type="submit" value="${register_confirm}"/>
    </form>

</body>
</html>
