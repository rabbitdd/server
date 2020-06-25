package jdbc;

import command.Command;
import human.HumanBeing;
import main.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDAO implements DAO<Customer, String> {
    private Connection connection;
    public  RegistrationDAO(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean create(Customer user) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLUser.INSERT.typeOfRequest)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        }
        return true;
    }

    @Override
    public boolean read(Customer user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLUser.GET.typeOfRequest)) {
            System.out.println("зашел");
            System.out.println(Thread.activeCount());
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            ResultSet set = preparedStatement.executeQuery();
            //System.exit(0);
            return set.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Long id, HumanBeing human, Customer customer, Command command) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Long id, Customer customer, Command command) throws SQLException {
        return false;
    }

    @Override
    public boolean clear(Customer model, Command command) {
        return false;
    }

    enum SQLUser {
        GET("SELECT username " +
                "FROM users WHERE username = ? AND password = ?"),
        INSERT("INSERT INTO users (username, password)" +
                "VALUES (?, ?)");
        String typeOfRequest;
        SQLUser(String typeOfRequest) {
            this.typeOfRequest = typeOfRequest;
        }
    }
}
