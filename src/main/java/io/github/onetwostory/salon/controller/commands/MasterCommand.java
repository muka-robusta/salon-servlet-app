package io.github.onetwostory.salon.controller.commands;

import io.github.onetwostory.salon.entity.User;
import io.github.onetwostory.salon.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MasterCommand implements Command{

    private static final String CONTROLLER_PAGE = "masters.jsp";

    private final UserService userService;

    public MasterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        final List<User> masters = userService.findMasters();
        request.setAttribute("masterList", masters);

        return CONTROLLER_PAGE;
    }
}
