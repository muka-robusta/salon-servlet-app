package io.github.onetwostory.salon.controller.commands;

import io.github.onetwostory.salon.entity.Appointment;
import io.github.onetwostory.salon.entity.AppointmentApplication;
import io.github.onetwostory.salon.service.AppointmentApplicationService;
import io.github.onetwostory.salon.service.AppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AppointmentSetCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AppointmentSetCommand.class.getName());
    private static final String CONTROLLER_PAGE = "administrator/appointment_set.jsp";

    private AppointmentApplicationService appointmentApplicationService;
    private final AppointmentService appointmentService;


    public AppointmentSetCommand(AppointmentService appointmentService, AppointmentApplicationService appointmentApplicationService) {
        this.appointmentService = appointmentService;
        this.appointmentApplicationService = appointmentApplicationService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        logger.info("Admin page: Handle client applications");

        final List<AppointmentApplication> appointmentApplicationList = appointmentApplicationService.getAll();
        request.setAttribute("appointmentApplicationList", appointmentApplicationList);

        return CONTROLLER_PAGE;
    }
}
