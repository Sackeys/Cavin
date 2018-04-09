package integration;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JsonReader {

    // Source : https://www.mkyong.com/java/json-simple-example-read-and-write-json/
    private static String path = new File(".").getAbsolutePath() + "/src/test/java/integration/json/";

    public static <T> JSONArray toArray(List<T> arrayObj) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(arrayObj);
        return jsonArray;
    }

    public static JSONArray getArray(String fileName, List<String> keyToConvertToInt) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray)parser.parse(new FileReader(JsonReader.path + fileName + ".json"));

        // Convertit les champs Long en Int pour les propriétés spécifiées en paramètres
        for (Object obj : jsonArray) {
            for (String key : keyToConvertToInt) {
                JSONObject jsonObject = (JSONObject) obj;
                jsonObject.replace(key, ((Long) jsonObject.get(key)).intValue());
            }
        }

        return jsonArray;
    }
}
