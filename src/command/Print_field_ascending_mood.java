package command;

import human.HumanBeing;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;

public class Print_field_ascending_mood implements ExecuteCommand {

    Collection collection;
    ArrayDeque<HumanBeing> People;
    ArrayList<HumanBeing> Mood;
    ReadWriteLock lock;

    public Print_field_ascending_mood(Collection collection, ArrayDeque<HumanBeing> People, ArrayList<HumanBeing> Mood, ReadWriteLock lock) {
        this.collection = collection;
        this.People = People;
        this.Mood = Mood;
        this.lock = lock;
    }


    @Override
    public void execute(Command command) {
        collection.print_field_ascending_mood(People, Mood, command, lock);
    }
}
