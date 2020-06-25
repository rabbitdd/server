package command;

public class Help implements ExecuteCommand{
    Collection collection;

    public Help(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(Command command) {
        collection.help(command);
    }
}
