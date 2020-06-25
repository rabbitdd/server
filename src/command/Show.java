package command;

import human.HumanBeing;

import java.util.ArrayDeque;
import java.util.concurrent.locks.ReadWriteLock;

public class Show implements ExecuteCommand{
    Collection collection;
    ArrayDeque<HumanBeing> People;
    ReadWriteLock lock;
    public Show(Collection collection, ArrayDeque<HumanBeing> People, ReadWriteLock lock) {
        this.collection = collection;
        this.People = People;
        this.lock = lock;
    }

    @Override
    public void execute(Command command) {
        collection.show(People, lock, command);
    }
}
