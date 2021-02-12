package io.github.onetwostory.salon.controller.commands;

import io.github.onetwostory.salon.controller.Webpage;
import io.github.onetwostory.salon.service.AppointmentApplicationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteApplicationCommand implements Command{

    private static final Logger logger = LogManager.getLogger(DeleteApplicationCommand.class.getName());
    private final AppointmentApplicationService appointmentApplicationService;

    public DeleteApplicationCommand(AppointmentApplicationService service) {
        appointmentApplicationService = service;
    }

    @Override
    public String execute(HttpServletRequest request) {

        String applicationIdString = request.getParameter("application_id");
        logger.info(String.format("Deleting by id -> %s", applicationIdString));

        if (applicationIdString == null)
            return Webpage.HANDLE_APPLICATIONS;

        final int applicationId = Integer.parseInt(applicationIdString);
        appointmentApplicationService.deleteApplication(applicationId);

        return Webpage.HANDLE_APPLICATIONS;
    }


}
