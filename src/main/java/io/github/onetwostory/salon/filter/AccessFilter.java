package io.github.onetwostory.salon.filter;


import io.github.onetwostory.salon.controller.Webpage;
import io.github.onetwostory.salon.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        User user;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uriPath = request.getRequestURI();
        if (uriPath.contains(Webpage.USER_LIST)) {
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
