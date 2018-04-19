package integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JsonReader {

    /**
     * Moralité : JSON-simple, c'est de la BONNE GROSSE M***E.
     */

    // Source : https://www.mkyong.com/java/json-simple-example-read-and-write-json/
    private static String path = new File(".").getAbsolutePath() + "/src/test/java/integration/json/";

    public static <T> JSONArray toArray(List<T> arrayObj) throws ParseException {
        // Créé l'objet JsonArray
        JSONArray tempJsonArray = new JSONArray();
        tempJsonArray.addAll(arrayObj);

        // Convertit l'objet en texte JSON
        String jsonText = tempJsonArray.toJSONString();

        // Reparse en JsonArray
        // L'intérêt d'une telle manipulation est de convertir tous les types entiers en Long afin
        // d'avoir le format nL au sein de tous les JsonObject, et de pouvoir les comparer entre eux
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray)parser.parse(jsonText);
        return jsonArray;
    }

    public static <T> JSONObject toObject(T obj) throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String stringObject = mapper.writeValueAsString(obj);

        JSONParser parser = new JSONParser();
        JSONObject tempJsonObject = (JSONObject) parser.parse(stringObject);

        String jsonText = tempJsonObject.toJSONString();

        JSONObject jsonObject = (JSONObject) parser.parse(jsonText);

        return jsonObject;
    }


    public static JSONArray getArray(String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray)parser.parse(new FileReader(JsonReader.path + fileName + ".json"));
        return jsonArray;
    }

    public static JSONObject getObject(String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(JsonReader.path + fileName + ".json"));
        return jsonObject;
    }
}
