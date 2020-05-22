package command;

import human.HumanBeing;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Print_field_ascending_mood implements ExecuteCommand {

    Collection collection;
    ArrayDeque<HumanBeing> People;
    ArrayList<HumanBeing> Mood;

    public Print_field_ascending_mood(Collection collection, ArrayDeque<HumanBeing> People, ArrayList<HumanBeing> Mood) {
        this.collection = collection;
        this.People = People;
        this.Mood = Mood;
    }


    @Override
    public void execute(Command command) {
        collection.print_field_ascending_mood(People, Mood);
    }
}
