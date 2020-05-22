package command;

import human.HumanBeing;

import java.util.ArrayDeque;

public class Head implements ExecuteCommand {
    Collection collection;
    ArrayDeque<HumanBeing> People;
    public Head(Collection collection, ArrayDeque<HumanBeing> People) {
        this.collection = collection;
        this.People = People;
    }


    @Override
    public void execute(Command command) {
        collection.head(People);
    }
}
