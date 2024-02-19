package ru.astra.service;

import ru.astra.domain.User;

import java.util.List;

public interface UserService {
    User getById(Long userId);

    User saveUser(String username);

    List<User> getAllUsers();

    User updateUser(Long userId, String username);

    boolean deleteUser(User user);
}
