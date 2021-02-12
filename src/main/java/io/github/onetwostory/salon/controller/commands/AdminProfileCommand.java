package io.github.onetwostory.salon.controller.commands;

import io.github.onetwostory.salon.entity.Appointment;
import io.github.onetwostory.salon.entity.User;
import io.github.onetwostory.salon.service.AppointmentService;
import io.github.onetwostory.salon.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AdminProfileCommand implements Command{

    private static final Logger logger = LogManager.getLogger(ProfileCommand.class.getName());
    private static final String CONTROLLER_PAGE = "profile.jsp";

    private final UserService userService;
    private final AppointmentService appointmentService;

    public AdminProfileCommand(UserService userService, AppointmentService appointmentService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        final List<Appointment> appointmentsOfClient = appointmentService.findAll();

        request.setAttribute("user", user);
        request.setAttribute("appointments", appointmentsOfClient);

        return CONTROLLER_PAGE;
    }

    private String returnWithError(String errorMessage, HttpServletRequest request) {
        request.setAttribute("errorMessage", errorMessage);
        return CONTROLLER_PAGE;
    }
}
