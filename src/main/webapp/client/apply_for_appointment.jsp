<%--
  Created by IntelliJ IDEA.
  User: microchel
  Date: 1/29/21
  Time: 7:57 PM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <title>Apply for Appointment</title>
</head>
<body>
    <h2>Apply for appointment</h2>
    <form action="apply" method="get">
        Enter service option, you want to apply:
        <input type="text" placeholder="Service Option" name="service_option"><br/>
        Appointment date:
        <input type="date" name="appointment_date" min="2021-01-01"><br/>
        Time from:
        <input type="time" name="appointment_time_start" min="08:00" max="18:00" required>
        To:
        <input type="time" name="appointment_time_end" min="08:00" max="18:00" required><br/>
        Comment and expectations type here:<br/>
        <textarea name="appointment_comments" cols="30" rows="10"></textarea>
        <br>
        <input type="submit" value="Apply for appointment">
    </form>
</body>
</html>
