package io.github.onetwostory.salon.controller.commands;

import io.github.onetwostory.salon.controller.Webpage;
import io.github.onetwostory.salon.entity.AppointmentApplication;
import io.github.onetwostory.salon.entity.ServiceOption;
import io.github.onetwostory.salon.entity.User;
import io.github.onetwostory.salon.service.AppointmentApplicationService;
import io.github.onetwostory.salon.service.ServiceOptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

public class ApplyForAppointment implements Command {

    private static final String CONTROLLER_PAGE = "/client/apply_for_appointment.jsp";
    private static final Logger logger = LogManager.getLogger(ApplyForAppointment.class.getName());

    private final AppointmentApplicationService appointmentApplicationService;
    private final ServiceOptionService serviceOptionService;

    public ApplyForAppointment(AppointmentApplicationService service, ServiceOptionService serviceOptionService) {
        this.appointmentApplicationService = service;
        this.serviceOptionService = serviceOptionService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        logger.info("Creating appointment application");

        if(validateFieldsForNull(request))
            return CONTROLLER_PAGE;

        logger.info("Fetching data from request by user -> %s", request.getSession().getAttribute("user"));
        AppointmentApplication application = generateServiceOptionByFieldsFromRequest(request);

        if (application == null)
            return returnWithMessage("errorMessage",
                    "Error filling form. All date and time artifacts must be filled.<br> " +
                            "Maybe service option you entered doesn\'t exists?",
                    request);

        logger.info(String.format("Trying to save application -> %s", application));
        appointmentApplicationService.saveApplication(application);

        returnWithMessage("indexMessage", "Appointment application has registered", request);
        return Webpage.INDEX_PAGE;
    }

    private String returnWithMessage(String attributeMessageName, String attributeMessageValue, HttpServletRequest request) {
        request.setAttribute(attributeMessageName, attributeMessageValue);
        return CONTROLLER_PAGE;
    }

    private boolean validateFieldsForNull(HttpServletRequest request) {

        String[] validationFieldNames = {
                "service_option"
        };

        return Arrays.stream(validationFieldNames)
                .map(request::getParameter)
                .anyMatch(this::validateFieldForNull);
    }

    private AppointmentApplication generateServiceOptionByFieldsFromRequest(HttpServletRequest request) {
        final String serviceOptionName = request.getParameter("service_option");
        final Optional<ServiceOption> serviceOptionByName = serviceOptionService.findByName(serviceOptionName);

        // TODO: handle if service option is null
        logger.info("Mapping application by request");
        return serviceOptionByName.map(serviceOption -> new AppointmentApplication.Builder()
                .option(serviceOption)
                .description(request.getParameter("appointment_comments"))
                .appointmentDate(LocalDate.parse(request.getParameter("appointment_date")))
                .startFreeTime(LocalTime.parse(request.getParameter("appointment_time_start")))
                .endFreeTime(LocalTime.parse(request.getParameter("appointment_time_end")))
                .client((User) request.getSession().getAttribute("user"))
                .build()).orElse(null);

    }

    private boolean validateFieldForNull(String field) {
        if (field == null) return true;
        else if ("".equals(field)) return true;
        else return false;
    }
}
