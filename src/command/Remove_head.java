package command;

import human.HumanBeing;
import jdbc.ObjectsDAO;
import main.Customer;

import java.util.ArrayDeque;
import java.util.concurrent.locks.ReadWriteLock;

public class Remove_head implements ExecuteCommand{
    Collection collection;
    ArrayDeque<HumanBeing> People;
    ObjectsDAO objectsDAO;
    ReadWriteLock lock;
    public Remove_head(Collection collection, ArrayDeque<HumanBeing> People, ObjectsDAO objectsDAO, ReadWriteLock lock) {
        this.collection = collection;
        this.People = People;
        this.objectsDAO = objectsDAO;
        this.lock = lock;
    }

    @Override
    public void execute(Command command) {
        collection.remove_head(People, objectsDAO, command.getUser(), command, lock);
    }
}
