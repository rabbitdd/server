package command;

import human.HumanBeing;
import jdbc.ObjectsDAO;
import jdbc.UserDAO;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.concurrent.locks.ReadWriteLock;

public class Update implements ExecuteCommand {
    Collection collection;
    ArrayDeque<HumanBeing> people;
    HumanBeing human;
    ObjectsDAO user;
    ReadWriteLock lock;

    public Update(Collection collection, ArrayDeque<HumanBeing> People, HumanBeing human, ObjectsDAO user, ReadWriteLock lock) {
        this.collection = collection;
        this.people = People;
        this.human = human;
        this.user = user;
        this.lock = lock;
    }

    @Override
    public void execute(Command command)
    {
        String[] args = command.getArgs();
        try {
            collection.update(people, command.getHuman(), Long.parseLong(args[1]), user, command.getUser(), command, lock);
        } catch (Exception e) {
            command.ans.append("Упс.. Что-то не так скомандой ");
        }
    }
}
