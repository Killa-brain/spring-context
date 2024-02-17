package ru.astra.service.impl;

import org.springframework.stereotype.Component;
import ru.astra.domain.User;
import ru.astra.repository.UserDao;
import ru.astra.service.UserService;

import java.util.List;

@Component("userService")
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getById(Long userId) {
        return userDao.findById(userId);
    }

    @Override
    public User saveUser(String username) {
        var optionalUser = userDao.save(username);
        return optionalUser.orElseThrow(() -> new RuntimeException("error for saving user " + username));
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User updateUser(Long userId, String username) {
        var isUpdate = userDao.update(userId, username);
        if (isUpdate) {
            return new User(userId, username);
        } else throw new RuntimeException("error for updating user: " + username);
    }

    @Override
    public boolean deleteUser(User user) {
        return userDao.deleteUserById(user.getId());
    }
}
