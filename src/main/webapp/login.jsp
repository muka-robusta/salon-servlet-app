<%--
  Created by IntelliJ IDEA.
  User: microchel
  Date: 1/28/21
  Time: 11:50 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<html>
<head>
    <title>Login</title>
</head>
<body>
    <a href="/redirect:/">Back to Menu</a>
    <br>
    <a href="login?lang=en">Language</a>
    <a href="login?lang=ua">Мова</a>

    <h1>${loginform_login}</h1>
    <form action="login" method="post">
        <input type="hidden" name="command" value="login">
        <input type="text" name="login_email" placeholder="${loginform_email}"/>
        <input type="password" name="login_password" placeholder="${loginform_password}"/>
        <br/>
        ${errorLoginPassMessage}
        <br/>
        <input type="submit" value="${loginform_confirm}"/>
        ${loginform_account_absence_question}
        <a href="register?lang=${lang}">${loginform_register}</a>
    </form>

</body>
</html>
