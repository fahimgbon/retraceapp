package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

// Represents a key with a function, colour, shape, copy and lost status
public class Key implements Writable {
    private String name; // the key name
    private String colour; // the key colour
    private String shape; // the key shape
    private String location; // the key location
    private ArrayList<String> locationLog; // tracks the locations key has been

    /*
     * REQUIRES: function, colour, shape and location to have non-zero lengths
     * EFFECTS: name of the key is set to name;
     *          colour of the key is set to colour;
     *          shape of the key is set to shape;
     *          location of key is set to location;
     *          locationLog is created with inputted location
     */
    public Key(String name, String colour, String shape, String location) {
        this.name = name;
        this.colour = colour;
        this.shape = shape;
        this.location = location;
        this.locationLog = new ArrayList<>();
        this.locationLog.add(location);
    }

    /*
     * REQUIRES: location to have a non-zero length
     * MODIFIES: this
     * EFFECTS: Updates the key's location with the inputted string and adds that location
     *          as the last element of the locationLog. Method returns true as confirmation.
     */

    public boolean updateLocation(Key key, String location) {
        key.location = location;
        key.locationLog.add(location);
        return true;
    }

    /*
     * EFFECTS: Gets the location of the key, returning the value as the key's most recent location.
     */
    public String mostRecentLocation() {
        return this.location;
    }

    public String getName() {
        return this.name;
    }

    public String getColour() {
        return this.colour;
    }

    public String getShape() {
        return this.shape;
    }

    public ArrayList<String> getLocationLog() {
        return this.locationLog;
    }

    public Key getKey() {
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("colour", colour);
        json.put("shape", shape);
        json.put("location", location);
        json.put("locationLog", locationLog);
        return json;
    }

    public void setLocationLog(ArrayList log) {
        this.locationLog = log;
    }

}
