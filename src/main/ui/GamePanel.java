package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.Random;
import model.GameMap;
import model.GameOverException;
import model.Tower;

import persistence.JsonWriter;
import model.Enemy;
import model.Event;
import model.EventLog;

public class GamePanel extends JPanel {
    private static final String JSON_STORE = "./data/map.json";
    private GameMap map;
    private int gridWidth = 60;
    private int gridHeight = 60;
    private int rows = 5;
    private int cols = 10;
    private Image towerImage;
    private Image enemyImage;
    private int maxTowers;
    private int towerCount = 0;
    private int level;
    private Timer placementTimer;
    private Timer enemyMoveTimer;
    private boolean canPlaceTower;
    private boolean gameOver = false;
    private boolean enemyExist = false;
    private boolean gameOverTriggered = false;
    private JsonWriter jsonWriter;
    private JFrame frame; 
    // Tower Defence Game App game panel

    // EFFECT: construct a game panel
    public GamePanel(JFrame frame, GameMap map, int level) { 
        this.map = map;
        this.setPreferredSize(new Dimension(600, 350));
        this.setLayout(new BorderLayout());
        this.level = level;
        this.canPlaceTower = true;
        this.maxTowers = 2 + level;
        this.frame = frame;

        towerImage = new ImageIcon("tower.png").getImage();
        enemyImage = new ImageIcon("enemy.png").getImage();

        jsonWriter = new JsonWriter(JSON_STORE);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton exitButton = new JButton("Exit");
        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        saveTheTowers(saveButton);
        exitTheGame(exitButton);

        fiveSecTimer();
        startEnemyGeneration();
        startEnemyMovement();
        addMouseListener(mouseListener);
    }

    // EFFECTS: to see if there is any enemy exist.
    public boolean enemyCleared() {
        return !map.hasEnemies();
    }

    // EFFECTS: set up a five sec timer to stop placing towers after 5 sec
    private void fiveSecTimer() {
        placementTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canPlaceTower = false;
                System.out.println("You can no longer place towers.");
            }
        });
        placementTimer.setRepeats(false);
        placementTimer.start();
    }

    // EFFECTS: generate Enemies
    private void startEnemyGeneration() {
         
        Timer enemyGenerationTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpEnemies(level);
                ((Timer) e.getSource()).stop(); 
            }
        });
        enemyGenerationTimer.start(); 

    }

    // EFFECTS: enemy start moving
    private void startEnemyMovement() {
        enemyMoveTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    try {
                        map.updateMap(); // Attempt to update the map (move enemies)
                        repaint(); // Repaint the panel after the update
                    } catch (GameOverException ex) {
                        // If the exception is thrown (game over), handle it here
                        handleGameOver();
                    }
                }

            }
        });
        enemyMoveTimer.start();
    }

    // Effects: genrate the next menu when game is over
    private void gameOver() {
        if (!gameOverTriggered) {
            gameOverTriggered = true;

            gameOver = true;
            enemyMoveTimer.stop();
            placementTimer.stop();
            new Menu();
            System.out.print(gameOverTriggered);
        }
    }

    // MODIFies: exitButton
    // EffECTS: generate a new initial frame
    private void exitTheGame(JButton exitButton) {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printLog();
                System.exit(0);
            }
        });
    }

    // MODIFies: saveButton
    // EffECTS: save the map
    private void saveTheTowers(JButton saveButton) {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMap();
            }
        });
    }

    // EFFECTS: add Towers and remove towers by clicking the mouse

    private MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            int gridX = mouseX / gridWidth;
            int gridY = mouseY / gridHeight;

            if (gridX < 0 || gridX >= cols || gridY < 0 || gridY >= rows) {
                System.out.println("out of range");
                return;
            }

            if (!canPlaceTower) {
                System.out.println("You can no longer place towers.");
                return;
            }

            addTower(e, gridX, gridY);
            removeTower(e, gridX, gridY);
        }

        // MODIEFIES: this
        // EFFECTS: remove towers
        private void removeTower(MouseEvent e, int gridX, int gridY) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                for (Tower tower : map.getTowers()) {
                    if (tower.getX2() == gridX && tower.getY2() == gridY) {
                        map.removeTower(tower);
                        towerCount--;
                        repaint();
                        break;
                    }
                }
            }
        }

        // MODIEFIES: this
        // EFFECTS: add towers
        private void addTower(MouseEvent e, int gridX, int gridY) {
            if (e.getButton() == MouseEvent.BUTTON1 && towerCount < maxTowers) {
                Tower tower = new Tower(gridX, gridY);
                if (map.addTower(tower, 1) == 1) {
                    towerCount++;
                    repaint();
                }
            }
        }
    };

    // MODIEFIES: this
    // EFFECTS: set up enemies
    private void setUpEnemies(int level) {
        Random random = new Random();
        for (int i = 0; i <= level + 3; i++) {
            int health = random.nextInt(30) + 40 * level;
            int enemyX = map.getWidth();
            int enemyY = random.nextInt(map.getHeight());
            addEnemy(health, enemyX, enemyY);
        }

        enemyExist = true;
    }

    // MODIEFIES: this
    // EFFECTS: add enemies
    public void addEnemy(int health, int x, int y) {
        map.addEnemy(new Enemy(health, x, y));
    }

    // MODIEFIES: g
    // EFFECTS: paint the map
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        gridPanel(g);
        paintTowers(g);
        paintEnemies(g);
        paintAttackLines(g);
        replay();
    }

    // EFFECTS: replay when enemies are cleared
    private void replay() {
        if (enemyExist && enemyCleared()) {
            if (!gameOver) {
                gameOver();

                JOptionPane.showOptionDialog(
                        this,
                        "All enemies have been defeated! You win.",
                        "Game Over",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[] { "OK" },
                        "OK"); 
                frame.dispose();
            }
        }

    }

    // EFFECTS: replay when enemies reach the button
    private void handleGameOver() {
        if (!gameOverTriggered) {
            gameOverTriggered = true;
            gameOver = true;
            enemyMoveTimer.stop();
            placementTimer.stop();
            new Menu();
            JOptionPane.showOptionDialog(
                    this,
                    "Game Over: Enemy has reached the base!",
                    "Game Over",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[] { "OK" },
                    "OK"); 
            frame.dispose();
        }
    }

    // Draw the towers
    // modifies: g
    // effects: draws the towers onto g
    private void paintTowers(Graphics g) {
        for (Tower tower : map.getTowers()) {
            int x = tower.getX2() * gridWidth;
            int y = tower.getY2() * gridHeight;
            g.drawImage(towerImage, x, y, gridWidth, gridHeight, this);
        }
    }

    // Draw the enemies
    // modifies: g
    // effects: draws the enemies onto g
    private void paintEnemies(Graphics g) {
        for (Enemy enemy : map.getEnemies()) {
            int x = enemy.getX1() * gridWidth;
            int y = enemy.getY1() * gridHeight;

            g.drawImage(enemyImage, x, y, gridWidth, gridHeight, this);

            for (Tower tower : map.getTowers()) {
                if (tower.isInRange(enemy)) {
                    int towerX = tower.getX2() * gridWidth + gridWidth / 2;
                    int towerY = tower.getY2() * gridHeight + gridHeight / 2;
                    g.setColor(Color.RED);
                    g.drawLine(towerX, towerY, (x) + gridWidth / 2, y + gridHeight / 2);
                }
            }
        }
    }

    // Draw the line
    // modifies: g
    // effects: draws attack lines between tower and enemy onto g
    private void paintAttackLines(Graphics g) {
        for (Tower tower : map.getTowers()) {
            for (Enemy enemy : map.getEnemies()) {
                if (tower.isInRange(enemy)) {
                    int towerX = tower.getX2() * gridWidth + gridWidth / 2;
                    int towerY = tower.getY2() * gridHeight + gridHeight / 2;
                    int enemyX = enemy.getX1() * gridWidth + gridWidth / 2;
                    int enemyY = enemy.getY1() * gridHeight + gridHeight / 2;
                    g.setColor(Color.RED);
                    g.drawLine(towerX, towerY, enemyX, enemyY);
                }
            }
        }
    }

    // Draw the grids
    // modifies: g
    // effects: draws grids onto g
    private void gridPanel(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= cols; i++) {
            g.drawLine(i * gridWidth, 0, i * gridWidth, gridHeight * rows);
        }
        for (int i = 0; i <= rows; i++) {
            g.drawLine(0, i * gridHeight, gridWidth * cols, i * gridHeight);
        }
    }

    // effects: save the map
    private void saveMap() {
        System.out.println("Saving the game...");
        try {
            jsonWriter.open();
            jsonWriter.write(map);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // effects: to see if it is really game over
    public boolean getGameOver() {
        return gameOver;
    }

    // EFFECTS: print all the event in the console
    public void printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n\n");
        }
    }
}
