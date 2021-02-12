package io.github.onetwostory.salon.controller.commands;

import io.github.onetwostory.salon.controller.Webpage;
import io.github.onetwostory.salon.entity.Appointment;
import io.github.onetwostory.salon.entity.AppointmentApplication;
import io.github.onetwostory.salon.entity.ServiceOption;
import io.github.onetwostory.salon.entity.User;
import io.github.onetwostory.salon.entity.enums.Status;
import io.github.onetwostory.salon.service.AppointmentApplicationService;
import io.github.onetwostory.salon.service.AppointmentService;
import io.github.onetwostory.salon.service.ServiceOptionService;
import io.github.onetwostory.salon.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentSetCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AppointmentSetCommand.class.getName());
    private static final String CONTROLLER_PAGE = "appointment_set.jsp";

    private final AppointmentApplicationService appointmentApplicationService;
    private final AppointmentService appointmentService;
    private final ServiceOptionService serviceOptionService;
    private final UserService userService;

    public AppointmentSetCommand(AppointmentService appointmentService,
                                 AppointmentApplicationService appointmentApplicationService,
                                 ServiceOptionService serviceOptionService,
                                 UserService userService) {
        this.appointmentService = appointmentService;
        this.appointmentApplicationService = appointmentApplicationService;
        this.serviceOptionService = serviceOptionService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        logger.info("Admin page: Handle client applications");

        final List<ServiceOption> allServiceOptions = serviceOptionService.findAllServiceOptions();
        request.setAttribute("serviceOptions", allServiceOptions);

        final List<AppointmentApplication> appointmentApplicationList = appointmentApplicationService.getAll();
        request.setAttribute("appointmentApplicationList", appointmentApplicationList);

        final List<User> clientsFromApplicationsSet = new ArrayList<>(appointmentApplicationList.stream()
                .map(application -> application.getClient())
                .distinct()
                .collect(Collectors.toList()));
        request.setAttribute("clientList", clientsFromApplicationsSet);

        final List<User> masters = userService.findMasters();
        request.setAttribute("masterList", masters);

        if (validateFieldsForNull(request))
            return CONTROLLER_PAGE;

        final String serviceOptionFieldValueString = request.getParameter("service_option");
        final Optional<ServiceOption> serviceOptionFieldValue = allServiceOptions.stream()
                .filter(serviceOption -> serviceOptionFieldValueString.equals(serviceOption.getName()))
                .findFirst();

        final String masterFieldValueString = request.getParameter("master");
        final Integer masterId = Integer.parseInt(masterFieldValueString.split(" ")[0]);
        final Optional<User> masterFieldValue = masters.stream()
                .filter(master -> master.getIdentifier().equals(masterId))
                .findFirst();

        final String clientFieldValueString = request.getParameter("client");
        final Integer clientId = Integer.parseInt(clientFieldValueString.split(" ")[0]);
        final Optional<User> clientFieldValue = clientsFromApplicationsSet.stream()
                .filter(client -> client.getIdentifier().equals(clientId))
                .findFirst();

        if (!serviceOptionFieldValue.isPresent() || !masterFieldValue.isPresent() || !clientFieldValue.isPresent())
            return setReturnWithError("Set Master, Client and Service Option properly", request);

        final Appointment appointmentByFields = getAppointmentByFields(request,
                serviceOptionFieldValue.get(),
                masterFieldValue.get(),
                clientFieldValue.get());

        appointmentService.save(appointmentByFields);

        return setReturnWithMessage("Done! Appointment set", request);
    }

    private boolean validateFieldsForNull(HttpServletRequest request) {
        String[] fieldNamesToValidate = {
                "service_option",
                "appointment_date",
                "appointment_time",
                "master",
                "client"
        };

        return Arrays.stream(fieldNamesToValidate)
                .map(request::getParameter)
                .anyMatch(this::validateFieldForNull);
    }

    private boolean validateFieldForNull(String field) {
        logger.info("Field -> " + field);
        if (field == null) return true;
        else if ("".equals(field)) return true;
        else return false;
    }

    private Appointment getAppointmentByFields(HttpServletRequest request, ServiceOption serviceOption, User master, User client) {

        List<ServiceOption> serviceOptionList = new ArrayList<>();
        serviceOptionList.add(serviceOption);

        return new Appointment.Builder()
                .master(master)
                .serviceOptionList(serviceOptionList)
                .client(client)
                .date(LocalDate.parse(request.getParameter("appointment_date")))
                .time(LocalTime.parse(request.getParameter("appointment_time")))
                .status(Status.APPROVED)
                .build();

    }

    private String setReturnWithMessage(String message, HttpServletRequest request) {
        request.setAttribute("messageFromServer", message);
        return CONTROLLER_PAGE;
    }

    private String setReturnWithError(String errorMessageForUser, HttpServletRequest request) {
        logger.info("Error in filling form");
        request.setAttribute("errorMessage", errorMessageForUser);
        return CONTROLLER_PAGE;
    }
}
