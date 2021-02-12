package io.github.onetwostory.salon.controller.commands;

import io.github.onetwostory.salon.controller.Webpage;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return "/redirect:" + Webpage.INDEX_PAGE;
    }
}
