package command;

import human.HumanBeing;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Filter_starts_with_name implements ExecuteCommand{
    Collection collection;
    ArrayDeque<HumanBeing> People;

    public Filter_starts_with_name(Collection collection, ArrayDeque<HumanBeing> People) {
        this.collection = collection;
        this.People = People;
    }

    @Override
    public void execute(Command command) {
        //System.out.println(command.getArgs()[0]);
        collection.filter_starts_with_name(People, command.getArgs()[1]);
    }
}
