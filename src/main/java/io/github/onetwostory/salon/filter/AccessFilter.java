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

public class AccessFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(AccessFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        User user = null;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", -1);

        String requestedURI = httpServletRequest.getRequestURI();
        logger.info("FILTER -> " + requestedURI);
        if (requestedURI.contains("client")) {
            logger.info("Trying to access /client/*");
            if ((user = (User) httpServletRequest.getSession().getAttribute("user")) != null) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } else {
                httpServletResponse.sendRedirect(Webpage.LOGIN_PAGE);
            }
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }

    }


//    @Override
    public void doFilter2(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        User user;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uriPath = request.getRequestURI();
        if (uriPath.contains(Webpage.USER_PROFILE)) {
            if ((user = (User) request.getSession().getAttribute("user")) != null){
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                servletResponse.getWriter().append("403 - Access denied");
                return;
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
