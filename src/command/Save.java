package command;

import human.HumanBeing;

import java.util.ArrayDeque;

public class Save implements ExecuteCommand {
    Collection collection;
    ArrayDeque<HumanBeing> People;
    public Save(Collection collection, ArrayDeque<HumanBeing> People) {
        this.collection = collection;
        this.People = People;
    }

    @Override
    public void execute(Command command) {
        //collection.save(People);
    }
}
