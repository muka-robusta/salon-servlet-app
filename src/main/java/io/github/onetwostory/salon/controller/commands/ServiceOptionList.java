package io.github.onetwostory.salon.controller.commands;

import io.github.onetwostory.salon.entity.ServiceOption;
import io.github.onetwostory.salon.service.ServiceOptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class ServiceOptionList implements Command{

    private static final String CONTROLLER_PAGE = "/client/service_options.jsp";
    private static final Logger logger = LogManager.getLogger(ServiceOptionList.class.getName());
    private final ServiceOptionService serviceOptionService;

    public ServiceOptionList(ServiceOptionService service) {
        serviceOptionService = service;
    }

    @Override
    public String execute(HttpServletRequest request) {

        List<ServiceOption> allServiceOptions = serviceOptionService.findAllServiceOptions();
        request.setAttribute("serviceOptionList", allServiceOptions);

        return CONTROLLER_PAGE;
    }
}
