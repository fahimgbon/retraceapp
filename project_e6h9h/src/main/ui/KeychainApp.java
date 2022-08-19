package ui;

import model.Key;
import model.Keychain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;

// EFFECTS: runs the keychain application
public class KeychainApp {

    private static final String JSON_STORE = "./data/keychain.json";
    private Keychain keychain;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the keychain application
    public KeychainApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        keychain = new Keychain();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        runKeychain();
    }

    // MODIFIES: this
    // EFFECTS: Receives user input and processes it
    private void runKeychain() {
        boolean continueProgram = true;
        String instruct = null;
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        while (continueProgram) {
            menuOptions();
            instruct = input.next();
            instruct = instruct.toUpperCase();

            if (instruct.equals("Q")) {
                continueProgram = false;
            } else {
                processInstruct(instruct);
            }
        }
        System.out.println("\nThank You For Using the Keychain App! :) ");
    }

    // MODIFIES: this
    // EFFECTS: Process the user's instruction
    private void processInstruct(String instruct) {
        if (instruct.equals("V")) {
            doViewKeychain();
        } else if (instruct.equals("A")) {
            doAddKey();
        } else if (instruct.equals("R")) {
            doRemoveKey();
        } else if (instruct.equals("U")) {
            isEmptyBeforeFindKey();
        } else if (instruct.equals("S")) {
            saveKeychain();
        } else if (instruct.equals("L")) {
            loadKeychain();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu options to the user
    private void menuOptions() {
        System.out.println("\nChoose from:");
        System.out.println("\tV -> View Keychain");
        System.out.println("\tA -> Add a Key to your Keychain");
        System.out.println("\tR -> Remove a Key from your Keychain");
        System.out.println("\tU -> Update the Location of a Key");
        System.out.println("\tS -> Save your keychain to file");
        System.out.println("\tL -> Load your keychain from file");
        System.out.println("\tQ -> Quit");
    }

    // EFFECTS: Checks to see if keychain is empty before calling seeKeychain to allow users to view their keychain
    public void doViewKeychain() {
        if (keychain.isEmpty()) {
            System.out.println("Sorry, your keychain is currently empty. Please add at least 1 key to your keychain");
        } else {
            ArrayList<Key> lok = this.keychain.viewKeychain();
            seeKeychain(lok);
        }
    }

    // EFFECTS: Shows user information (name, colour, shape, most recent location and location log)
    public void seeKeychain(ArrayList<Key> lok) {
        for (Key k : lok) {
            System.out.println("You have a " + k.getName() + " in your keychain."
                    + "\nThis key is " + k.getColour() + " and " + k.getShape()
                    + "\nIt was most recently located in/at: " + k.mostRecentLocation()
                    + "\nHere are all the places this key has been: " + k.getLocationLog() + "\n");
        }
    }

    // REQUIRES: String inputs of non-zero length
    // EFFECTS: Returns a key of the user's specifications to be used as necessary
    public Key createKey() {
        System.out.println("What is the name of the key?");
        String name = input.next();
        System.out.println("What is the colour of the key?");
        String colour = input.next();
        System.out.println("What is the shape of the key?");
        String shape = input.next();
        System.out.println("Where was the key most recently located?");
        String location = input.next();
        return new Key(name, colour, shape, location);
    }

    // MODIFIES: this
    // EFFECTS: Conducts the addition of a key if the keychain is empty or key is unique.
    public void doAddKey() {
        Key key = createKey();
        if (!keychain.addKey(key)) {
            System.out.println("Sorry, you already have that key on your keychain. Try Again.");
        } else {
            System.out.println("You have successfully added your key!");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Conducts the removal of a key if the key is in the keychain.
     */
    public void doRemoveKey() {
        Key key = createKey();
        if (!keychain.removeKey(key)) {
            System.out.println("Sorry, you don’t have that key in your keychain. Try again.");
        } else {
            keychain.removeKey(key);
            System.out.println("You have successfully removed that key!");
        }
    }

    // EFFECTS: Checks to see if keychain is empty before calling doFindKey to allow users to find the key
    // they're looking for
    public void isEmptyBeforeFindKey() {
        if (this.keychain.isEmpty()) {
            System.out.println("Sorry, your keychain is currently empty. Please add at least 1 key to your keychain");
        } else {
            doFindKey();
        }
    }

    /*
     * MODIFIES: this, key
     * EFFECTS: Conducts the removal of a key if the key is in the keychain.
     */
    public void doFindKey() {
        Key key = createKey();
        System.out.println("\nWhat location would you like to update your key with?");
        String location = input.next();
        if (keychain.findKey(key, location)) {
            System.out.println("\nThe location " + location
                    + " has been added to your " + key.getName());
        } else {
            System.out.println("\nSorry, that key is not currently in your keychain. Try again.");
        }
    }

    // EFFECTS: Saves the keychain to the file
    private void saveKeychain() {
        try {
            jsonWriter.open();
            jsonWriter.write(keychain);
            jsonWriter.close();
            System.out.println("Saved keychain to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    /*
    MODIFIES: this
    EFFECTS: Loads the keychain from file
     */
    private void loadKeychain() {
        try {
            keychain = jsonReader.read();
            System.out.println("Loaded your keychain from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}