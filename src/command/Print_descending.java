package command;

import human.HumanBeing;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Print_descending implements ExecuteCommand {
    Collection collection;
    ArrayDeque<HumanBeing> People;
    ArrayList<HumanBeing> SortList;

    public Print_descending(Collection collection, ArrayDeque<HumanBeing> People, ArrayList<HumanBeing> SortList) {
        this.collection = collection;
        this.People = People;
        this.SortList = SortList;
    }
    @Override
    public void execute(Command command) {
        collection.print_descending(People, SortList);
    }
}
