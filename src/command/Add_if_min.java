package command;

import human.HumanBeing;
import jdbc.ObjectsDAO;
import jdbc.UserDAO;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.concurrent.locks.ReadWriteLock;

public class Add_if_min implements ExecuteCommand{
    Collection collection;
    ArrayDeque<HumanBeing> People;
    HashSet<Long> Id;
    HumanBeing human;
    UserDAO user;
    ObjectsDAO objectsDAO;
    ReadWriteLock lock;
    public Add_if_min(Collection collection, ArrayDeque<HumanBeing> people, HashSet<Long> id, HumanBeing human, UserDAO user, ObjectsDAO objectsDAO,
                      ReadWriteLock lock) {
        this.collection = collection;
        People = people;
        Id = id;
        this.human = human;
        this.user = user;
        this.objectsDAO = objectsDAO;
        this.lock = lock;
    }


    @Override
    public void execute(Command command) {
        collection.add_if_min(People, command.getHuman(), Id, user, objectsDAO, command, lock);
    }
}
