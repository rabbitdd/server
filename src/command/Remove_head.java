package command;

import human.HumanBeing;

import java.util.ArrayDeque;

public class Remove_head implements ExecuteCommand{
    Collection collection;
    ArrayDeque<HumanBeing> People;
    public Remove_head(Collection collection, ArrayDeque<HumanBeing> People) {
        this.collection = collection;
        this.People = People;
    }

    @Override
    public void execute(Command command) {
        collection.remove_head(People);
    }
}
