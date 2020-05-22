package command;

import human.HumanBeing;

import java.util.ArrayDeque;

public class Remove_by_id implements ExecuteCommand{
    Collection collection;
    ArrayDeque<HumanBeing> People;
    public Remove_by_id(Collection collection, ArrayDeque<HumanBeing> People) {
        this.collection = collection;
        this.People = People;
    }

    @Override
    public void execute(Command command) {
        collection.remove_by_id(People, Integer.parseInt(command.getArgs()[1]));
    }
}
