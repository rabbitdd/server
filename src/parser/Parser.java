package parser;

import Data.Source;
import human.HumanBeing;
import java.util.ArrayDeque;

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
}
