package io.github.onetwostory.salon.dao;

import io.github.onetwostory.salon.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    List<User> findByName(String firstName, String lastName);
    Optional<User> findByEmail(String email);
}
