package ru.astra.repository;

import ru.astra.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User findById(Long userId);

    List<User> getAllUsers();

    Optional<User> save(String username);

    boolean update(Long userId, String username);

    boolean deleteUserById(Long userId);
}
