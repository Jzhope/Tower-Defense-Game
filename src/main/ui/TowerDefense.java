package ui;

import model.GameMap;


import javax.swing.*;


public class TowerDefense extends JFrame {

    // Constructs main window
	// effects: sets up window in which Tower Defense game will be played
    public TowerDefense(GameMap map, int level) {
        setTitle("Tower Defense Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamePanel = new GamePanel(map,level);
        add(gamePanel);
    
        setSize(600, 500);
        setLocationRelativeTo(null); 
        pack();
        setVisible(true);
         
    }

     
}
