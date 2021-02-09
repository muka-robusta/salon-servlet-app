package io.github.onetwostory.salon;

import io.github.onetwostory.salon.controller.Webpage;
import io.github.onetwostory.salon.controller.commands.*;
import io.github.onetwostory.salon.entity.AppointmentApplication;
import io.github.onetwostory.salon.service.AppointmentApplicationService;
import io.github.onetwostory.salon.service.AppointmentService;
import io.github.onetwostory.salon.service.ServiceOptionService;
import io.github.onetwostory.salon.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Servlet extends HttpServlet {

    private UserService userService = new UserService();
    private ServiceOptionService serviceOptionService = new ServiceOptionService();
    private AppointmentApplicationService appointmentApplicationService = new AppointmentApplicationService();
    private AppointmentService appointmentService = new AppointmentService();

    private Map<String, Command> commands = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(Servlet.class.getName());


    public void init() {
        commands.put(Webpage.LOGIN_PAGE, new LoginUser(userService));
        commands.put(Webpage.REGISTER_PAGE, new RegisterUser(userService));
        commands.put(Webpage.USER_LIST, new UserList(userService));
        commands.put(Webpage.USER_SERVICE, new ServiceOptionList(serviceOptionService));
        commands.put(Webpage.APPLY, new ApplyForAppointment(appointmentApplicationService, serviceOptionService));
        commands.put(Webpage.HANDLE_APPLICATIONS, new AppointmentSetCommand(appointmentService, appointmentApplicationService));
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        handleRequest(request, response);

    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String path = request.getRequestURI();
        //path = path.replaceAll(".*/" , "");
        logger.info(String.format("requested page -> %s", path));


        Command command = commands.getOrDefault(path , (r)->"/index.jsp");
        String page = command.execute(request);
        request.getRequestDispatcher(page).forward(request, response);
    }


}
