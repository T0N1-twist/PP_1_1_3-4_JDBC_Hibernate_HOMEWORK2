package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users";
    private static final String CLEAN_TABLE_SQL = "TRUNCATE TABLE users";


    @Override
    public void createUsersTable() {
        Util.getSessionFactory();

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery(DROP_TABLE_SQL).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось удалить таблицу", e);
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
                Transaction tx = null;
            try (Session session = Util.getSessionFactory().openSession()) {
                tx = session.beginTransaction();
                session.save(new User(name, lastName, age));
                tx.commit();
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                throw new RuntimeException("Не удалось сохранить пользователя", e);
            }
       }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Не удалось удалить пользователя по ID", e);
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
           return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось получить список пользователей", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Не удалось очистить таблицу пользователей", e);
        }

    }
}
