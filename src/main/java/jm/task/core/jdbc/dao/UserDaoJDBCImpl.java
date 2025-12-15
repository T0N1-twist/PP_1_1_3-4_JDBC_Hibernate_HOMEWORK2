package jm.task.core.jdbc.dao;

import com.mysql.cj.callback.UsernameCallback;
import com.mysql.cj.exceptions.DataReadException;
import com.sun.xml.bind.v2.model.core.ID;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.print.DocFlavor;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.*;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS users(
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(30) NOT NULL,
                    lastName VARCHAR(30)  NOT NULL,
                    age INT
                    )
                """;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql); // или executeUpdate(sql)
            System.out.println("Таблица создана!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            int rowsAffected = ps.executeUpdate();
            System.out.println("Пользователь сохранен. Затронуто строк: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении пользователя: " + e.getMessage());
        }

    }

    @Override
    public void removeUserById(long id) {

        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            ps.setLong(1, id);

            System.out.println(ps.executeUpdate() > 0
                    ? "Пользователь с ID " + id + " УДАЛЕН УСПЕШНО!"
                    : "Пользователь с ID " + id + " Не найден "
            );
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
        }

    }


    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при получении пользователей: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

