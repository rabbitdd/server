package command;

import human.HumanBeing;
import human.Mood;
import human.WeaponType;
import jdbc.ObjectsDAO;
import jdbc.UserDAO;
import main.Application;
import main.Customer;
import main.UdpServer;
import parser.Parser;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static Data.Source.path_out;

public class Collection {

    void add(ArrayDeque<HumanBeing> Humanity, HumanBeing Person, HashSet<Long> Id, UserDAO user, ObjectsDAO objectsDAO, Command command,
             ReadWriteLock lock) {
        for(int i = 1; i <= 10000; i++) {
            if(!Id.contains((long)i)) {
                Person.setId((long)i);
                Id.add((long)i);
                break;
            }
        }
        Person.setCreationDate(new java.util.Date());
        lock.writeLock().lock();
        try {
            Person.setUsername(command.getUser().getLogin());
            System.out.println(user.create(Person));
            objectsDAO.setCommand(command);
            objectsDAO.create(command.getUser());

            Humanity.add(Person);
            Thread.sleep(2000);
        } catch (SQLException | InterruptedException e) {
            command.ans.append("Произошла ошибка во время добавления");
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
        UdpServer.log.info("Выполнена команда add");
    }

    void add_if_min(ArrayDeque<HumanBeing> Humanity, HumanBeing Person, HashSet<Long> Id, UserDAO user, ObjectsDAO objectsDAO, Command command,
                    ReadWriteLock lock) {
        Optional<Integer> speed = Humanity.stream().map(HumanBeing::getImpactSpeed).min(Integer::compareTo);
        if (speed.isPresent() && Person.getImpactSpeed() > speed.get()) {
            Person.setCreationDate(new java.util.Date());
            add(Humanity, Person, Id, user, objectsDAO, command, lock);
        }
        UdpServer.log.info("Выполнена команда add_if_min");
    }

    void clear(ArrayDeque<HumanBeing> Humanity, ObjectsDAO objectsDAO, Customer customer, Connection connection, ReadWriteLock lock,
               Command command) {
        lock.writeLock().lock();
        try {
            objectsDAO.clear(customer, command);
            Humanity.clear();
            Parser.setCollection(Humanity, connection, lock);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            command.ans.append("Выполнена команда clear");
            lock.writeLock().unlock();
        }
        //Humanity.clear();
        UdpServer.log.info("Выполнена команда clear");
    }

    void filter_starts_with_name(ArrayDeque<HumanBeing> Humanity, String name, Command command, ReadWriteLock lock) {
        lock.readLock().lock();
        try {
            Humanity.forEach(humanBeing -> {
                if (humanBeing.getName().contains(name)) {
                    //StringBuilder ans = new StringBuilder();
                    command.ans.append(humanBeing.getName()).append(" ").append(humanBeing.getId()).append(" ").append(humanBeing.getMood());
                    //command.setAns(ans);
                }
            });
        } finally {
            lock.readLock().unlock();
        }
        UdpServer.log.info("Выполнена команда filter_starts_with_name");


    }

    void update(ArrayDeque<HumanBeing> Humanity, HumanBeing Person, long id, ObjectsDAO objectsDAO, Customer user, Command command,
                ReadWriteLock lock) {
        lock.writeLock().lock();
        Optional<HumanBeing> human_upd = Humanity.stream().filter(humanBeing -> humanBeing.getId() == id).findAny();
        HumanBeing person = new HumanBeing();
        if (human_upd.isPresent()) {
            person.setName(Person.getName());
            person.setId(Person.getId());
            person.setCreationDate(new java.util.Date());
            person.setImpactSpeed(Person.getImpactSpeed());
            person.setCar(Person.getCar());
            person.setCoordinates(Person.getCoordinates());
            person.setHasToothpick(Person.getHasToothpick());
            person.setCreationDate(new java.util.Date());
            switch (Person.getMood()) {
                case "LONGING": person.setMood(Mood.LONGING); break;
                case "GLOOM": person.setMood(Mood.GLOOM); break;
                case "CALM": person.setMood(Mood.CALM); break;
                case "RAGE": person.setMood(Mood.RAGE); break;
                case "FRENZY": person.setMood(Mood.FRENZY); break;
            }
            switch (Person.getWeaponType()) {
                case "HAMMER": person.setWeaponType(WeaponType.HAMMER); break;
                case "AXE": person.setWeaponType(WeaponType.AXE); break;
                case "SHOTGUN": person.setWeaponType(WeaponType.SHOTGUN); break;
                case "RIFLE": person.setWeaponType(WeaponType.RIFLE); break;
                case "BAT": person.setWeaponType(WeaponType.BAT); break;
            }
            person.setRealHero(Person.isRealHero());
            try {
                if (objectsDAO.update(id, person, user, command)) {
                    HumanBeing human = human_upd.get();
                    human.setName(Person.getName());
                    human.setCreationDate(new java.util.Date());
                    human.setImpactSpeed(Person.getImpactSpeed());
                    human.setCar(Person.getCar());
                    human.setCoordinates(Person.getCoordinates());
                    human.setHasToothpick(Person.getHasToothpick());
                    human.setCreationDate(new java.util.Date());
                    switch (Person.getMood()) {
                        case "LONGING":
                            human.setMood(Mood.LONGING);
                        case "GLOOM":
                            human.setMood(Mood.GLOOM);
                        case "CALM":
                            human.setMood(Mood.CALM);
                        case "RAGE":
                            human.setMood(Mood.RAGE);
                        case "FRENZY":
                            human.setMood(Mood.FRENZY);
                    }
                    switch (Person.getWeaponType()) {
                        case "HAMMER":
                            human.setWeaponType(WeaponType.HAMMER); break;
                        case "AXE":
                            human.setWeaponType(WeaponType.AXE); break;
                        case "SHOTGUN":
                            human.setWeaponType(WeaponType.SHOTGUN); break;
                        case "RIFLE":
                            human.setWeaponType(WeaponType.RIFLE); break;
                        case "BAT":
                            human.setWeaponType(WeaponType.BAT); break;
                    }
                    human.setRealHero(Person.isRealHero());
                }


            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }



        } else {
            //StringBuilder ans = new StringBuilder();
            command.ans.append("Элемента с id = ").append(id).append(" не существует").append("\n");
            //command.setAns(ans);
        }
        UdpServer.log.info("Выполнена команда update");
    }

    void head(ArrayDeque<HumanBeing> People, ReadWriteLock lock, Command command) {
        lock.readLock().lock();
        //StringBuilder ans = new StringBuilder();
        try {
            command.ans.append(People.getFirst().getName()).append(" ").append(People.getFirst().getId());
            //command.setAns(ans);
        } catch (NoSuchElementException e) {
            command.ans.append("Ошибка! В коллекции нет ни единого элемента, сначала нужно что-то туда добавить" + "\n");
            //command.setAns(ans);
        } finally {
            lock.readLock().unlock();
        }
        UdpServer.log.info("Выполнена команда head");
    }

    void info(ArrayDeque<HumanBeing> People, ReadWriteLock lock, Command command) {
        lock.readLock().lock();
        try {
            command.ans.append("Тип коллекции: ").append(People.getClass()).append("\n").append("Размер: ").append(People.size()).append("\n");
            UdpServer.log.info("Выполнена команда info");
        } finally {
            lock.readLock().unlock();
        }
    }

    void print_descending(ArrayDeque<HumanBeing> People, ArrayList<HumanBeing> SortList, Command command, ReadWriteLock lock) {
        lock.readLock().lock();
        //StringBuilder ans = new StringBuilder();
        SortList.addAll(People);
        SortList.sort(new Comparator<HumanBeing>() {
            @Override
            public int compare(HumanBeing o1, HumanBeing o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        command.ans.append("Элементы колекции в порядке убывания: Обратный Алфавитный по имени:" + "\n");
        SortList.forEach(x -> command.ans.append("Имя: ").append(x.getName()).append("\nID: ").append(x.getId()).append("\nОружие: ").append(x.getWeaponType()).append("\n"));
        SortList.clear();
        //command.setAns(ans);
        UdpServer.log.info("Выполнена команда print_descending");
        lock.readLock().unlock();
    }

    void print_field_ascending_mood(ArrayDeque<HumanBeing> People, ArrayList<HumanBeing> Mood, Command command, ReadWriteLock lock) {
        lock.readLock().lock();
        //StringBuilder ans = new StringBuilder();
        Mood.addAll(People);
        Mood.sort(new Comparator<HumanBeing>() {
            @Override
            public int compare(HumanBeing o1, HumanBeing o2) {
                return o1.getMood().compareTo(o2.getMood());
            }
        });
        command.ans.append("Поля Mood в порядке возрастания:" + "\n");
        Mood.forEach(x -> command.ans.append("Имя: ").append(x.getName()).append("\nID: ").append(x.getId()).append("\nНастроение: ").append(x.getMood()).append("\n"));
        Mood.clear();
        //command.setAns(ans);
        UdpServer.log.info("Выполнена команда print_field_ascending_mood");
        lock.readLock().unlock();
    }

    void remove_by_id(ArrayDeque<HumanBeing> People, int id, UserDAO user, ObjectsDAO objectsDAO, Customer customer, Command command,
                      ReadWriteLock lock) {
        //StringBuilder ans = new StringBuilder();
        lock.writeLock().lock();

        try {
            if (objectsDAO.delete((long)id, customer, command)) {
                command.ans.append("Удален объект с id ").append(id);
                People.removeIf(x -> (x.getId() == (long) id));
            }
            //command.setAns(ans);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
        UdpServer.log.info("Выполнена команда remove_by_id");
    }

    void remove_head(ArrayDeque<HumanBeing> People, ObjectsDAO objectsDAO, Customer customer, Command command,
                     ReadWriteLock lock) {
        //StringBuilder ans = new StringBuilder();
        lock.writeLock().lock();
        try {
            HumanBeing Human = People.peek();
            Long id = Human.getId();
            if (objectsDAO.delete(id, customer, command)) {
                People.removeIf(x -> (x.getId() == (long) id));
                command.ans.append("\n").append(Human.getName()).append(" ").append(Human.getId());
            }

        } catch (NullPointerException | SQLException e) {
            command.ans.append("Ошибка! Вы не можете удалить элемент из головы, так как коллекция пуста, сначала добавьте что-то туда" + "\n");
        } finally {
            lock.writeLock().unlock();
        }
        //command.setAns(ans);
        UdpServer.log.info("Выполнена команда remove_head");
    }

    void show(ArrayDeque<HumanBeing> People, ReadWriteLock lock, Command command) {
        lock.readLock().lock();
        //StringBuilder ans = new StringBuilder();
        try {
            if (People.isEmpty())
                command.ans.append("Коллекция пуста");
            else
                People.forEach(Man -> command.ans.append("Элемент коллекции: ").append(Man.getName()).append(", Id: ").append(Man.getId()).append(", Speed: ").append(Man.getImpactSpeed()).append("\n"));
            UdpServer.log.info("Выполнена команда show");
            //command.setAns(ans);
        } finally {
            lock.readLock().unlock();
        }
    }

    void help(Command command) {
        //StringBuilder ans = new StringBuilder();
            ///home/s284694/JavaLab5/Data/help.txt"
            Path help = Paths.get("/home/s284694/JavaLab5/Data/help.txt/");
            if (Files.notExists(help))
                command.ans.append("Не удается найти файл, проверьте что файл существует").append("\n");
            else
                if (!Files.isReadable(help))
                    command.ans.append("Ошибка прав доступа на файл").append("\n");
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
                            command.ans.append(line).append("\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    command.ans.append("\n");
                    //command.setAns(ans);
                }
        command.ans.append("Выполнена команда help");
        UdpServer.log.info("Выполнена команда help");

    }

    /*void save(ArrayDeque<HumanBeing> People) {
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
    }*/


    void execute_script(String path, HashSet<String> set, HashSet<Long> Id, Command command, ReadWriteLock lock) {
        //lock.readLock().lock();
        lock.writeLock().lock();
        try {
            FileInputStream file = new FileInputStream(path);
            Scanner input = new Scanner(file);
            set.add(path);
            while (input.hasNextLine()) {
                String[] args = input.nextLine().split(" ");
                Command newCommand = new Command(args[0], Arrays.copyOfRange(args, 0, args.length));
                newCommand.setUser(command.getUser());
                if (args[0].equals("execute_script")) {
                    if (!set.contains(args[1])) {
                        Application.Request(newCommand);
                    } else {
                        command.ans.append("Скрипт содержит рекурсию!" + "\n");
                    }
                    set.removeIf(x -> x.equals(args[1]));
                } else {
                    if (newCommand.getNameOfCommand().equals("add") || newCommand.getNameOfCommand().equals("add_if_min") ||
                            newCommand.getNameOfCommand().equals("update"))
                        newCommand.setHuman(Application.createObject(newCommand, Id));
                    //lock.readLock().lock();
                    //lock.writeLock().lock();
                    try {
                        Application.Request(newCommand);
                    } finally {
                        //lock.readLock().unlock();
                        //lock.writeLock().unlock();
                    }
                }
            }
        } catch (Exception e) {
            Path file = Paths.get(path);
            if (Files.notExists(file)) {
                command.ans.append("Файла с именем ").append(path).append(" не существует").append("\n");
            } else if (!Files.isReadable(file)) {
                command.ans.append("У вас нет прав на чтение файла ").append(path).append("\n");
            }
        } finally {
            command.ans.append("Выполнена команда execute_script");
            lock.writeLock().unlock();
            //lock.readLock().unlock();
        }
        UdpServer.log.info("Выполнена команда execute_script");
    }

    void UpdateInterface(ArrayDeque<HumanBeing> People, Command command, ReadWriteLock lock) {
        lock.writeLock().lock();
        try {
            command.setPeople(People);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
