package io.github.onetwostory.salon.controller.commands;

import io.github.onetwostory.salon.controller.Webpage;
import io.github.onetwostory.salon.entity.User;
import io.github.onetwostory.salon.manager.MessageManager;
import io.github.onetwostory.salon.manager.SecretManager;
import io.github.onetwostory.salon.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class LoginUser implements Command {

    private final UserService userService;
    private static final String CONTROLLER_PAGE = "login.jsp";
    private static final Logger logger = LogManager.getLogger(LoginUser.class.getName());

    public LoginUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        Optional<String> lang = Optional.ofNullable(request.getParameter("lang"));
        loadLocalization(request, lang.orElse("ua"));

        String[] validationFields = {"login_email", "login_password"};
        boolean isValid = Arrays.stream(validationFields)
                .map(request::getParameter)
                .allMatch(this::validateField);

        if (!isValid)
            return CONTROLLER_PAGE;

        String email = request.getParameter("login_email");
        String password = request.getParameter("login_password");

        Optional<User> userOptional = userService.findByEmail(email);

        if (!userOptional.isPresent()) {
            request.setAttribute("errorLoginPassMessage", "Wrong credentials");
            return CONTROLLER_PAGE;
        }

        User loginUser = userOptional.get();
        if (loginUser.getHashedPassword().equals(convertPasswordToHash(password))) {
            request.getSession().setAttribute("user", loginUser);
            logger.info(String.format("Login operation is successful -> %s", loginUser.getName()));
            System.out.println("Log in successful");
            return Webpage.USER_LIST;
        }

        request.setAttribute("errorLoginPassMessage", "Wrong credentials");
        return CONTROLLER_PAGE;
    }


    private void loadLocalization(HttpServletRequest request, String profile) {
        request.setAttribute("lang", profile);
        String domainAction = "message";
        String[] signs = {"login", "email", "password", "confirm", "account_absence_question", "register"};

        Arrays.stream(signs)
                .forEach(sign -> {
                    String attributeName = domainAction + "." + profile + "." + sign;
                    String fromSignName = "loginform_" + sign;

                    String attributeValue = MessageManager.getProperty(attributeName);

                    request.setAttribute(fromSignName, attributeValue);
                });

    }

    private boolean validateField(String value) {
        if (value == null) return false;
        else if ("".equals(value)) return false;
        return true;
    }

    private String convertPasswordToHash(String rawPassword) {
        String textToHash = SecretManager.getProperty("hash.salt.start") +
                rawPassword +
                SecretManager.getProperty("hash.salt.end");

        return SecretManager.getBytesToHex(SecretManager.encodeToSHA256(textToHash));
    }

}
