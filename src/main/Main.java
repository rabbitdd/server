package main;

import command.*;
import human.HumanBeing;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

import static Data.Source.GSON;
import static Data.Source.path_in;

public class Main {

    public static void main(String[] args) {
        try {
            UdpServer.log.info("Начало работы");
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(path_in));
            Reader jsonReader = new InputStreamReader(input);
            ArrayDeque<HumanBeing> HumanityJson;
            HashSet<Long> Id = new HashSet<>();
            ArrayDeque<HumanBeing> Humanity = new ArrayDeque<>();
            Collection collection = new Collection();
            HumanBeing person = new HumanBeing();
            HashSet<String> set = new HashSet<>();
            ArrayList<HumanBeing> SortList = new ArrayList<>();

            HumanityJson = GSON.fromJson(jsonReader, ArrayDeque.class);
            for (Object Human : HumanityJson) {
                person = GSON.fromJson(String.valueOf(Human), HumanBeing.class);
                Humanity.add(person);
                Id.add(person.getId());
            }
            User user = new User(
                    new Add(collection, Humanity, person, Id),
                    new Add_if_min(collection, Humanity, Id, person),
                    new Clear(Humanity, collection),
                    new Filter_starts_with_name(collection, Humanity),
                    new Head(collection, Humanity),
                    new Info(collection, Humanity),
                    new Print_descending(collection, Humanity, SortList),
                    new Print_field_ascending_mood(collection, Humanity, SortList),
                    new Remove_by_id(collection, Humanity),
                    new Remove_head(collection, Humanity),
                    new Show(collection, Humanity),
                    new Help(collection),
                    new Save(collection, Humanity),
                    new ExecuteScript(collection, set, Id),
                    new Update(collection, Humanity, person)
            );
            Application app = new Application(person, Humanity, Id, collection, SortList, set, user);
            UdpServer server = new UdpServer(app, Humanity);
            UdpServer.log.info("Запуск сервера");
            server.run();
        } catch (Exception e) {
            Path file = Paths.get(path_in);
            if (Files.notExists(file)) {
                UdpServer.ans.append("Файла с именем ").append(path_in).append(" не существует").append("\n");
            } else if (!Files.isReadable(file)) {
                UdpServer.ans.append("У вас нет прав на чтение файла ").append(path_in).append("\n");
            }
        }

    }
}
