package command;

import human.HumanBeing;
import jdbc.ObjectsDAO;
import jdbc.UserDAO;


import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.concurrent.locks.ReadWriteLock;

public class Add implements ExecuteCommand{
    Collection collection;
    ArrayDeque<HumanBeing> People;
    HashSet<Long> Id;
    HumanBeing human;
    UserDAO user;
    ObjectsDAO objectsDAO;
    ReadWriteLock lock;

    public Add(Collection collection, ArrayDeque<HumanBeing> People, HumanBeing human, HashSet<Long> Id, UserDAO user, ObjectsDAO objectsDAO,
               ReadWriteLock lock) {
        this.collection = collection;
        this.People = People;
        this.Id = Id;
        this.human = human;
        this.user = user;
        this.objectsDAO = objectsDAO;
        this.lock = lock;
    }

    @Override
    public void execute(Command command) {
        objectsDAO.setCommand(command);
        collection.add(People, command.getHuman(), Id, user, objectsDAO, command, lock);
    }
}
