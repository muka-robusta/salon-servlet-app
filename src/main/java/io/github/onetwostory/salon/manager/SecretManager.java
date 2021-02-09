package io.github.onetwostory.salon.manager;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class SecretManager {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("secret");
    private SecretManager(){}

    public static String getProperty(String propertyName) {
        return bundle.getString(propertyName);
    }

    public static byte[] encodeToSHA256(String text) {
        try {

            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            return instance.digest(text.getBytes(StandardCharsets.UTF_8));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm is not found");
        }
    }

    public static String getBytesToHex(byte[] hash) {
        StringBuilder hashHex = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);

            if (hex.length() == 1)
                hashHex.append('0');

            hashHex.append(hex);
        }

        return hashHex.toString();
    }
}
