package command;

import human.HumanBeing;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;

public class Filter_starts_with_name implements ExecuteCommand{
    Collection collection;
    ArrayDeque<HumanBeing> People;
    ReadWriteLock lock;

    public Filter_starts_with_name(Collection collection, ArrayDeque<HumanBeing> People, ReadWriteLock lock) {
        this.collection = collection;
        this.People = People;
        this.lock = lock;
    }

    @Override
    public void execute(Command command) {
        //System.out.println(command.getArgs()[0]);
        collection.filter_starts_with_name(People, command.getArgs()[1], command, lock);
    }
}
