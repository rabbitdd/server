package command;

import human.HumanBeing;
import jdbc.ObjectsDAO;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayDeque;
import java.util.concurrent.locks.ReadWriteLock;

public class Clear implements ExecuteCommand {
    Collection collection;
    ArrayDeque<HumanBeing> People;
    ObjectsDAO objectsDAO;
    Connection connection;
    ReadWriteLock lock;
    public Clear(ArrayDeque<HumanBeing> People, Collection collection, ObjectsDAO objectsDAO, Connection connection,
                 ReadWriteLock lock) {
        this.People = People;
        this.collection = collection;
        this.objectsDAO = objectsDAO;
        this.connection = connection;
        this.lock = lock;
    }
    @Override
    public void execute(Command command) {
        collection.clear(People, objectsDAO, command.getUser(), connection, lock, command);
    }
}
