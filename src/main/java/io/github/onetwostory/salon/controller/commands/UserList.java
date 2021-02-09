package io.github.onetwostory.salon.controller.commands;


import io.github.onetwostory.salon.entity.User;
import io.github.onetwostory.salon.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class UserList implements Command {

    private static final String CONTROLLER_PAGE = "user_list.jsp";
    private final UserService userService;

    public UserList(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
//        request.setAttribute("users", users);
        return CONTROLLER_PAGE;
    }
}
