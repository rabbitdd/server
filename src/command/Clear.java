package command;

import human.HumanBeing;

import java.io.IOException;
import java.util.ArrayDeque;

public class Clear implements ExecuteCommand {
    Collection collection;
    ArrayDeque<HumanBeing> People;

    public Clear(ArrayDeque<HumanBeing> People, Collection collection) {
        this.People = People;
        this.collection = collection;
    }
    @Override
    public void execute(Command command) {
        collection.clear(People);
    }
}
