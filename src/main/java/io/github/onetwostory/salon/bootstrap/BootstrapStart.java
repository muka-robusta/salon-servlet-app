package io.github.onetwostory.salon.bootstrap;

import io.github.onetwostory.salon.dao.DaoFactory;
import io.github.onetwostory.salon.dao.UserDao;
import io.github.onetwostory.salon.dao.impl.JDBCUserDao;
import io.github.onetwostory.salon.entity.User;

import java.util.Optional;

public class BootstrapStart {
    public static void main(String[] args) {
        DaoFactory daoFactory = DaoFactory.getInstance();
        try (UserDao dao = daoFactory.createUserDao()) {
            Optional<User> byEmail = dao.findByEmail("annadominie@gmail.com");
            System.out.println(byEmail.get());
        }
    }
}
