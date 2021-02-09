package io.github.onetwostory.salon.controller.commands;

import javax.servlet.http.HttpServletRequest;

public class Info implements Command {

    private static final String CONTROLLER_PAGE = "info.jsp";

    @Override
    public String execute(HttpServletRequest request) {

        request.setAttribute("method", request.getMethod());
        request.setAttribute("request_url", request.getRequestURL());
        request.setAttribute("protocol", request.getProtocol());
        request.setAttribute("remote_address", request.getRemoteAddr());
        request.setAttribute("scheme", request.getScheme());

        return CONTROLLER_PAGE;
    }
}
