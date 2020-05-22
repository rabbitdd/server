package main;

import command.*;
import human.HumanBeing;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

public class Application {
    static HumanBeing human;
    static ArrayDeque<HumanBeing> Person;
    static HashSet<Long> Id;
    static Collection collection;
    static User user;
    static ArrayList<HumanBeing> SortList;
    static HashSet<String> set;

    public Application(HumanBeing human, ArrayDeque<HumanBeing> Person, HashSet<Long> id, Collection collection,
                       ArrayList<HumanBeing> SortList, HashSet<String> set, User user) {
        this.human = human;
        this.Person = Person;
        Id = id;
        this.SortList = SortList;
        this.collection = collection;
        this.set = set;
        this.user = user;
    }

    /*public static void RunApp() throws SocketException {
        User user = new User(
                new Add(collection, Person, human, Id),
                new Add_if_min(collection, Person, Id, human),
                new Clear(Person, collection),
                new Filter_starts_with_name(collection, Person),
                new Head(collection, Person),
                new Info(collection, Person),
                new Print_descending(collection, Person, SortList),
                new Print_field_ascending_mood(collection, Person, SortList),
                new Remove_by_id(collection, Person),
                new Remove_head(collection, Person),
                new Show(collection, Person),
                new Help(collection),
                new Save(collection, Person),
                new ExecuteScript(collection, set, Id),
                new Update(collection, Person, human)
        );

    }*/

    public static void Request(Command command) {
        switch (command.getNameOfCommand()) {
            case "add":
                user.add(command);
                break;
            case "add_if_min":
                user.add_if_min(command);
                break;
            case "clear":
                user.clear(command);
                break;
            case "filter_starts_with_name":
                user.filter_starts_with_name(command);
                break;
            case "head":
                user.head(command);
                break;
            case "info":
                user.info(command);
                break;
            case "print_descending":
                user.print_descending(command);
                break;
            case "print_field_ascending_mood":
                user.print_field_ascending_mood(command);
                break;
            case "remove_by_id":
                user.remove_by_id(command);
                break;
            case "remove_head":
                user.remove_head(command);
                break;
            case "show":
                user.show(command);
                break;
            case "help":
                user.help(command);
                break;
            case "exit":
                user.save(command);
                break;
            case "execute_script":
                user.execute_script(command);
                break;
            case "update":
                user.update(command);
                break;
            default:
                UdpServer.ans.append("Такой команды не существует, повторите ввод").append("\n");
        }
    }

    public static HumanBeing createObject(Command command, HashSet<Long> Id) {
        String[] args = command.getArgs();
        Filler fill = new Filler();
        HumanBeing element = new HumanBeing();
        element.setName(fill.fillName(args[1]));
        element.setCreationDate(LocalDate.now());
        element.setCoordinates(fill.fillCoordinates(args[2], args[3]));
        element.setRealHero(fill.isHero(args[4]));
        element.setHasToothpick(fill.tooth(args[5]));
        element.setImpactSpeed(fill.fillSpeed(args[6]));
        element.setWeaponType(fill.fillWeapon(args[7]));
        element.setMood(fill.fillMood(args[8]));
        element.setCar(fill.isCoolCar(args[9]));
        return element;
    }




}
