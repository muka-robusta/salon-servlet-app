package io.github.onetwostory.salon.controller.commands;

import io.github.onetwostory.salon.controller.Webpage;
import io.github.onetwostory.salon.service.AppointmentService;

import javax.servlet.http.HttpServletRequest;

public class DeleteAppointmentCommand implements Command {

    private final AppointmentService appointmentService;

    public DeleteAppointmentCommand(AppointmentService service) {
        this.appointmentService = service;
    }

    @Override
    public String execute(HttpServletRequest request) {

        final String appointmentIdString = request.getParameter("appointment_id");
        if (appointmentIdString == null)
            return Webpage.ADMIN_PROFILE;

        final Integer appointmentId = Integer.parseInt(appointmentIdString);
        appointmentService.deleteAppointmentById(appointmentId);

        return returnWithMessage("Successfully deleted", request);
    }

    private String returnWithMessage(String message, HttpServletRequest request) {
        request.setAttribute("serverMessage", message);
        return Webpage.ADMIN_PROFILE;
    }

}
