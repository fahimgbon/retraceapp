package persistence;

import model.Keychain;
import org.json.JSONObject;


import java.io.*;

// Represents a writer that writes JSON representation of keychain to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: Constructor creates writer to write to the chosen destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    /*
    MODIFIES: this
    EFFECTS: Opens the writer; throws FileNotFoundException if the destination file cannot be opened for writing
    */
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    /*
    MODIFIES: this
    EFFECTS: Writes JSON representation of keychain to file
    */
    public void write(Keychain kc) {
        JSONObject json = kc.toJson();
        saveToFile(json.toString(TAB));
    }

    /*
    MODIFIES: this
    EFFECTS: Closes writer
     */
    public void close() {
        writer.close();
    }

    /*
    MODIFIES: this
    EFFECTS: Writes the string to file
     */
    private void saveToFile(String json) {
        writer.print(json);
    }
}
