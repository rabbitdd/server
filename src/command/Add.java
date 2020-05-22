package command;

import human.HumanBeing;


import java.util.ArrayDeque;
import java.util.HashSet;

public class Add implements ExecuteCommand{
    Collection collection;
    ArrayDeque<HumanBeing> People;
    HashSet<Long> Id;
    HumanBeing human;

    public Add(Collection collection, ArrayDeque<HumanBeing> People, HumanBeing human, HashSet<Long> Id) {
        this.collection = collection;
        this.People = People;
        this.Id = Id;
        this.human = human;
    }

    @Override
    public void execute(Command command) {
        collection.add(People, command.getHuman(), Id);
    }
}
