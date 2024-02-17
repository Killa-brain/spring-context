package ru.astra.repository.impl;

import org.springframework.stereotype.Component;
import ru.astra.domain.User;
import ru.astra.repository.UserDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    public static final String GET_USER_BY_NAME = "SELECT * FROM users WHERE id = ?";
    public static final String GET_ALL_USERS = "SELECT * FROM users";
    public static final String SAVE_USER = "INSERT INTO users (username) VALUES (?)";
    public static final String UPDATE_USER = "UPDATE users SET username = ? WHERE id = ?";
    public static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(Long userId) {
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(GET_USER_BY_NAME)) {
            preparedStatement.setLong(1, userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getLong("id"), rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(GET_ALL_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var user = new User(rs.getLong("id"), rs.getString("username"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Optional<User> save(String username) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, username);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                return Optional.of(new User(id, username));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Long userId, String username) {
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, username);
            preparedStatement.setLong(2, userId);
            int result = preparedStatement.executeUpdate();
            return result > 0 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUserById(Long userId) {
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, userId);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
