package io.github.onetwostory.salon.dao.mapper;

import io.github.onetwostory.salon.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User>{

    @Override
    public User extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new User.Builder()
                .id(resultSet.getInt("user_id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .hashedPassword(resultSet.getString("hashed_password"))
                .role(resultSet.getString("role"))
                .build();
    }
}
