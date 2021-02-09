package io.github.onetwostory.salon.service;

import io.github.onetwostory.salon.dao.DaoFactory;
import io.github.onetwostory.salon.dao.UserDao;
import io.github.onetwostory.salon.dao.impl.JDBCDaoFactory;
import io.github.onetwostory.salon.entity.User;
import io.github.onetwostory.salon.entity.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserService {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private final static Logger logger = LogManager.getLogger(UserService.class.getName());

    public List<User> findAllUsers() {
        UserDao userDao = daoFactory.createUserDao();
        return userDao.findAll();
    }

    public void saveUser(User user) {

        user.setRole(Role.CLIENT);

        logger.info(String.format("Saving user -> %s", user.toString()));

        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.create(user);
        }

    }

    public Optional<User> findByEmail(String email) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            logger.info(String.format("Finding by email -> %s", email));
            Optional<User> userOptional = userDao.findByEmail(email);
            return userOptional;
        }
    }

}
