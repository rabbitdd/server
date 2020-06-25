package command;

import human.HumanBeing;

import java.util.ArrayDeque;
import java.util.concurrent.locks.ReadWriteLock;

public class Info implements ExecuteCommand {
    Collection collection;
    ArrayDeque<HumanBeing> People;
    ReadWriteLock lock;
    public Info(Collection collection, ArrayDeque<HumanBeing> People, ReadWriteLock lock) {
        this.collection = collection;
        this.People = People;
        this.lock = lock;
    }
    @Override
    public void execute(Command command) {
        collection.info(People, lock, command);
    }
}
