package parser;

import Data.Source;
import human.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.concurrent.locks.ReadWriteLock;

import static Data.Source.GSON;

/**
 * Класс Parser
 * @author Мишанин Никита
 */
public class Parser implements Source {

    public static byte[] parsToJson(ArrayDeque<HumanBeing> Humanity) {
        String json = GSON.toJson(Humanity);
        byte[] buffer = json.getBytes();
        return buffer;
    }

    public static void setCollection(ArrayDeque<HumanBeing> Humanity, Connection connection, ReadWriteLock lock) {
        //lock.readLock().lock();
        try (Statement stat = connection.createStatement()){
            ResultSet data = stat.executeQuery("SELECT * FROM People");
            while (data.next()) {
                HumanBeing human = new HumanBeing();
                //System.out.println(data.getString(1));
                human.setName(data.getString(1));
                human.setId(data.getLong(2));
                human.setCreationDate(data.getDate(3));
                human.setRealHero(data.getBoolean(4));
                human.setHasToothpick(data.getBoolean(5));
                human.setImpactSpeed(data.getInt(6));
                switch (data.getString(7)) {
                    case "HAMMER": human.setWeaponType(WeaponType.HAMMER);
                    case "AXE": human.setWeaponType(WeaponType.AXE);
                    case "SHOTGUN": human.setWeaponType(WeaponType.SHOTGUN);
                    case "RIFLE": human.setWeaponType(WeaponType.RIFLE);
                    case "BAT": human.setWeaponType(WeaponType.BAT);
                }
                switch (data.getString(8)) {
                    case "LONGING": human.setMood(Mood.LONGING);
                    case "GLOOM": human.setMood(Mood.GLOOM);
                    case "CALM": human.setMood(Mood.CALM);
                    case "RAGE": human.setMood(Mood.RAGE);
                    case "FRENZY": human.setMood(Mood.FRENZY);
                }
                human.setCar(new Car(data.getBoolean(9)));
                human.setCoordinates(new Coordinates(data.getFloat(10), data.getInt(11)));
                //System.out.println(human.getName());
                Humanity.add(human);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //lock.readLock().unlock();
        }
    }
}
