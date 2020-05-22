package command;

import human.HumanBeing;

import java.util.ArrayDeque;

public class Show implements ExecuteCommand{
    Collection collection;
    ArrayDeque<HumanBeing> People;
    public Show(Collection collection, ArrayDeque<HumanBeing> People) {
        this.collection = collection;
        this.People = People;
    }

    @Override
    public void execute(Command command) {
        collection.show(People);
    }
}
