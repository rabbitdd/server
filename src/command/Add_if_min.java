package command;

import human.HumanBeing;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;

public class Add_if_min implements ExecuteCommand{
    Collection collection;
    ArrayDeque<HumanBeing> People;
    HashSet<Long> Id;
    HumanBeing human;
    public Add_if_min(Collection collection, ArrayDeque<HumanBeing> people, HashSet<Long> id, HumanBeing human) {
        this.collection = collection;
        People = people;
        Id = id;
        this.human = human;
    }


    @Override
    public void execute(Command command) {
        collection.add_if_min(People, command.getHuman(), Id);
    }
}
