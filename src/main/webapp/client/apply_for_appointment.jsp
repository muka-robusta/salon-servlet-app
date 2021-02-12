<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Apply for Appointment</title>
</head>
<body>
    <h2>Apply for appointment</h2>
    <form action="apply" method="post">
        <p>
            Enter service option, you want to apply:
            <input type="text" placeholder="Service Option" name="service_option" required><br/>
        </p>
        <p>
            Appointment date:
            <input type="date" name="appointment_date" min="2021-01-01" required><br/>
        </p>
        <p>
            Time from:
            <input type="time" name="appointment_time_start" min="08:00" max="18:00" required>
            To:
            <input type="time" name="appointment_time_end" min="08:00" max="18:00" required><br/>
        </p>
        <p>
            Comment and expectations type here:<br/>
            <textarea name="appointment_comments" cols="30" rows="10"></textarea>
        </p>
        <p>
            <input type="submit" value="Apply for appointment">
        </p>
    </form>
    <p>${errorMessage}</p>
    <p><a href="/redirect:/">Return to menu</a></p>
</body>
</html>
