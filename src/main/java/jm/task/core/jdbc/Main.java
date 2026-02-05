package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;



public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        System.out.println("\n Добавление пользователей");

        userService.saveUser("Tony", "Twist", (byte) 25);
        userService.saveUser("Sofia", "Rodina", (byte) 39);
        userService.saveUser("Nina", "Kravitz", (byte) 34);

        System.out.println("Получены пользователи");
        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
