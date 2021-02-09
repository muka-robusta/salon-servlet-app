package io.github.onetwostory.salon.manager;

import java.util.ResourceBundle;

public class MessageManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    private MessageManager(){}

    public static String getProperty(String propertyName) {
        return resourceBundle.getString(propertyName);
    }
}
