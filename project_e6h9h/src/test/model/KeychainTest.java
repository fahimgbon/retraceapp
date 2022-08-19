package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class KeychainTest {
    private static Key houseKey;
    private static Key carKey;
    private static Key mailboxKey;
    private static Keychain keychain;

    @BeforeEach
    public void setup() {
        this.houseKey = new Key("House Key", "Gold", "Hexagonal", "My Bedroom");
        this.carKey = new Key("Car Key", "Silver", "Circular", "My Kitchen");
        this.mailboxKey = new Key("Mailbox Key", "Rose Gold", "Triangular", "My Bathroom");

        this.keychain = new Keychain();
    }

    @Test
    public void testKeychain() {
        assertEquals(0, this.keychain.numOfKeys());
        assertTrue(this.keychain.isEmpty());
    }

    @Test
    public void testNumOfKeys() {
        assertTrue(this.keychain.addKey(houseKey));
        assertEquals(1, this.keychain.numOfKeys());

        assertTrue(this.keychain.addKey(carKey));
        assertEquals(2, this.keychain.numOfKeys());
    }

    @Test
    public void testAddKey() {
        assertTrue(this.keychain.addKey(houseKey));
        assertEquals(1, this.keychain.numOfKeys());
        assertFalse(this.keychain.addKey(houseKey));

        Key testKey1 = new Key("House Key", "Gold", "Hexagonal", "My Bedroom");
        assertFalse(this.keychain.addKey(testKey1));

        Key testKey2 = new Key("House", "Gold", "Hexagonal", "My Bedroom");
        assertTrue(this.keychain.addKey(testKey2));

        Key testKey3 = new Key("House Key", "Silver", "Hexagonal", "My Bedroom");
        assertTrue(this.keychain.addKey(testKey3));

        Key testKey4 = new Key("House Key", "Gold", "Circular", "My Bedroom");
        assertTrue(this.keychain.addKey(testKey4));

        Key testKey5 = new Key("House Key", "Gold", "Hexagonal", "My Room");
        assertTrue(this.keychain.addKey(testKey5));

        Key testKey6 = new Key("House Key", "Gold", "Hexagonal", "My Bedroom");
        testKey6.updateLocation(testKey6, "YMCA");
        assertTrue(this.keychain.addKey(testKey6));
        assertEquals(2,
                this.keychain.viewKeychain().get(this.keychain.viewKeychain().size() - 1).getLocationLog().size());

        assertTrue(this.keychain.addKey(carKey));
        assertEquals(7, this.keychain.numOfKeys());

    }

    @Test
    public void testRemoveKey() {
        assertFalse(this.keychain.removeKey(mailboxKey));

        assertTrue(this.keychain.addKey(houseKey));
        assertTrue(this.keychain.addKey(carKey));
        assertEquals(2, this.keychain.numOfKeys());

        assertTrue(this.keychain.removeKey(carKey));
        assertEquals(1, this.keychain.numOfKeys());

        Key testKey1 = new Key("House Key", "Gold", "Hexagonal", "My Bedroom");
        assertTrue(this.keychain.removeKey(testKey1));

        assertTrue(this.keychain.addKey(carKey));

        Key testKey2 = new Key("Car", "Silver", "Circular", "My Kitchen");
        assertFalse(this.keychain.removeKey(testKey2));

        Key testKey3 = new Key("Car Key", "Gold", "Circular", "My Kitchen");
        assertFalse(this.keychain.removeKey(testKey3));

        Key testKey4 = new Key("Car Key", "Silver", "Hexagonal", "My Kitchen");
        assertFalse(this.keychain.removeKey(testKey4));

        Key testKey5 = new Key("Car Key", "Silver", "Circular", "My Bathroom");
        assertFalse(this.keychain.removeKey(testKey5));

        Key testKey6 = new Key("Car Key", "Silver", "Circular", "My Kitchen");
        assertTrue(this.keychain.removeKey(testKey6));
        assertTrue(this.keychain.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(this.keychain.isEmpty());

        assertTrue(this.keychain.addKey(houseKey));
        assertFalse(this.keychain.isEmpty());
    }

    @Test
    public void testViewKeychain() {
        ArrayList<Key> sampleKeychain = new ArrayList<>();

        sampleKeychain.add(houseKey);
        assertTrue(this.keychain.addKey(houseKey));
        assertEquals(sampleKeychain, keychain.viewKeychain());

        sampleKeychain.add(carKey);
        assertTrue(this.keychain.addKey(carKey));
        assertEquals(sampleKeychain, keychain.viewKeychain());
    }

    @Test
    public void testFindKey() {
        assertFalse(this.keychain.findKey(houseKey, "My Kitchen"));

        assertTrue(this.keychain.addKey(houseKey));
        assertTrue(this.keychain.addKey(carKey));

        assertTrue(this.keychain.findKey(houseKey, "My Kitchen"));
        assertEquals("My Kitchen", houseKey.mostRecentLocation());
        assertEquals(2, houseKey.getLocationLog().size());

        assertFalse(this.keychain.findKey(mailboxKey, "My Kitchen"));
        assertEquals("My Bathroom", mailboxKey.mostRecentLocation());
        assertEquals(1, mailboxKey.getLocationLog().size());

        Key testKey1 = new Key("Car", "Silver", "Circular", "My Kitchen");
        assertFalse(this.keychain.findKey(testKey1, "YMCA"));

        Key testKey2 = new Key("Car Key", "Gold", "Circular", "My Kitchen");
        assertFalse(this.keychain.findKey(testKey2, "YMCA"));

        Key testKey3 = new Key("Car Key", "Silver", "Hexagonal", "My Kitchen");
        assertFalse(this.keychain.findKey(testKey3, "YMCA"));

        Key testKey4 = new Key("Car Key", "Silver", "Circular", "My Bathroom");
        assertFalse(this.keychain.findKey(testKey4, "YMCA"));

        Key testKey5 = new Key("Car Key", "Silver", "Circular", "My Kitchen");
        testKey5.updateLocation(testKey5, "My Basement");
        assertFalse(this.keychain.findKey(testKey5,"YMCA"));

        Key testKey6 = new Key("Car Key", "Silver", "Circular", "My Kitchen");
        assertTrue(this.keychain.findKey(testKey6, "YMCA"));
        // testKey1 becomes carKey because that is the object that is actually in the keychain
        assertEquals("YMCA", carKey.mostRecentLocation());
    }

}
