<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html xmlns:jsp="http://java.sun.com/JSP/Page"
      xmlns:c="http://java.sun.com/jsp/jstl/core"
      version="2.0">
<head>
    <title>Appointments</title>
</head>
<body>
    <h2>Appointments application set</h2>
    <table border="1px solid black">
        <tr>
            <td>Service option</td>
            <td>date</td>
            <td>time start</td>
            <td>time end</td>
        </tr>
        <c:forEach var="appointmentApplication" items="${appointmentApplicationList}">
            <tr>
                <td><c:out value="${appointmentApplication.option.name}"/></td>
                <td>
                    <c:out value="${appointmentApplication.appointmentDate}"/>.
                </td>
                <td>
                    <c:out value="${appointmentApplication.startFreeTime}"/>:
                </td>
                <td>
                    <c:out value="${appointmentApplication.endFreeTime}"/>:
                </td>
            </tr>

        </c:forEach>
    </table>
    <h2>Register appointment</h2>
    <form action="handle" method="post">
        Service options:
        <input type="text" placeholder="Service Option" name="service_option"><br/>
        Appointment date:
        <input type="date" name="appointment_date" min="2021-01-01"><br/>
        Appointment time:
        <input type="time" name="appointment_time_start" min="08:00" max="18:00" required>
        <br>


        <input type="submit" value="Apply for appointment">
    </form>

</body>
</html>
