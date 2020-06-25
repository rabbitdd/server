package command;

import human.HumanBeing;
import jdbc.ObjectsDAO;
import jdbc.UserDAO;

import java.util.ArrayDeque;
import java.util.concurrent.locks.ReadWriteLock;

public class Remove_by_id implements ExecuteCommand{
    Collection collection;
    ArrayDeque<HumanBeing> People;
    UserDAO user;
    ObjectsDAO objectsDAO;
    ReadWriteLock lock;
    public Remove_by_id(Collection collection, ArrayDeque<HumanBeing> People, UserDAO user, ObjectsDAO objectsDAO, ReadWriteLock lock) {
        this.collection = collection;
        this.People = People;
        this.user = user;
        this.objectsDAO = objectsDAO;
        this.lock = lock;
    }

    @Override
    public void execute(Command command) {
        collection.remove_by_id(People, Integer.parseInt(command.getArgs()[1]), user, objectsDAO, command.getUser(), command, lock);
    }
}
