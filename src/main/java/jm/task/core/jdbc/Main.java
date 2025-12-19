package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        System.out.println("\n Добавление пользователей");

        userService.saveUser("John", "Doe", (byte) 25);
        System.out.println("User с именем John добавлен в базу данных");
        userService.saveUser("Natasha", "Rostova", (byte) 16);
        System.out.println("User с именем Natasha добавлен в базу данных");
        userService.saveUser("Andrei", "Bolkonskiy", (byte) 28);
        System.out.println("User с именем Andrei добавлен в базу данных");

        System.out.println("Получены пользователи");
        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
