package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;


public class Keychain implements Writable {
    private ArrayList<Key> keychain;

    /*
     * EFFECTS: Constructs a new empty keychain.
     */
    public Keychain() {
        this.keychain = new ArrayList<>();
    }

    /*
     * EFFECTS: Gets the number of keys in the keychain through returning the list size.
     */
    public int numOfKeys() {
        return this.keychain.size();
    }

    /*
     * MODIFIES: this
     * EFFECTS: If the keychain is empty or the input key is unique, adds the key to the keychain and return true;
     *          otherwise, false.
     */
    public boolean addKey(Key key) {
        if (this.keychain.isEmpty()) {
            this.keychain.add(key);
            return true;
        } else {
            for (Key k: this.keychain) {
                if (!(k.getName().equals(key.getName()) && k.getColour().equals(key.getColour())
                        && k.getShape().equals(key.getShape())
                        && k.mostRecentLocation().equals(key.mostRecentLocation()))) {
                    this.keychain.add(key);
                    EventLog.getInstance().logEvent(
                            new Event(key.getName() + " was ADDED to your keychain"));
                    return true;
                }
            }
            return false;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: If the keychain is not empty and the input key is in the keychain, removes the key to from keychain
     *          and returns true; otherwise, false.
     */
    public boolean removeKey(Key key) {
        if (!this.keychain.isEmpty()) {
            for (Key k : this.keychain) {
                if (k.getName().equals(key.getName()) && k.getColour().equals(key.getColour())
                        && k.getShape().equals(key.getShape())
                        && k.mostRecentLocation().equals(key.mostRecentLocation())) {
                    this.keychain.remove(k);
                    EventLog.getInstance().logEvent(
                            new Event(key.getName() + " was REMOVED from your keychain"));
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * EFFECTS: Gets the keychain and returns it.
     */
    public ArrayList<Key> viewKeychain() {
        return this.keychain;
    }

    /*
     * EFFECTS: Checks to see if the keychain is empty and returns true if it is; otherwise, false.
     */
    public boolean isEmpty() {
        return this.keychain.size() == 0;
    }

    /*
     * MODIFIES: this, key
     * EFFECTS: If the keychain is not empty and the inputted key is found in the existing keychain,
     *          the method updates the location of the found key with the inputted location and returns true;
     *          otherwise, false.
     */
    public boolean findKey(Key key, String location) {
        if (!this.keychain.isEmpty()) {
            for (Key k: this.keychain) {
                if (k.getName().equals(key.getName()) && k.getColour().equals(key.getColour())
                        && k.getShape().equals(key.getShape())
                        && k.mostRecentLocation().equals(key.mostRecentLocation())) {
                    k.updateLocation(k, location);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("keys", keysToJson());
        return json;
    }

    // EFFECTS: Returns the things in this keychain as a JSON array
    private JSONArray keysToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Key k: keychain) {
            jsonArray.put(k.toJson());
        }

        return jsonArray;
    }

}
