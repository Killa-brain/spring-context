package ru.astra;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.astra.domain.User;
import ru.astra.service.UserService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.astra");
        UserService userService = (UserService) context.getBean("userService");

        // creating users
        User elvis = userService.saveUser("Elvis");
        System.out.println(elvis);
        System.out.println("*********************************");

        User john = userService.saveUser("John");
        System.out.println(john);
        System.out.println("*********************************");

        //getting user by id:
        var existingUser = userService.getById(john.getId());
        System.out.println(existingUser);
        System.out.println("*********************************");

        //getting all users:
        List<User> users = userService.getAllUsers();
        System.out.println(users);
        System.out.println("*********************************");

        //updating user:
        var rohn = userService.updateUser(elvis.getId(), "Rohn");
        System.out.println(rohn);
        users = userService.getAllUsers();
        System.out.println(users);
        System.out.println("*********************************");

        //deleting user:
        userService.deleteUser(elvis);
        users = userService.getAllUsers();
        System.out.println(users);
        System.out.println("*********************************");
    }
}
