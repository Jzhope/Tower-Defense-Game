package ui;

import java.util.Scanner;
import java.util.Random;

import model.GameMap;
import model.Tower;
import model.Enemy;

// Tower Defence Game App
public class GameApp {
    private GameMap map;
    private int roundNumber = 1;
    private Scanner scanner;
    private int isinbound = 1;

    // EFFECT: construct a game map with 10 width and 5 height then setup and start
    // the game app
    public GameApp() {
        scanner = new Scanner(System.in);
        map = new GameMap(10, 5);
        setupGame();
        startGame();
    }

    // REQUIRES: x position >0 and less than 10; 1=<y-postion<=5
    // EFFECTS: add new enemies on the map
    public void addEnemy(int health, int x, int y) {
        map.addEnemy(new Enemy(health, x, y));
    }

    // MODIEFIES: this
    // EFFECTS: process user input to place the towers and enemies
    private void setupGame() {
        System.out.println("The Map has 10 columns and 5 rows.\n Round " + roundNumber + ":");
        System.out.println("Enter the number of towers you would like to add:");
        int towerCount = scanner.nextInt();
        for (int i = 0; i < towerCount; i++) {
            System.out.println("Enter the column where the tower is located:");
            int towerX = scanner.nextInt() - 1;
            System.out.println("Enter the column where the tower is located:");
            int towerY = scanner.nextInt() - 1;
            isinbound = map.addTower(new Tower(towerX, towerY), isinbound);
            if (isinbound == 0) {
                i = i - 1;
            }
        }

        printDivider();
        System.out.println("How much difficulty do you want it to be? (from 1 to 4 level)");
        int input = scanner.nextInt();
        level(input);
        printDivider();
    }

    // EFFECTS: run the game
    public void startGame() {

        while (map.hasEnemies()) {
            map.updateMap();
        }
        if (!map.hasEnemies()) {
            newRound();
        }
    }

    // MODIFIES: this
    // EFFECTS: get into a new round of game & remove unnecessary tower
    private void newRound() {
        System.out.println("All enemies defeated! You win this round.");
        System.out.println("Would you like to remove any towers from the map? (yes/no)");
        String removeTowerResponse = scanner.next();

        if (removeTowerResponse.equals("yes")) {
            removeTower();
        }

        System.out.println("Would you like to start a new round? (yes/no)");

        String response = scanner.next();
        if (response.equals("yes")) {
            roundNumber++;
            setupGame();
            startGame();
        } else {
            System.out.println("Game over. Thanks for playing!");
            scanner.close();
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: add enemies according to the level
    private void level(int level) {
        Random random = new Random();
        for (int i = 0; i <= level + 4; i++) {
            int health = random.nextInt(100) + 100 * level;
            int enemyX = map.getWidth();
            int enemyY = random.nextInt(map.getHeight());
            addEnemy(health, enemyX, enemyY);
        }
    }

    // MODIFIES: this
    // EFFECTS: get every tower from the tower list
    public void getTower() {
        for (int i = 0; i < map.getTowers().size(); i++) {
            Tower tower = map.getTowers().get(i);
            System.out.println((i + 1) + ". Tower at ("
                    + tower.getX2() + "," + tower.getY2() + ")");
        }
    }

    // MODIFIES: this
    // EFFECTS: remove towers
    public void removeTower() {
        int towerIndex;
        do {
            getTower();
            System.out.println("Enter the number of the tower to remove (or 0 to skip):");
            towerIndex = scanner.nextInt();
            if (towerIndex > 0 && towerIndex <= map.getTowers().size()) {
                Tower towerToRemove = map.getTowers().get(towerIndex - 1);
                map.removeTower(towerToRemove);
                System.out.println("Removed tower at ("
                        + towerToRemove.getX2() + "," + towerToRemove.getY2() + ").");
            } else {
                System.out.println("No tower removed.");
            }
        } while (towerIndex != 0);
    }

    // EFFECTS: prints out a line of dashes to act as a divider
    private void printDivider() {
        System.out.println("------------------------------------");
    }
}