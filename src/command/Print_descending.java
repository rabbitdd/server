package command;

import human.HumanBeing;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;

public class Print_descending implements ExecuteCommand {
    Collection collection;
    ArrayDeque<HumanBeing> People;
    ArrayList<HumanBeing> SortList;
    ReadWriteLock lock;

    public Print_descending(Collection collection, ArrayDeque<HumanBeing> People, ArrayList<HumanBeing> SortList, ReadWriteLock lock) {
        this.collection = collection;
        this.People = People;
        this.SortList = SortList;
        this.lock = lock;
    }
    @Override
    public void execute(Command command) {
        collection.print_descending(People, SortList, command, lock);
    }
}
