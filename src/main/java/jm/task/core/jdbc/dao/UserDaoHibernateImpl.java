package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = new Util().getSessionFactory();


    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS users (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            name  VARCHAR(30),
            lastName  VARCHAR(30),
            age TINYINT
            )
            """;
    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users";
    private static final String SELECT_ALL_USER_SQL = "SELECT id,name,lastName,age FROM users";
    private static final String CLEAN_ALL_USER_SQL = "TRUNCATE TABLE users";

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(CREATE_TABLE_SQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать таблицу", e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(DROP_TABLE_SQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось удалить таблицу!", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)  {
                transaction.rollback();
            }
            throw new RuntimeException("Не удалось сохранить пользователя", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if(user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Не удалось удалить пользователя по ID", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<User> users= session.createNativeQuery(SELECT_ALL_USER_SQL,User.class).getResultList();
            transaction.commit();
            return users;
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Не удалось получить список пользователей", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(CLEAN_ALL_USER_SQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Не удалось очистить таблицу", e);
        }
    }
}