package command;

import human.HumanBeing;

import java.util.ArrayDeque;
import java.util.concurrent.locks.ReadWriteLock;

public class Head implements ExecuteCommand {
    Collection collection;
    ArrayDeque<HumanBeing> People;
    ReadWriteLock lock;
    public Head(Collection collection, ArrayDeque<HumanBeing> People, ReadWriteLock lock) {
        this.collection = collection;
        this.People = People;
        this.lock = lock;
    }


    @Override
    public void execute(Command command) {
        collection.head(People, lock, command);
    }
}
