package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

import model.GameMap;

public class Menu extends JFrame {
    int level;

    // Constructs main window
	// effects: sets up window in which menu will be rendered
    public Menu() {
         
        setTitle("Tower Defense Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 600);
        
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        JButton levelChooseButton1 = new JButton("Choose the level 1 to replay");
        JButton levelChooseButton2 = new JButton("Choose the level 2 to replay");
        JButton levelChooseButton3 = new JButton("Choose the level 3 to replay");
        JButton levelChooseButton4 = new JButton("Choose the level 4 to replay");
        JButton quitButton = new JButton("quit the game");

        addMenuTable(menu, levelChooseButton1, levelChooseButton2, levelChooseButton3, levelChooseButton4, quitButton);
        add(menu);

        menu.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelChooseButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelChooseButton2.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelChooseButton3.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelChooseButton4.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(menu);

        menuTable(levelChooseButton1, levelChooseButton2, levelChooseButton3, levelChooseButton4, quitButton);
          
        setLocationRelativeTo(null); 
        setVisible(true);
        pack();
    }

    private void addMenuTable(JPanel menu, JButton levelChooseButton1, JButton levelChooseButton2,
            JButton levelChooseButton3, JButton levelChooseButton4, JButton quitButton) {
        menu.add(levelChooseButton1);
        menu.add(levelChooseButton2);
        menu.add(levelChooseButton3);
        menu.add(levelChooseButton4);
        menu.add(quitButton);
    }

    private void menuTable(JButton levelChooseButton1, JButton levelChooseButton2, JButton levelChooseButton3,
            JButton levelChooseButton4, JButton quitButton) {
        levelChoose1(levelChooseButton1);

        levelChoose2(levelChooseButton2);

        levelChoose3(levelChooseButton3);

        levelChoose4(levelChooseButton4);

        quitGame(quitButton);
    }

    private void quitGame(JButton quitButton) {
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InitialFrame();
            }
        });
    }

    private void levelChoose4(JButton levelChooseButton4) {
        levelChooseButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewWindow(4);
            }
        });
    }

    private void levelChoose3(JButton levelChooseButton3) {
        levelChooseButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewWindow(3);
            }
        });
    }

    private void levelChoose2(JButton levelChooseButton2) {
        levelChooseButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewWindow(2);
            }
        });
    }

    private void levelChoose1(JButton levelChooseButton1) {
        levelChooseButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewWindow(1);
            }
        });
    }

    public void openNewWindow(int level) {
        GameMap map = new GameMap(10,5);
        new TowerDefense(map, level);
        this.dispose();
    }
}
