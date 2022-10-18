package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        // Создание таблицы User(ов)
        userService.createUsersTable();

        User user1 = new User("Ilya", "Drozdov", (byte) 22);
        User user3 = new User("Kiril", "Kirilov", (byte) 30);

        //Добавление 4 User(ов) в таблицу с данными на свой выбор.
        userService.saveUser(user1.getName(),user1.getLastName(),user1.getAge());
        userService.saveUser("Ivan", "Ivanov", (byte)25);
        userService.saveUser(user3.getName(),user3.getLastName(),user3.getAge());
        userService.saveUser("Anton", "Antonov", (byte)67);

        //Получение всех User из базы и вывод в консоль
        System.out.println(userService.getAllUsers().toString());

       // userService.removeUserById(2);
        //System.out.println(userService.getAllUsers().toString());

        //Очистка таблицы User(ов)
        userService.cleanUsersTable();

        //Удаление таблицы
        userService.dropUsersTable();
    }
}
