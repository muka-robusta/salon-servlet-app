package io.github.onetwostory.salon.filter;

import io.github.onetwostory.salon.controller.Webpage;
import io.github.onetwostory.salon.entity.User;
import io.github.onetwostory.salon.entity.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(AdminFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);

        String requestedURI = request.getRequestURI();
        User user = null;
        if (requestedURI.contains("admin")) {
            logger.info("Trying to access admin");
            if ((user = (User) request.getSession().getAttribute("user")) != null) {
                if (user.getRole().equals(Role.ADMIN)) {
                    filterChain.doFilter(request, response);
                }
                else {
                    request.setAttribute("indexMessage", "You don't have an access to perform admins actions");
                    response.sendRedirect(Webpage.INDEX_PAGE);
                }
            } else {
                response.sendRedirect(Webpage.LOGIN_PAGE);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
