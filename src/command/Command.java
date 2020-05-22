package command;

import human.HumanBeing;

import java.io.Serializable;

public class Command implements Serializable {
    private HumanBeing human;

    public HumanBeing getHuman() {
        return human;
    }

    public void setHuman(HumanBeing human) {
        this.human = human;
    }

    public String getNameOfCommand() {
        return nameOfCommand;
    }

    public void setNameOfCommand(String nameOfCommand) {
        this.nameOfCommand = nameOfCommand;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    private String nameOfCommand;
    private String[] args;

    public Command(String name, String[] args) {
        this.nameOfCommand = name;
        this.args = args;
    }




}
