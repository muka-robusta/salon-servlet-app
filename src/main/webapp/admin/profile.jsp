<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
    <h1>Profile</h1>
    <table border="1px solid" cellspacing="0" cellpadding="5px">
        <tr>
            <td>Id</td>
            <td>${user.identifier}</td>
        </tr>
        <tr>
            <td>First Name</td>
            <td>${user.firstName}</td>
        </tr>
        <tr>
            <td>Last Name</td>
            <td>${user.lastName}</td>
        </tr>
        <tr>
            <td>email</td>
            <td>${user.email}</td>
        </tr>
        <tr>
            <td>Role</td>
            <td>${user.role}</td>
        </tr>
    </table>

    <p>
        <a href="/redirect:/">Menu</a><br>
        <a href="handle">Handle applications</a><br>
        <a href="/redirect:/logout">Logout</a><br>
        <a href="create_service_option">Add Service Option</a>
    </p>
    <h2>Appointments:</h2>
    <table border="1px solid" cellspacing="0" cellpadding="5px">
        <tr>
            <td>Appointment Id</td>
            <td>Service option</td>
            <td>Appointment date</td>
            <td>Appointment time</td>
            <td>Appointment master</td>
            <td>Client</td>
        </tr>
        <c:forEach var="appointment" items="${appointments}">
            <tr>
                <td>${appointment.identifier}</td>
                <td>
                    <c:forEach var="service_option" items="${appointment.serviceOptionList}">
                        ${service_option.name}<br>
                    </c:forEach>
                </td>
                <td>${appointment.appointmentDate}</td>
                <td>${appointment.appointmentTime}</td>
                <td>
                        ${appointment.master.firstName} ${appointment.master.lastName}
                </td>
                <td>
                    ${appointment.client.firstName} ${appointment.client.lastName}
                </td>
            </tr>
        </c:forEach>
    </table>

    <p>
    <form action="delete_appointment" method="post">
        Delete Appointment:
        <input list="appointments" name="appointment_id" required>
        <datalist id="appointments">
            <c:forEach var="appointment" items="${appointments}">
                <option value="${appointment.identifier}"></option>
            </c:forEach>
        </datalist>
        <input type="submit" value="Delete appointment">
    </form>
    </p>
    <p>${serverMessage}</p>
</body>
</html>
