package command;

import human.HumanBeing;
import human.Mood;
import human.WeaponType;
import main.Application;
import main.UdpServer;
import parser.Parser;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import static Data.Source.path_out;

public class Collection {

    void add(ArrayDeque<HumanBeing> Humanity, HumanBeing Person, HashSet<Long> Id) {
        for(int i = 1; i <= 10000; i++) {
            if(!Id.contains((long)i)) {
                Person.setId((long)i);
                Id.add((long)i);
                break;
            }
        }
        Person.setCreationDate(LocalDate.now());
        Humanity.add(Person);
        UdpServer.log.info("Выполнена команда add");
    }

    void add_if_min(ArrayDeque<HumanBeing> Humanity, HumanBeing Person, HashSet<Long> Id) {
        Optional<Integer> speed = Humanity.stream().map(HumanBeing::getImpactSpeed).min(Integer::compareTo);
        if (speed.isPresent() && Person.getImpactSpeed() > speed.get()) {
            Person.setCreationDate(LocalDate.now());
            add(Humanity, Person, Id);
        }
        UdpServer.log.info("Выполнена команда add_if_min");
    }

    void clear(ArrayDeque<HumanBeing> Humanity) {
        Humanity.clear();
        UdpServer.log.info("Выполнена команда clear");
    }

    void filter_starts_with_name(ArrayDeque<HumanBeing> Humanity, String name) {
        Humanity.forEach(humanBeing -> {
            if (humanBeing.getName().contains(name))
                UdpServer.ans.append(humanBeing.getName()).append(" ").append(humanBeing.getId()).append(" ").append(humanBeing.getMood());
        });
        UdpServer.log.info("Выполнена команда filter_starts_with_name");


    }

    void update(ArrayDeque<HumanBeing> Humanity, HumanBeing Person, long id) {
        Optional<HumanBeing> human_upd = Humanity.stream().filter(humanBeing -> humanBeing.getId() == id).findAny();
        if (human_upd.isPresent()) {
            HumanBeing human = human_upd.get();
            human.setName(Person.getName());
            human.setCreationDate(LocalDate.now());
            human.setImpactSpeed(Person.getImpactSpeed());
            human.setCar(Person.getCar());
            human.setCoordinates(Person.getCoordinates());
            human.setHasToothpick(Person.getHasToothpick());
            human.setCreationDate(LocalDate.now());
            switch (Person.getMood()) {
                case "LONGING": human.setMood(Mood.LONGING);
                case "GLOOM": human.setMood(Mood.GLOOM);
                case "CALM": human.setMood(Mood.CALM);
                case "RAGE": human.setMood(Mood.RAGE);
                case "FRENZY": human.setMood(Mood.FRENZY);
            }
            switch (Person.getWeaponType()) {
                case "HAMMER": human.setWeaponType(WeaponType.HAMMER);
                case "AXE": human.setWeaponType(WeaponType.AXE);
                case "SHOTGUN": human.setWeaponType(WeaponType.SHOTGUN);
                case "RIFLE": human.setWeaponType(WeaponType.RIFLE);
                case "BAT": human.setWeaponType(WeaponType.BAT);
            }
            human.setRealHero(Person.isRealHero());


        } else {
            UdpServer.ans.append("Элемента с id = ").append(id).append(" не существует").append("\n");
        }
        UdpServer.log.info("Выполнена команда update");
    }

    void head(ArrayDeque<HumanBeing> People) {
        try {
            UdpServer.ans.append(People.getFirst().getName()).append(" ").append(People.getFirst().getId());
        } catch (NoSuchElementException e) {
            UdpServer.ans.append("Ошибка! В коллекции нет ни единого элемента, сначала нужно что-то туда добавить" + "\n");
        }
        UdpServer.log.info("Выполнена команда head");
    }

    void info(ArrayDeque<HumanBeing> People) {
        UdpServer.ans.append("Тип коллекции: ").append(People.getClass()).append("\n").append("Размер: ").append(People.size()).append("\n");
        UdpServer.log.info("Выполнена команда info");
    }

    void print_descending(ArrayDeque<HumanBeing> People, ArrayList<HumanBeing> SortList) {
        SortList.addAll(People);
        SortList.sort(new Comparator<HumanBeing>() {
            @Override
            public int compare(HumanBeing o1, HumanBeing o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        UdpServer.ans.append("Элементы колекции в порядке убывания: Обратный Алфавитный по имени:" + "\n");
        SortList.forEach(x -> UdpServer.ans.append("Имя: ").append(x.getName()).append("\nID: ").append(x.getId()).append("\nОружие: ").append(x.getWeaponType()));
        SortList.clear();
        UdpServer.log.info("Выполнена команда print_descending");
    }

    void print_field_ascending_mood(ArrayDeque<HumanBeing> People, ArrayList<HumanBeing> Mood) {
        Mood.addAll(People);
        Mood.sort(new Comparator<HumanBeing>() {
            @Override
            public int compare(HumanBeing o1, HumanBeing o2) {
                return o1.getMood().compareTo(o2.getMood());
            }
        });
        UdpServer.ans.append("Поля Mood в порядке возрастания:" + "\n");
        Mood.forEach(x -> UdpServer.ans.append("Имя: ").append(x.getName()).append("\nID: ").append(x.getId()).append("\nНастроение: ").append(x.getMood()));
        Mood.clear();
        UdpServer.log.info("Выполнена команда print_field_ascending_mood");
    }

    void remove_by_id(ArrayDeque<HumanBeing> People, int id) {
        People.removeIf(x -> (x.getId() == (long)id));
        UdpServer.log.info("Выполнена команда remove_by_id");
    }

    void remove_head(ArrayDeque<HumanBeing> People) {
        try {
            HumanBeing Human = People.poll();
            UdpServer.ans.append(Human.getName()).append(" ").append(Human.getId());
        } catch (NullPointerException e) {
            UdpServer.ans.append("Ошибка! Вы не можете удалить элемент из головы, так как коллекция пуста, сначала добавьте что-то туда" + "\n");
        }
        UdpServer.log.info("Выполнена команда remove_head");
    }

    void show(ArrayDeque<HumanBeing> People) {
        if (People.isEmpty())
            UdpServer.ans.append("Коллекция пуста");
        else
            People.forEach(Man -> UdpServer.ans.append("Элемент коллекции: ").append(Man.getName()).append(", Id: ").append(Man.getId()).append(", Speed: ").append(Man.getImpactSpeed()).append("\n"));
        UdpServer.log.info("Выполнена команда show");
    }

    void help() {
            ///home/s284694/JavaLab5/Data/help.txt"
            Path help = Paths.get("/home/s284694/JavaLab5/Data/help.txt/");
            if (Files.notExists(help))
                UdpServer.ans.append("Не удается найти файл, проверьте что файл существует").append("\n");
            else
                if (!Files.isReadable(help))
                    UdpServer.ans.append("Ошибка прав доступа на файл").append("\n");
                else {
                    File Help = new File("/home/s284694/JavaLab6/Data/help.txt/");
                    BufferedReader fin = null;
                    try {
                        fin = new BufferedReader(new FileReader(Help));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String line;
                    try {
                        while ((line = fin.readLine()) != null)
                            UdpServer.ans.append(line).append("\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    UdpServer.ans.append("\n");
                }
        UdpServer.log.info("Выполнена команда help");

    }

    void save(ArrayDeque<HumanBeing> People) {
        BufferedOutputStream output;
        try {
            output = new BufferedOutputStream(new FileOutputStream(path_out));
            output.write(Parser.parsToJson(People));
            output.flush();
            output.close();
        } catch (FileNotFoundException e) {
            String ex = e.toString();
            if (ex.equals("java.io.FileNotFoundException: /home/s284694/JavaLab5/Data/Input.json (Permission denied)"))
                UdpServer.ans.append("У вас нет прав записи на файл Input.json, перейдите в каталог Data и измените права" + "\n");
            else
            if (ex.equals("java.io.FileNotFoundException: " + path_out + " Не удается найти указанный файл)"))
                UdpServer.ans.append("Системе не удается найти указанный файл, проверьте имя" + "\n");
        } catch (IOException e) {
            UdpServer.ans.append("Запись невозможна по причине отсутствия прав" + "\n");
        }
        UdpServer.log.info("Выполнена команда save");
    }


    void execute_script(String path, HashSet<String> set, HashSet<Long> Id) {
        try {
            FileInputStream file = new FileInputStream(path);
            Scanner input = new Scanner(file);
            set.add(path);
            while (input.hasNextLine()) {
                String[] args = input.nextLine().split(" ");
                Command command = new Command(args[0], Arrays.copyOfRange(args, 0, args.length));
                if (args[0].equals("execute_script")) {
                    if (!set.contains(args[1])) {
                        Application.Request(command);
                    } else {
                        UdpServer.ans.append("Скрипт содержит рекурсию!" + "\n");
                    }
                    set.removeIf(x -> x.equals(args[1]));
                } else {
                    if (command.getNameOfCommand().equals("add") || command.getNameOfCommand().equals("add_if_min") ||
                            command.getNameOfCommand().equals("update"))
                        command.setHuman(Application.createObject(command, Id));
                    Application.Request(command);
                }
            }
        } catch (Exception e) {
            Path file = Paths.get(path);
            if (Files.notExists(file)) {
                UdpServer.ans.append("Файла с именем ").append(path).append(" не существует").append("\n");
            } else if (!Files.isReadable(file)) {
                UdpServer.ans.append("У вас нет прав на чтение файла ").append(path).append("\n");
            }
        }
        UdpServer.log.info("Выполнена команда execute_script");
    }
}
