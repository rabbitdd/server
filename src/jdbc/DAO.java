package jdbc;

import command.Command;
import human.HumanBeing;
import main.Customer;

import java.sql.SQLException;

public interface DAO<Entity, Key> {
    boolean create(Entity model) throws SQLException;
    boolean read(Entity model) throws SQLException;
    boolean update(Long id, HumanBeing human, Customer user, Command command) throws SQLException;
    boolean delete(Long id, Entity user, Command command) throws SQLException;
    boolean clear(Entity model, Command command) throws SQLException;
}
