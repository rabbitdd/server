package command;

import human.HumanBeing;

import java.util.ArrayDeque;
import java.util.HashSet;

public class Update implements ExecuteCommand {
    Collection collection;
    ArrayDeque<HumanBeing> people;
    HumanBeing human;

    public Update(Collection collection, ArrayDeque<HumanBeing> People, HumanBeing human) {
        this.collection = collection;
        this.people = People;
        this.human = human;
    }

    @Override
    public void execute(Command command)
    {
        String[] args = command.getArgs();
        collection.update(people, command.getHuman(), Long.parseLong(args[1]));
    }
}
