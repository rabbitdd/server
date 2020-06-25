package jdbc;

import command.Command;
import human.HumanBeing;
import main.Customer;
import main.UdpServer;
import parser.Parser;

import java.sql.*;

public class ObjectsDAO implements DAO<Customer, Long> {
    Connection connection;
    Command command;
    static Customer user;

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public ObjectsDAO(Connection connection) {
        this.connection = connection;

    }
    @Override
    public boolean create(Customer user) throws SQLException {
        System.out.println(user.getLogin());
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLUser.INSERT.typeOfRequest)) {
            preparedStatement.setLong(1, command.getHuman().getId());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.executeQuery();
        }
        return false;
    }

    @Override
    public boolean read(Customer user) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Long id, HumanBeing human, Customer user, Command command) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLUser.GET.typeOfRequest + id)) {
            ResultSet set = preparedStatement.executeQuery();
            set.next();
            if (user.getLogin().equals(set.getString(2))) {
                try (PreparedStatement statement = connection.prepareStatement(SQLUser.UPDATE.typeOfRequest + id)) {
                    statement.setString(1, human.getName());
                    //statement.setLong(2, human.getId());
                    statement.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
                    statement.setBoolean(3, human.isRealHero());
                    statement.setBoolean(4, human.getHasToothpick());
                    statement.setInt(5, human.getImpactSpeed());
                    statement.setString(6, human.getWeaponType());
                    statement.setString(7, human.getMood());
                    statement.setBoolean(8, human.getCar().isCool());
                    statement.setFloat(9, human.getCoordinates().getX());
                    statement.setInt(10, human.getCoordinates().getY());
                    statement.executeUpdate();
                    return true;
                    //newPreparedStatement.executeUpdate();
                }
            } else {
                StringBuilder ans = new StringBuilder();
                ans.append("Этот объект вам не пренадлежит");
                command.setAns(ans);
            }
        }
        return false;
    }

    @Override
    public boolean delete(Long id, Customer user, Command command) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLUser.GET.typeOfRequest + id)) {
            ResultSet set = preparedStatement.executeQuery();
            set.next();
            if (user.getLogin().equals(set.getString(2))) {
                try (PreparedStatement newPreparedStatement = connection.prepareStatement(SQLUser.DELETE.typeOfRequest + id)) {
                    newPreparedStatement.executeUpdate();
                    //command.setAns(new StringBuilder("Объект с id " + id + " удален"));
                    return true;
                }
            } else {
                StringBuilder ans = new StringBuilder();
                ans.append("Объект с id ").append(id).append(" вам не пренадлежит");
                command.setAns(ans);
            }
        }
        return false;
    }

    @Override
    public boolean clear(Customer model, Command command) throws SQLException {
        StringBuilder ans = new StringBuilder();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLUser.NEWGET.typeOfRequest)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String login = resultSet.getString(2);
                long id = resultSet.getLong(1);
                if (model.getLogin().equals(login)) {
                    try (PreparedStatement statement = connection.prepareStatement(SQLUser.DELETE.typeOfRequest + id)) {
                        statement.executeUpdate();
                    }
                } else {

                    ans.append("Объект с id ").append(id).append(" вам не пренадлежит").append("\n");
                    command.setAns(ans);
                }
            }
        }
        return false;
    }

    enum SQLUser {
        GET("SELECT id, login FROM Objects WHERE id = "),
        INSERT("INSERT INTO Objects (id, login)" +
                "VALUES (?, ?) RETURNING id"),
        DELETE("DELETE FROM People WHERE id = "),
        UPDATE("UPDATE People SET name = ?, Date = ?, realHero = ?, hasToothpick = ?, impactSpeed = " +
                "?, weaponType = ?, mood = ?, car = ?, coordinate_x = ?, coordinate_y = ? WHERE id = "),
        NEWGET("SElECT id, login FROM Objects");
        String typeOfRequest;
        SQLUser(String typeOfRequest) {
            this.typeOfRequest = typeOfRequest;
        }
    }
}
