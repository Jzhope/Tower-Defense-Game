package ui;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

import model.GameMap;
import model.GameOverException;
import model.Tower;
import model.Enemy;

// Tower Defence Game App
public class GameApp {
    private static final String JSON_STORE = "./data/map.json";
    private GameMap map;
    private int roundNumber = 1;
    private Scanner scanner;
    private int isinbound = 1;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECT: construct a game map with 10 width and 5 height then setup and start
    // the game app
    public GameApp() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        map = new GameMap(10, 5);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
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
        getTowerCount();

        printDivider();
        System.out.println("How much difficulty do you want it to be? (from 1 to 4 level)");
        int input = scanner.nextInt();
        level(input);
    }

    private void getTowerCount() {
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
    }

    // EFFECTS: run the game
    public void runGame() {
        if (!map.hasEnemies()) {
            boolean keepGoing = true;
            String command = null;

            while (keepGoing) {
                displayMenu();
                command = scanner.next();
                command = command.toLowerCase();

                if (command.equals("q")) {
                    keepGoing = false;
                } else {
                    processCommand(command);
                }
            }
            System.out.println("Game over. Thanks for playing!");
        }
    }

    // EFFECTS: run the game
    public void startGame() {
        try {
            while (map.hasEnemies()) {
                map.updateMap();
            }
        } catch (GameOverException e) {
            System.out.println(e.getMessage());
            System.out.println("Game over. Thanks for playing!");
            scanner.close();
            System.exit(0);
        }

        runGame();
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("All enemies defeated! You win this round.");
        System.out.println("\t r - > Remove towers from the map");
        System.out.println("\t s - > Save the Map");
        System.out.println("\t l - > Load the Map");
        System.out.println("\t n - > start a new round");
        System.out.println("\t q - > quilt the game");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("r")) {
            removeTower();
        } else if (command.equals("s")) {
            saveMap();
        } else if (command.equals("l")) {
            loadMap();
        } else if (command.equals("n")) {
            newRound();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: get into a new round of game
    private void newRound() {
        roundNumber++;
        setupGame();
        startGame();
    }

    // MODIFIES: this
    // EFFECTS: add enemies according to the level
    private void level(int level) {
        Random random = new Random();
        for (int i = 0; i <= level + 4; i++) {
            int health = random.nextInt(50) + 50 * level;
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

    // EFFECTS: saves the map to file
    private void saveMap() {
        try {
            jsonWriter.open();
            jsonWriter.write(map);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads map from file
    private void loadMap() {
        try {
            map = jsonReader.read();
            System.out.println("Loaded map from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints out a line of dashes to act as a divider
    private void printDivider() {
        System.out.println("------------------------------------");
    }
}