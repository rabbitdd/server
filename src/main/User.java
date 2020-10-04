package main;

import command.Command;
import command.ExecuteCommand;

public class User {
    ExecuteCommand add, add_if_min, clear, filter_starts_with_name, head, info, print_descending,
            print_field_ascending_mood, remove_by_id, remove_head, show, help, save, execute_script, update,
    updateInterface;

    public User(ExecuteCommand add,
                ExecuteCommand add_if_min,
                ExecuteCommand clear,
                ExecuteCommand filter_starts_with_name,
                ExecuteCommand head,
                ExecuteCommand info,
                ExecuteCommand print_descending,
                ExecuteCommand print_field_ascending_mood,
                ExecuteCommand remove_by_id,
                ExecuteCommand remove_head,
                ExecuteCommand show,
                ExecuteCommand help,
                ExecuteCommand save,
                ExecuteCommand execute_script,
                ExecuteCommand update,
                ExecuteCommand updateInterface) {
        this.add = add;
        this.add_if_min = add_if_min;
        this.clear = clear;
        this.filter_starts_with_name = filter_starts_with_name;
        this.head = head;
        this.info = info;
        this.print_descending = print_descending;
        this.print_field_ascending_mood = print_field_ascending_mood;
        this.remove_by_id = remove_by_id;
        this.remove_head = remove_head;
        this.show = show;
        this.help = help;
        this.save = save;
        this.execute_script = execute_script;
        this.update = update;
        this.updateInterface = updateInterface;
    }

    public void add(Command command) {
        add.execute(command);
    }
    public void add_if_min(Command command) {
        add_if_min.execute(command);
    }
    public void clear(Command command) {
        clear.execute(command);
    }
    public void filter_starts_with_name(Command command) {
        filter_starts_with_name.execute(command);
    }
    public void update(Command command) {
        update.execute(command);
    }
    public void head(Command command) {head.execute(command);}
    public void info(Command command) {info.execute(command);}
    public void print_descending(Command command) {print_descending.execute(command);}
    public void print_field_ascending_mood(Command command) {print_field_ascending_mood.execute(command);}
    public void remove_by_id(Command command) {remove_by_id.execute(command);}
    public void remove_head(Command command) {remove_head.execute(command);}
    public void show(Command command) {show.execute(command);}
    public void help(Command command) { help.execute(command);}
    public void save(Command command) {save.execute(command);}
    public void execute_script(Command command) {execute_script.execute(command);}
    public void updateInterface(Command command) {updateInterface.execute(command);}
}
