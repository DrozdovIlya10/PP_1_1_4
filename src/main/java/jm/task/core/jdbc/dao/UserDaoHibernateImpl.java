package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    SessionFactory sessionFactory = Util.getSessionFactory();
    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            session.createSQLQuery("CREATE TABLE user (id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    lastname VARCHAR(255) NOT NULL,\n" +
                    "    age INT NOT NULL\n" +
                    ");"
            ).executeUpdate();
            transaction.commit();

            //session.close();
        } catch (Exception e) {
            System.out.println("Ошибка при создании таблицы");
            transaction.rollback();
        }
    }
    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            transaction.commit();

            //session.close();
        } catch (Exception e) {
            System.out.println("Ошибка при удалении таблицы");
            transaction.rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);

            session.save(user);

            transaction.commit();

            //session.close();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении пользователя");
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            transaction = session.beginTransaction();

            User user = (User) session.load(User.class, id);
            session.delete(user);

            transaction.commit();

            //session.close();
        } catch (Exception e) {
            System.out.println("Ошибка при удалении пользователя");
            transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(User.class);
            Root rootEntry = cq.from(User.class);
            CriteriaQuery all = cq.select(rootEntry);
            TypedQuery allQuery = session.createQuery(all);
            list = allQuery.getResultList();
        } catch (Exception e) {
            System.out.println("Ошибка при выводе всех пользователей");
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE User").executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Ошибка при очистке таблицы user");
            transaction.rollback();
        }
    }
}
