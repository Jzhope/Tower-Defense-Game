package persistence;

//import model.Category;
import model.Tower;
import model.GameMap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads map from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game map from file and returns it;
    // throws IOException if an error occurs reading data from file
    public GameMap read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameMap(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses map from JSON object and returns it
    private GameMap parseGameMap(JSONObject jsonObject) {
        int width = jsonObject.getInt("width");
        int height = jsonObject.getInt("height");
        GameMap gm = new GameMap(width,height);
        addTowers(gm, jsonObject);
        return gm;
    }

    // MODIFIES: gm
    // EFFECTS: parses towers from JSON object and adds them to game map
    private void addTowers(GameMap gm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("towers");
        for (Object json : jsonArray) {
            JSONObject nextTower = (JSONObject) json;
            addTower(gm, nextTower);   
        }
    }
    
    // MODIFIES: gm
    // EFFECTS: parses single tower from JSON object and adds it to GameMap
    private void addTower(GameMap gm, JSONObject towerJson) {
        int x = towerJson.getInt("The column of Tower");
        int y = towerJson.getInt("The row of Tower");
        Tower tower = new Tower(x, y);
        gm.addTower(tower, 1);   
    }
}
