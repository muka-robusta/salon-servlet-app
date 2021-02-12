package io.github.onetwostory.salon.dao;

import io.github.onetwostory.salon.entity.User;
import io.github.onetwostory.salon.entity.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    List<User> findByName(String firstName, String lastName);
    List<User> findByRole(Role role);
    Optional<User> findByEmail(String email);
}
