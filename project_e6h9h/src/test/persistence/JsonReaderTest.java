package persistence;

import model.Key;
import model.Keychain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {
    private static ArrayList houseKeyLog;
    private static ArrayList carKeyLog;

    @BeforeEach
    public void setup() {
        houseKeyLog = new ArrayList<>();
        assertEquals(0,houseKeyLog.size());
        houseKeyLog.add("My Bedroom");
        assertEquals(1,houseKeyLog.size());


        carKeyLog = new ArrayList<>();
        assertEquals(0,carKeyLog.size());
        carKeyLog.add("My Bedroom");
        assertEquals(1,carKeyLog.size());
        carKeyLog.add("My Kitchen");
        assertEquals(2,carKeyLog.size());
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/randomFileName.json");
        try {
            Keychain kc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyKeychain() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyKeychain.json");
        try {
            Keychain kc = reader.read();
            assertEquals(0, kc.numOfKeys());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralKeychain() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralKeychain.json");
        try {
            Keychain kc = reader.read();
            ArrayList<Key> keys = kc.viewKeychain();
            assertEquals(2, keys.size());

            checkKey("House Key", "Gold","Hexagonal","My Bedroom", houseKeyLog, keys.get(0));

            checkKey("Car Key", "Silver","Circular","My Kitchen", carKeyLog, keys.get(1));
        } catch (IOException e) {
            fail("Could not read from file");
        }
    }
}