package command;

import main.Application;

import java.util.HashSet;
import java.util.concurrent.locks.ReadWriteLock;

public class ExecuteScript implements ExecuteCommand{
    Collection collection;
    HashSet<String> set;
    String path;
    HashSet<Long> Id;
    ReadWriteLock lock;
    public ExecuteScript(Collection collection, HashSet<String> set, HashSet<Long> Id, ReadWriteLock lock) {
        this.collection = collection;
        this.set = set;
        this.Id = Id;
        this.lock = lock;
    }
    @Override
    public void execute(Command command) {
        path = command.getArgs()[1];
        collection.execute_script(path, set, Id, command, lock);
    }
}
