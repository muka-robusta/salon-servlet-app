package io.github.onetwostory.salon.entity;


import io.github.onetwostory.salon.entity.enums.Role;

import java.util.Arrays;

public class User extends BaseEntity{

    private String firstName;
    private String lastName;
    private String email;
    private String hashedPassword;
    private Byte[] image;
    private Role role;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public static class Builder {
        private User user;

        public Builder() {
            user = new User();
        }

        public Builder firstName(String name) {
            user.setFirstName(name);
            return this;
        }

        public Builder lastName(String name) {
            user.setLastName(name);
            return this;
        }

        public Builder email(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder hashedPassword(String pass) {
            user.setHashedPassword(pass);
            return this;
        }

        public Builder id(Integer id) {
            user.setIdentifier(id);
            return this;
        }

        public Builder role(String value) {
            user.setRole(Role.valueOf(value.toUpperCase()));
            return this;
        }

        public User build() {
            return user;
        }

    }

    @Override
    public String toString() {
        return "User{" +
                "id = " + this.getIdentifier() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", role=" + role +
                '}';
    }
}
