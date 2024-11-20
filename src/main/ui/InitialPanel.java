package ui;

 

import javax.swing.*;

import persistence.JsonReader;
import model.GameMap;
import java.io.IOException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 
public class InitialPanel extends JPanel {

    private static final String JSON_STORE = "./data/map.json";
    int difficulty = 1;
    private JsonReader jsonReader;
    private GameMap map;

 
    
    //EFFECTS: construct a panel to show menu
    public InitialPanel() {
        
         
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        setAlignmentX(Component.CENTER_ALIGNMENT);

         
        jsonReader = new JsonReader(JSON_STORE);
        map = new GameMap(10,5);
 
        JButton button1 = new JButton("Load the Towers");
        JButton button2 = new JButton("Level Choose");
        JButton button3 = new JButton("Start the Game");

        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadMap(button1);
        showdifficulty(button2);
        openWindow(button3);

        add(button1);
        add(Box.createVerticalStrut(20)); 
        add(button2);
        add(Box.createVerticalStrut(20));
        add(button3);
    }

    
    //effects:open window in which Space Invaders game will be played
    private void openWindow(JButton button3) {
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewWindow();
            }
        });
    }

    //effects: choose difficulty level
    private void showdifficulty(JButton button2) {
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDifficultySelector();
            }
        });
    }

    //effects: load the map
    private void loadMap(JButton button1) {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTheMap();
            }
        });
    }

    private void showDifficultySelector() {
        JSlider difficultySlider = new JSlider(1, 4, 1);
        difficultySlider.setMajorTickSpacing(1);
        difficultySlider.setPaintTicks(true);
        difficultySlider.setPaintLabels(true);

 
        int result = JOptionPane.showConfirmDialog(
                this, 
                difficultySlider, 
                "Select Difficulty", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            difficulty = difficultySlider.getValue();
            JOptionPane.showMessageDialog(this, "Selected Difficulty: " + difficulty);
        }
    }

    private void loadTheMap() {
        try {
            map = jsonReader.read();
            new TowerDefense(map, 1);
            System.out.println("Loaded map from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

 
    private void openNewWindow() {
        new TowerDefense(map,difficulty); 
    }

}