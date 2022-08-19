package ui;

import model.Key;
import model.Keychain;
import model.Event;
import model.EventLog;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import persistence.JsonReader;
import persistence.JsonWriter;

// EFFECTS: runs the keychain application GUI
public class VisualKeychainApp extends JFrame {
    // Main Panels
    private JPanel appPanel;
    private JPanel topPanel;
    private JPanel jsonPanel;
    private JPanel middlePanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel bottomPanel;

    // Buttons
    private JButton loadButton;
    private JButton saveButton;
    private JButton leftRemoveButton;
    private JButton middleAddButton;
    private JButton rightUpdateButton;

    // Key fields
    private JPanel firstField;
    private JPanel secondField;
    private JPanel thirdField;
    private JPanel fourthField;
    private JPanel fifthField;

    // JLabels and JTextFields for each key field
    private JLabel nameTitle;
    private JTextField keyName;
    private JLabel colourTitle;
    private JTextField keyColour;
    private JLabel shapeTitle;
    private JTextField keyShape;
    private JLabel locationTitle;
    private JTextField keyLocation;
    private JLabel locationLogTitle;
    private JLabel keyLocationLog;

    //JSON variables
    private static final String JSON_STORE = "./data/keychain.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Keychain Panel
    private JList keychainPanel;
    private Keychain keychain;
    private DefaultListModel keychainModel;
    private GridBagConstraints constraintTop;
    private GridBagConstraints constraintJson;
    private GridBagConstraints constraintMiddle;
    private GridBagConstraints constraintBottom;

    // Visual Component
    private static final String keyImageLocation = "./src/main/ui/key.jpeg";

    VisualKeychainApp() {
        super("My Keychain Project");
        visual();
        outerScreen();
        initialize();
        innerScreen();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showLog(EventLog.getInstance());
            }
        });
    }

    private void showLog(EventLog eventLog) {
        System.out.println("\nHere is the Event Log:");
        for (Event event: eventLog) {
            System.out.println("\n" + event.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates visual component (image of key)
    public void visual() {
        JWindow visualComponent = new JWindow();
        ImageIcon keyImage = new ImageIcon(keyImageLocation);
        JLabel keyImageLabel = new JLabel(keyImage);
        keyImageLabel.setIcon(keyImage);
        visualComponent.add(keyImageLabel);
        visualComponent.setBounds(750, 100, 463, 617);
        visualComponent.pack();
        visualComponent.repaint();
        visualComponent.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Instantiates the keychain, keychain model, JSON writer/reader and sets keyPanel module
    public void initialize() {
        keychain = new Keychain();
        keychainModel = new DefaultListModel();

        keychainPanel = new JList();
        keychainPanel.setModel(keychainModel);
        keychainPanel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        keychainPanel.setSelectedIndex(0);
        keychainPanel.addListSelectionListener(new KeySelection());

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: Sets the parameters for the outer screen (JFrame) and adds appPanel to outer screen
    public void outerScreen() {
        setLayout(new GridLayout(1, 1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(450,450));
        setSize(600,1200);
        setLocationRelativeTo(null);
        add(createAppPanel());
        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates the appPanel
    public JPanel createAppPanel() {
        appPanel = new JPanel();
        constraintTop = new GridBagConstraints();
        constraintJson = new GridBagConstraints();
        constraintMiddle = new GridBagConstraints();
        constraintBottom = new GridBagConstraints();
        appPanel.setLayout(new GridBagLayout());
        appPanel.setSize(600,1200);
        return appPanel;
    }

    // EFFECTS: Calls methods that create the 4 panels for appPanel
    public void innerScreen() {
        createTopPanel();
        createJsonPanel();
        createMiddlePanel();
        createBottomPanel();
    }

    // MODIFIES: this
    // EFFECTS: Creates the topPanel for appPanel
    public void createTopPanel() {
        topPanel = new JPanel();
        constraintTop.gridx = 0;
        constraintTop.gridy = 0;
        constraintTop.gridheight = 2;
        constraintTop.fill = GridBagConstraints.VERTICAL;
        topPanel.setLayout(new GridLayout(1, 1));
        appPanel.add(topPanel, constraintTop);
        topPanelContent();
    }

    // MODIFIES: this
    // EFFECTS: Creates app header and adds to topPanel
    public void topPanelContent() {
        final JLabel header = new JLabel("Retrace App");
        header.setFont(new Font("Sans Serif", Font.BOLD, 24));
        topPanel.add(header);
    }

    // MODIFIES: this
    // EFFECTS: Creates the JsonPanel for appPanel
    public void createJsonPanel() {
        jsonPanel = new JPanel();
        constraintJson.gridx = 0;
        constraintJson.gridy = 2;
        constraintJson.gridheight = 1;
        constraintJson.fill = GridBagConstraints.VERTICAL;
        jsonPanel.setLayout(new GridLayout(1, 5));
        appPanel.add(jsonPanel, constraintJson);
        jsonPanelContent();
    }

    // MODIFIES: this
    // EFFECTS: Creates Load and Save Buttons with appropriate functionality; adds to jsonPanel
    public void jsonPanelContent() {
        loadButton = new JButton("Load Keychain");
        jsonPanel.add(loadButton);
        loadButton.setActionCommand("Load Keychain");
        loadButton.addActionListener(new LoadListener());

        saveButton = new JButton("Save Keychain");
        jsonPanel.add(saveButton);
        saveButton.setActionCommand("Load Keychain");
        saveButton.addActionListener(new SaveListener());
    }

    // MODIFIES: this
    // EFFECTS: Creates the middlePanel for appPanel
    public void createMiddlePanel() {
        middlePanel = new JPanel();
        constraintMiddle.gridx = 0;
        constraintMiddle.gridy = 3;
        constraintMiddle.gridheight = 4;
        constraintMiddle.fill = GridBagConstraints.VERTICAL;
        middlePanel.setLayout(new GridLayout(1,2));
        appPanel.add(middlePanel, constraintMiddle);
        createLeftPanel();
        createRightPanel();
    }

    // MODIFIES: this
    // EFFECTS: Creates the leftPanel for middlePanel
    public void createLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(1, 1));
        middlePanel.add(leftPanel);
        leftPanel.add(keychainPanel);
    }

    // MODIFIES: this
    // EFFECTS: Creates the rightPanel for middlePanel
    public void createRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(5, 1));
        middlePanel.add(rightPanel);
        createFirstField();
        createSecondField();
        createThirdField();
        createFourthField();
        createFifthField();
    }

    // MODIFIES: this
    // EFFECTS: Creates the firstField for rightPanel; display key name content
    public void createFirstField() {
        firstField = new JPanel();
        firstField.setLayout(new GridLayout(2, 1));
        rightPanel.add(firstField);

        nameTitle = new JLabel("Name");
        firstField.add(nameTitle);
        keyName = new JTextField();
        firstField.add(keyName);
    }

    // MODIFIES: this
    // EFFECTS: Creates the secondField for rightPanell display key colour content
    public void createSecondField() {
        secondField = new JPanel();
        secondField.setLayout(new GridLayout(2, 1));
        rightPanel.add(secondField);

        colourTitle = new JLabel("Colour");
        secondField.add(colourTitle);
        keyColour = new JTextField();
        secondField.add(keyColour);
    }

    // MODIFIES: this
    // EFFECTS: Creates the thirdField for rightPanel; display key shape content
    public void createThirdField() {
        thirdField = new JPanel();
        thirdField.setLayout(new GridLayout(2, 1));
        rightPanel.add(thirdField);

        shapeTitle = new JLabel("Shape");
        thirdField.add(shapeTitle);
        keyShape = new JTextField();
        thirdField.add(keyShape);
    }

    // MODIFIES: this
    // EFFECTS: Creates the fourthField for rightPanel; display key location content
    public void createFourthField() {
        fourthField = new JPanel();
        fourthField.setLayout(new GridLayout(2, 1));
        rightPanel.add(fourthField);

        locationTitle = new JLabel("Location");
        fourthField.add(locationTitle);
        keyLocation = new JTextField();
        fourthField.add(keyLocation);
    }
    
    // MODIFIES: this
    // EFFECTS: Creates the fifthField for rightPanel; displays key locationLog content
    public void createFifthField() {
        fifthField = new JPanel();
        fifthField.setLayout(new GridLayout(1, 2));
        rightPanel.add(fifthField);

        locationLogTitle = new JLabel("Location Log: ");
        fifthField.add(locationLogTitle);
        keyLocationLog = new JLabel("Array");
        fifthField.add(keyLocationLog);
    }

    // MODIFIES: this
    // EFFECTS: Creates the bottomPanel for appPanel
    public void createBottomPanel() {
        bottomPanel = new JPanel();
        constraintBottom.gridx = 0;
        constraintBottom.gridy = 7;
        constraintBottom.gridheight = 1;
        constraintBottom.fill = GridBagConstraints.VERTICAL;
        bottomPanel.setLayout(new GridLayout(1, 5));
        appPanel.add(bottomPanel, constraintBottom);
        bottomPanelContent();
    }

    // MODIFIES: this
    // EFFECTS: Creates Add, Remove and Update Location Buttons with appropriate functionality; adds to bottomPanel
    public void bottomPanelContent() {
        leftRemoveButton = new JButton("Remove Key");
        bottomPanel.add(leftRemoveButton);
        leftRemoveButton.setActionCommand("Remove Key");
        leftRemoveButton.addActionListener(new RemoveListener());

        middleAddButton = new JButton("Add Key");
        bottomPanel.add(middleAddButton);
        middleAddButton.setActionCommand("Add Key");
        middleAddButton.addActionListener(new AddListener());

        rightUpdateButton = new JButton("Update Location");
        bottomPanel.add(rightUpdateButton);
        rightUpdateButton.setActionCommand("Update Location");
        rightUpdateButton.addActionListener(new UpdateListener());

    }

    // MODIFIES: this
    // EFFECTS: Resets the keychain model
    public void keychainReset() {
        keychainModel.removeAllElements();
        ArrayList<Key> lok = this.keychain.viewKeychain();
        for (Key k : lok) {
            keychainModel.addElement(k.getName());
        }
    }

    class LoadListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: Loads the keychain from file when action occurs -- button is clicked
        public void actionPerformed(ActionEvent e) {
            loadKeychain();
        }

        // MODIFIES: this
        // EFFECTS: Loads the keychain from file
        private void loadKeychain() {
            try {
                keychain = jsonReader.read();
                for (Key key: keychain.viewKeychain()) {
                    keychainModel.addElement(key.getName());
                }
                // System.out.println("Loaded your keychain from " + JSON_STORE);
            } catch (IOException e) {
                // System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    class SaveListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: Saves the keychain to the file when action occurs -- button is clicked
        public void actionPerformed(ActionEvent e) {
            saveKeychain();
        }

        // MODIFIES: this
        // EFFECTS: Saves the keychain to the file
        private void saveKeychain() {
            try {
                jsonWriter.open();
                jsonWriter.write(keychain);
                jsonWriter.close();
                // System.out.println("Saved keychain to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                // System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    class AddListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: Adds the key to the keychain when button is clicked
        public void actionPerformed(ActionEvent e) {
            Key k = new Key(keyName.getText(), keyColour.getText(), keyShape.getText(), keyLocation.getText());
            keychain.addKey(k);
            keychainReset();
        }
    }

    class RemoveListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: Removes the key from the keychain when button is clicked
        public void actionPerformed(ActionEvent e) {
            Key k = new Key(keyName.getText(), keyColour.getText(), keyShape.getText(), keyLocation.getText());
            keychain.removeKey(k);
            keychainReset();
        }
    }

    class UpdateListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: Updates the key location when button is clicked
        public void actionPerformed(ActionEvent e) {
            int keyIndex = keychainPanel.getSelectedIndex();
            if (keyIndex >= 0) {
                Key k = keychain.viewKeychain().get(keyIndex);
                k.updateLocation(k, keyLocation.getText());
                keychainReset();
            }
        }
    }

    class KeySelection implements ListSelectionListener {
        // MODIFIES: this
        // EFFECTS: If the keyIndex is >= show set the visual fields to match the key's fields
        public void valueChanged(ListSelectionEvent e) {
            int keyIndex = keychainPanel.getSelectedIndex();
            if (keyIndex >= 0) {
                Key k = keychain.viewKeychain().get(keyIndex);
                keyName.setText(k.getName());
                keyColour.setText(k.getColour());
                keyShape.setText(k.getShape());
                keyLocation.setText(k.mostRecentLocation());
                keyLocationLog.setText(k.getLocationLog().toString());
            }
        }
    }

}
