package persistence;

import model.Key;
import model.Keychain;

import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// A reader that allows for data stored in JSON file to be loaded
public class JsonReader {
    private String source;

    // EFFECTS: Constructor allows reader to read from original source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: Reads the keychain from file and returns it;
    // Throws the IOException if an error occurs reading data from file
    public Keychain read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseKeychain(jsonObject);
    }

    // EFFECTS: Reads the source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: Parses keychain from JSON object and returns it
    private Keychain parseKeychain(JSONObject jsonObject) {
        Keychain kc = new Keychain();
        addKeys(kc, jsonObject);
        return kc;
    }

    /*
    MODIFIES: kc
    EFFECTS: Method parses keys from JSON object and adds them to keychain
     */
    private void addKeys(Keychain kc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("keys");
        for (Object json : jsonArray) {
            JSONObject nextKey = (JSONObject) json;
            addKey(kc, nextKey);
        }
    }

    /*
    MODIFIES: kc
    EFFECTS: Parses key from JSON object and adds it to keychain
     */
    private void addKey(Keychain kc, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String colour = jsonObject.getString("colour");
        String shape = jsonObject.getString("shape");
        String location = jsonObject.getString("location");

        JSONArray locationLog = jsonObject.getJSONArray("locationLog");

        ArrayList<String> updatedLog = new ArrayList<>();
        for (Object l: locationLog) {
            String elementLocation = (String) l;
            updatedLog.add(elementLocation);
        }

        Key key = new Key(name, colour, shape, location);
        kc.addKey(key);

        key.setLocationLog(updatedLog);
    }
}
