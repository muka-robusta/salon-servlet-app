package io.github.onetwostory.salon.dao.impl;

import io.github.onetwostory.salon.dao.UserDao;
import io.github.onetwostory.salon.dao.mapper.UserMapper;
import io.github.onetwostory.salon.entity.User;
import io.github.onetwostory.salon.entity.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class JDBCUserDao implements UserDao {

    private Connection connection;
    private final static Logger logger = LogManager.getLogger(JDBCUserDao.class.getName());

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    public List<User> findByName(String firstName, String lastName) {

        List<User> certainUsersWithFirstLastName = new ArrayList<>();

        String preparedStatementString = "SELECT * from users WHERE first_name = ? AND last_name = ?;";

        try (PreparedStatement statement = connection.prepareStatement(preparedStatementString)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            ResultSet resultSet = statement.executeQuery();
            UserMapper userMapper = new UserMapper();

            while(resultSet.next()) {
                User resultUser = userMapper.extractFromResultSet(resultSet);
                certainUsersWithFirstLastName.add(resultUser);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return certainUsersWithFirstLastName;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        logger.info(String.format("DB: Finding by email -> %s", email));
        String findByEmailStatementString = "SELECT * FROM users WHERE email = ?;";

        try (PreparedStatement statement = connection.prepareStatement(findByEmailStatementString)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            UserMapper userMapper = new UserMapper();

            User user = null;

            // TODO: add proper user absence handling
            while (resultSet.next()) {
                user = userMapper.extractFromResultSet(resultSet);
            }

            return Optional.of(user);

        } catch (SQLException throwables) {
            throw new RuntimeException("Can't connect to DB");
        }
    }

    @Override
    public User findById(int id) {
        User user = null;
        String findByIdStatementString = "SELECT * FROM users WHERE user_id = ?;";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(findByIdStatementString)) {
            preparedStatement.setInt(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new UserMapper().extractFromResultSet(resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        String statementQuery = "SELECT * from users;";

        try (Statement queryStatement = connection.createStatement()) {

            ResultSet resultSet = queryStatement.executeQuery(statementQuery);
            UserMapper userMapper = new UserMapper();

            while (resultSet.next()) {
                User currentUser = userMapper.extractFromResultSet(resultSet);
                users.add(currentUser);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }

    @Override
    public void create(User userToSave) {

        logger.info(String.format("DB Layer User -> %s", userToSave.toString()));

        String insertStatementString = "INSERT INTO users(first_name, last_name, email, hashed_password, role) " +
                "VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(insertStatementString)) {

            statement.setString(1, userToSave.getFirstName());
            statement.setString(2, userToSave.getLastName());
            statement.setString(3, userToSave.getEmail());
            statement.setString(4, userToSave.getHashedPassword());
            statement.setString(5, userToSave.getRole().toString());

            if (statement.executeUpdate() == 0)
                throw new RuntimeException("User with this email is already exists");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void update(User obj) {

    }

    @Override
    public void delete(User obj) {

    }

    @Override
    public void close() {

    }
}
