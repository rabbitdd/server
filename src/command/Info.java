package command;

import human.HumanBeing;

import java.util.ArrayDeque;

public class Info implements ExecuteCommand {
    Collection collection;
    ArrayDeque<HumanBeing> People;
    public Info(Collection collection, ArrayDeque<HumanBeing> People) {
        this.collection = collection;
        this.People = People;
    }
    @Override
    public void execute(Command command) {
        collection.info(People);
    }
}
