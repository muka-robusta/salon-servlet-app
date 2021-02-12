package io.github.onetwostory.salon.controller.commands;

import io.github.onetwostory.salon.controller.Webpage;
import io.github.onetwostory.salon.entity.ServiceOption;
import io.github.onetwostory.salon.service.ServiceOptionService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;

public class AddServiceOptionCommand implements Command{

    private static final String CONTROLLER_PAGE = "add_service.jsp";
    private final ServiceOptionService serviceOptionService;

    public AddServiceOptionCommand(ServiceOptionService service) {
        this.serviceOptionService = service;
    }

    @Override
    public String execute(HttpServletRequest request) {

        if (validateAllFieldsForNull(request))
            return CONTROLLER_PAGE;

        if (validateAnyFieldForNull(request))
            return putErrorMessageAndReturnController("All fields must be filled", request);

        final ServiceOption serviceOption = generateServiceOptionByFields(request);
        serviceOptionService.save(serviceOption);

        return Webpage.INDEX_PAGE;
    }

    private ServiceOption generateServiceOptionByFields(HttpServletRequest request) {
        return new ServiceOption.Builder()
                .name(request.getParameter("service_option_name"))
                .description(request.getParameter("service_option_description"))
                .price(new BigDecimal(request.getParameter("service_option_price")))
                .build();
    }

    private String putErrorMessageAndReturnController(String errorMessage, HttpServletRequest request) {
        request.setAttribute("errorMessage", errorMessage);
        return CONTROLLER_PAGE;
    }

    private boolean validateAnyFieldForNull(HttpServletRequest request) {
        String[] fieldNamesToValidate = {
                "service_option_name",
                "service_option_description",
                "service_option_price"
        };

        return Arrays.stream(fieldNamesToValidate)
                .map(request::getParameter)
                .anyMatch(this::validateFieldForNull);
    }

    private boolean validateAllFieldsForNull(HttpServletRequest request) {
        String[] fieldNamesToValidate = {
                "service_option_name",
                "service_option_description",
                "service_option_price"
        };

        return Arrays.stream(fieldNamesToValidate)
                .map(request::getParameter)
                .allMatch(this::validateFieldForNull);
    }

    private boolean validateFieldForNull(String fieldValue) {
        if (fieldValue == null) return true;
        else if ("".equals(fieldValue)) return true;
        else return false;
    }
}
