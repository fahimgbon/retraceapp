package persistence;

import model.Key;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkKey(String name, String colour, String shape, String location, ArrayList locationLog, Key key) {
        assertEquals(name, key.getName());
        assertEquals(colour, key.getColour());
        assertEquals(shape, key.getShape());
        assertEquals(location, key.mostRecentLocation());
        assertEquals(locationLog, key.getLocationLog());
    }
}
