package main;

import command.*;
import command.Collection;
import human.HumanBeing;
import jdbc.ObjectsDAO;
import jdbc.Registration;
import jdbc.RegistrationDAO;
import jdbc.UserDAO;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static Data.Source.GSON;
import static Data.Source.path_in;
import parser.Parser;

public class Main {
    static ArrayList<Long> objectId = new ArrayList<>();
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/Human";
        String name = "postgres";
        String password = "12345aA";
        try {
            UdpServer.log.info("Начало работы");
            //BufferedInputStream input = new BufferedInputStream(new FileInputStream(path_in));
            //Reader jsonReader = new InputStreamReader(input);
            //ArrayDeque<HumanBeing> HumanityJson;
            HashMap<String, String> customers = new HashMap<>();

            HashSet<Long> Id = new HashSet<>();
            ArrayDeque<HumanBeing> Humanity = new ArrayDeque<>();
            Collection collection = new Collection();
            HumanBeing person = new HumanBeing();
            HashSet<String> set = new HashSet<>();
            ArrayList<HumanBeing> SortList = new ArrayList<>();
            ReadWriteLock lock = new ReentrantReadWriteLock();
            //HumanityJson = GSON.fromJson(jsonReader, ArrayDeque.class);
            //for (Object Human : HumanityJson) {
            //    person = GSON.fromJson(String.valueOf(Human), HumanBeing.class);
            //    Humanity.add(person);
             //   Id.add(person.getId());
            //}
            //System.out.println("sss");
            Connection connection = null;
            connection = DriverManager.getConnection(url, name, password);
            if (connection != null)
                System.out.println("Успешное подключение к бд");
            UserDAO UserDAO = new UserDAO(connection);
            RegistrationDAO registrationDAO = new RegistrationDAO(connection);
            Registration registration = new Registration(customers, registrationDAO);
            ObjectsDAO objectsDAO = new ObjectsDAO(connection);
            User user = new User(
                    new Add(collection, Humanity, person, Id, UserDAO, objectsDAO, lock),
                    new Add_if_min(collection, Humanity, Id, person, UserDAO, objectsDAO, lock),
                    new Clear(Humanity, collection, objectsDAO, connection, lock),
                    new Filter_starts_with_name(collection, Humanity, lock),
                    new Head(collection, Humanity, lock),
                    new Info(collection, Humanity, lock),
                    new Print_descending(collection, Humanity, SortList, lock),
                    new Print_field_ascending_mood(collection, Humanity, SortList, lock),
                    new Remove_by_id(collection, Humanity, UserDAO, objectsDAO, lock),
                    new Remove_head(collection, Humanity, objectsDAO, lock),
                    new Show(collection, Humanity, lock),
                    new Help(collection),
                    new Save(collection, Humanity),
                    new ExecuteScript(collection, set, Id, lock),
                    new Update(collection, Humanity, person, objectsDAO, lock), new UpdateInterface(collection, Humanity, lock)
            );
            Application app = new Application(person, Humanity, Id, collection, SortList, set, user);
            //Client client = new Client(channel, 8080);
            UdpServer server = new UdpServer();

            UdpServer.log.info("Запуск сервера");
            Parser.setCollection(Humanity, connection, lock);
            server.run();
            connection.close();
        } catch (Exception e) {

        }

    }
}
