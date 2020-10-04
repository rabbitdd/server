package command;

import human.HumanBeing;

import java.util.ArrayDeque;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UpdateInterface implements ExecuteCommand{

    private ArrayDeque<HumanBeing> People;
    private Collection collection;
    private ReadWriteLock lock;
    public UpdateInterface(Collection collection, ArrayDeque<HumanBeing> People, ReadWriteLock lock) {
        this.People = People;
        this.collection = collection;
        this.lock = lock;
    }
    @Override
    public void execute(Command command) {
        collection.UpdateInterface(People, command, lock);
    }
}
