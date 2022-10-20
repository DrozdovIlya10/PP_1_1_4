package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;

public class Util {
private static Util instance;

    private Util() {}

    public static Util getInstance() {
        if(instance == null) {
            instance = new Util();
        }
        return instance;
    }
    public static SessionFactory getSessionFactory() {

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(Config.getConfiguration().getProperties()).build();

        SessionFactory sessionFactory = Config.getConfiguration().buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }
}
class Config {
    private static final String NAME_USER = "root";
    private static final String PASSWORD = "Qwerty-123";
    private static final String URL = "jdbc:mysql://localhost:3306/test1";
    private static SessionFactory sessionFactory;

    public static Configuration getConfiguration() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        Configuration configuration = new Configuration();

        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.URL, URL);
        settings.put(Environment.USER, NAME_USER);
        settings.put(Environment.PASS, PASSWORD);
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

        configuration.setProperties(settings);
        configuration.addAnnotatedClass(User.class);
        return configuration;
    }
}
/*    private static Connection connection;
      private static Statement statement;
        public void Connected () {
        try {
            connection = DriverManager.getConnection(URL, NAME_USER, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e ) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }*/

