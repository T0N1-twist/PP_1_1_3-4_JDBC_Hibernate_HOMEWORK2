package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        System.out.println("Таблица создана!");

        System.out.println("\n Добавление пользователей");
        userDao.saveUser("John", "Doe", (byte)25);
        userDao.saveUser("Andrei", "Bolkonsky", (byte)27);
        userDao.saveUser("Natasha", "Rostova", (byte)16);

        System.out.println("\n3. Список всех пользователей:");
        List<User> allUsers = userDao.getAllUsers();
        allUsers.forEach(System.out::println);

        System.out.println("\n4. Очистка таблицы...");
        userDao.cleanUsersTable();


        System.out.println("\n=== После очистки ===");
        List<User> usersAfter = userDao.getAllUsers();
        System.out.println("Количество пользователей: " + usersAfter.size());

        if (usersAfter.isEmpty()) {
            System.out.println(" Таблица успешно очищена!");
        } else {
            System.out.println(" Ошибка: таблица не очищена!");
        }


        userDao.dropUsersTable();
        System.out.println("Таблица удалена");

        // Homework completed

    }
}
