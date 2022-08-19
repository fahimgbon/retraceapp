package persistence;

import model.Key;
import model.Keychain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
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
    void testWriterInvalidFile() {
        try {
            Keychain kc = new Keychain();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyKeychain() {
        try {
            Keychain kc = new Keychain();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyKeychain.json");
            writer.open();
            writer.write(kc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyKeychain.json");
            kc = reader.read();
            assertEquals(0, kc.numOfKeys());
        } catch (IOException e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralKeychain() {
        try {
            Keychain kc = new Keychain();

            kc.addKey(new Key("House Key", "Gold","Hexagonal","My Bedroom"));

            Key carKey = new Key("Car Key", "Silver","Circular","My Bedroom");
            carKey.updateLocation(carKey,"My Kitchen");
            kc.addKey(carKey);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralKeychain.json");
            writer.open();
            writer.write(kc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralKeychain.json");
            kc = reader.read();
            ArrayList<Key> keys = kc.viewKeychain();
            assertEquals(2, keys.size());

            checkKey("House Key",
                    "Gold","Hexagonal","My Bedroom", houseKeyLog, keys.get(0));

            checkKey("Car Key", "Silver","Circular","My Kitchen", carKeyLog, keys.get(1));

        } catch (IOException e) {
            fail("This exception should not have been thrown");
        }
    }
}