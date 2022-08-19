package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class KeyTest {

    private static Key houseKey;
    private static Key carKey;

    @BeforeEach
    public void setup() {
        this.houseKey = new Key("House Key", "Gold", "Hexagonal", "My Bedroom");
        this.carKey = new Key("Car Key", "Silver", "Circular", "My Kitchen");
    }

    @Test
    public void testKey() {
        assertEquals("House Key", houseKey.getName());
        assertEquals("Gold", houseKey.getColour());
        assertEquals("Hexagonal", houseKey.getShape());
        assertEquals("My Bedroom", houseKey.mostRecentLocation());

        ArrayList<String> sampleLocationLog = new  ArrayList<>();
        sampleLocationLog.add("My Bedroom");
        assertEquals(sampleLocationLog, houseKey.getLocationLog());

        assertEquals("Car Key", carKey.getName());
        assertEquals("Silver", carKey.getColour());
        assertEquals("Circular", carKey.getShape());
        assertEquals("My Kitchen", carKey.mostRecentLocation());


        ArrayList<String> testLocationLog = new  ArrayList<>();
        testLocationLog.add("My Kitchen");
        assertEquals(testLocationLog, carKey.getLocationLog());
    }

    @Test
    public void testUpdateLocation() {
        assertEquals(1, houseKey.getLocationLog().size());
        assertEquals("My Bedroom", houseKey.mostRecentLocation());

        assertTrue(houseKey.updateLocation(houseKey,"My Kitchen"));
        assertEquals("My Kitchen", houseKey.mostRecentLocation());
        assertEquals(2, houseKey.getLocationLog().size());

        ArrayList<String> testLocationLog = new  ArrayList<>();
        testLocationLog.add("My Bedroom");
        testLocationLog.add("My Kitchen");

        assertEquals(testLocationLog, houseKey.getLocationLog());
    }

    @Test
    public void testMostRecentLocation() {
        assertEquals("My Bedroom", houseKey.mostRecentLocation());

        assertTrue(houseKey.updateLocation(houseKey,"My Kitchen"));
        assertEquals("My Kitchen", houseKey.mostRecentLocation());

        assertTrue(houseKey.updateLocation(houseKey,"My Car"));
        assertEquals("My Car", houseKey.mostRecentLocation());
    }
    @Test
    public void testGetName() {
        assertEquals("House Key", houseKey.getName());
    }

    @Test
    public void testGetColour() {
        assertEquals("Gold", houseKey.getColour());
    }

    @Test
    public void testGetShape() {
        assertEquals("Hexagonal", houseKey.getShape());
    }

    @Test
    public void testGetLocation() {
        assertEquals("My Bedroom", houseKey.mostRecentLocation());
    }

    @Test
    public void testGetLocationLog() {
        ArrayList<String> testLocationLog = new  ArrayList<>();
        testLocationLog.add("My Bedroom");

        assertEquals(testLocationLog, houseKey.getLocationLog());

        testLocationLog.add("My Kitchen");
        assertTrue(houseKey.updateLocation(houseKey,"My Kitchen"));

        assertEquals(testLocationLog, houseKey.getLocationLog());
    }

    @Test
    public void testGetKey() {
        assertEquals(this.houseKey, houseKey.getKey());
}
}