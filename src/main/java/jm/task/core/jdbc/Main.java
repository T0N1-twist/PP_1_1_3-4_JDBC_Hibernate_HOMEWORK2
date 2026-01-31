package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;



public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        System.out.println("\n Добавление пользователей");

        userService.saveUser("Tony", "Twist", (byte) 25);
        System.out.println("User с именем Tony добавлен в базу данных");
        userService.saveUser("Sofia", "Rodina", (byte) 39);
        System.out.println("User с именем Jim добавлен в базу данных");
        userService.saveUser("Nina", "Kravitz", (byte) 34);
        System.out.println("User с именем Nina добавлен в базу данных");

        System.out.println("Получены пользователи");
        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
