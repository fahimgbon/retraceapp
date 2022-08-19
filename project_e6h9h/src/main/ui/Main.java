package ui;

import java.io.FileNotFoundException;

// EFFECTS: Main class used to run keychain application
public class Main {
    public static void main(String[] args) {
        try {
            new KeychainApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
