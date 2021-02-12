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
    <table border="1px solid" cellspacing="0" cellpadding="5px">
        <tr>
            <td>Appointment id</td>
            <td>Service option</td>
            <td>date</td>
            <td>time start</td>
            <td>time end</td>
            <td>client first name</td>
            <td>client last name</td>
            <td>client id</td>
        </tr>
        <c:forEach var="appointmentApplication" items="${appointmentApplicationList}">
            <tr>
                <td><c:out value="${appointmentApplication.identifier}"/></td>
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
                <td>
                    ${appointmentApplication.client.firstName}
                </td>
                <td>
                    ${appointmentApplication.client.lastName}
                </td>
                <td>
                    ${appointmentApplication.client.identifier}
                </td>

            </tr>

        </c:forEach>
    </table>
    <p>
        <form action="delete_application" method="post">
            Delete:
            <input list="applications" name="application_id" required>
            <datalist id="applications">
                <c:forEach var="application" items="${appointmentApplicationList}">
                    <option value="${application.identifier}"></option>
                </c:forEach>
            </datalist>
            <input type="submit" value="Delete application">
        </form>
    </p>
    <hr>
    <h2>Register appointment</h2>
    <form action="handle" method="post">
        Service options:
        <input list="service_options" name="service_option" required>
        <datalist id="service_options">
            <c:forEach var="serviceOption" items="${serviceOptions}">
                <option value="${serviceOption.name}"></option>
            </c:forEach>
        </datalist>
        Appointment date:
        <input type="date" name="appointment_date" min="2021-01-01" required><br/>
        Appointment time:
        <input type="time" name="appointment_time" min="08:00" max="18:00" required>
        <br>
        Master:
        <input list="master_list" name="master" required>
        <datalist id="master_list">
            <c:forEach var="master" items="${masterList}">
                <option value="${master.identifier} - ${master.firstName} ${master.lastName}"></option>
            </c:forEach>
        </datalist>
        <br>
        Appointment for client:
        <input list="client_list" name="client" required>
        <datalist id="client_list">
            <c:forEach var="client" items="${clientList}">
                <option value="${client.identifier} - ${client.firstName} ${client.lastName}"></option>
            </c:forEach>
        </datalist>

        <input type="submit" value="Set the appointment">
    </form>
    <p>${messageFromServer}</p>
    <p>
        <a href="/redirect:/">Menu</a>
    </p>
</body>
</html>
