package jdbc;

import command.Command;
import human.HumanBeing;
import main.Customer;

import java.sql.*;

public class UserDAO implements DAO<HumanBeing, String> {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean create(HumanBeing human) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.INSERT.typeOfRequest)) {
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
            //statement.executeUpdate();
            ResultSet set = statement.executeQuery();
            set.next();
            human.setId(set.getLong(1));
            //statement.executeUpdate();
            //result = statement.executeQuery().next();
        }
        return false;
    }

    @Override
    public boolean read(HumanBeing humanBeing) {
        return false;
    }

    @Override
    public boolean update(Long id, HumanBeing human, Customer user, Command command) throws SQLException {
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
        }
        return false;
    }

    @Override
    public boolean delete(Long id, HumanBeing human, Command command) throws SQLException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.DELETE.typeOfRequest + id)) {
            statement.executeUpdate();

        }
        return false;
    }

    @Override
    public boolean clear(HumanBeing model, Command command) {
        return false;
    }


    enum SQLUser {
        //GET("SELECT name, id, coordinates, Date, realHero, hasToothpick, impactSpeed, weaponType, mood, car, coordinate_x, coordinate_y " +
        //        "FROM People"),
        INSERT("INSERT INTO People (name, id, Date, realHero, hasToothpick, " +
                "impactSpeed, weaponType, mood, car, coordinate_x, coordinate_y) " +
                "VALUES (?, nextval('generate'), ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id"),
        DELETE("DELETE FROM People WHERE id = "),
        UPDATE("UPDATE People SET name = ?, Date = ?, realHero = ?, hasToothpick = ?, impactSpeed = " +
                "?, weaponType = ?, mood = ?, car = ?, coordinate_x = ?, coordinate_y = ? WHERE id = ")
        ;
        String typeOfRequest;
        SQLUser(String typeOfRequest) {
            this.typeOfRequest = typeOfRequest;
        }
    }
}
