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
import java.util.Optional;

public class RegisterUser implements Command {

    private final UserService userService;
    private static final Logger logger = LogManager.getLogger(RegisterUser.class.getName());
    private static final String CONTROLLER_PAGE = "register.jsp";

    public RegisterUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        Optional<String> lang = Optional.ofNullable(request.getParameter("lang"));
        loadLocalization(request, lang.orElse("ua"));

        String[] validationFields = {"register_first_name",
                "register_last_name",
                "register_email",
                "register_password"};


        boolean passValue = Arrays.stream(validationFields)
                .map(request::getParameter)
                .allMatch(this::validateField);

        if (!passValue)
            return CONTROLLER_PAGE;

        User registeredUser = new User.Builder()
                .firstName(request.getParameter("register_first_name"))
                .lastName(request.getParameter("register_last_name"))
                .email(request.getParameter("register_email"))
                .hashedPassword(convertPasswordToHash(request.getParameter("register_password")))
                .build();


        try {
            userService.saveUser(registeredUser);
        }catch (RuntimeException ex) {
            logger.error(String.format("Unable to register user -> %s", ex));
            request.setAttribute("registration_error", ex.getMessage());
        }

        return Webpage.LOGIN_PAGE;
    }

    private void loadLocalization(HttpServletRequest request, String langProfile) {
        String domainProperty = "message";
        String[] signs = {"register", "first_name", "last_name", "email", "password", "confirm"};

        Arrays.stream(signs)
                .forEach(signElement -> {
                    String attributeName = domainProperty + "." + langProfile + "." + signElement;
                    String fromSignName = "register_" + signElement;

                    String attributeValue = MessageManager.getProperty(attributeName);
                    request.setAttribute(fromSignName, attributeValue);
                });
    }

    private String convertPasswordToHash(String rawPassword) {
        String textToHash = SecretManager.getProperty("hash.salt.start") +
                rawPassword +
                SecretManager.getProperty("hash.salt.end");

        return SecretManager.getBytesToHex(SecretManager.encodeToSHA256(textToHash));
    }

    private boolean validateField(String value) {
        if(value == null) return false;
        else if (value.equals("")) return false;
        //else if (value.length() > 25) return false;
        else return true;
    }


}
