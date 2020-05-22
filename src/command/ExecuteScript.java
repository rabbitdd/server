package command;

import main.Application;

import java.util.HashSet;

public class ExecuteScript implements ExecuteCommand{
    Collection collection;
    HashSet<String> set;
    String path;
    HashSet<Long> Id;
    public ExecuteScript(Collection collection, HashSet<String> set, HashSet<Long> Id) {
        this.collection = collection;
        this.set = set;
        this.Id = Id;
    }
    @Override
    public void execute(Command command) {
        path = command.getArgs()[1];
        collection.execute_script(path, set, Id);
    }
}
