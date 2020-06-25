package jdbc;

import command.Command;
import human.HumanBeing;
import main.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;



public class Registration {
    static int count  = 0;
    static RegistrationDAO registrationDAO;
    static HashMap<String, String> customers;

    public Registration(HashMap<String, String> customers, RegistrationDAO registrationDAO) {
        Registration.customers = customers;
        Registration.registrationDAO = registrationDAO;
    }
    public static void reg(Customer user, Command command) {
        count++;
        String login = user.getLogin();
        String password = user.getPassword();
        if (registrationDAO.read(user)) {
            //System.out.println("*");
            command.setAns(new StringBuilder("Добро пожаловать"));
            //System.out.println(ans);
        }
        else {
            try {
                registrationDAO.create(user);
                customers.put(login, password);
                System.out.println("***");
                command.setAns(new StringBuilder("Зарегистрирован"));

                count = 0;
            } catch (SQLException e) {
               command.setAns(new StringBuilder("Неверный пароль для пользователя " + user.getLogin()));
            }

        }
    }

    public static void showMap() {
        for (Map.Entry<String, String> item : customers.entrySet()) {
            System.out.println(item.getKey() + " " + item.getValue());
        }
    }
}
