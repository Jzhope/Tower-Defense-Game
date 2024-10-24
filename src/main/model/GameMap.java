package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.ArrayList;


// Represents the background that allows the enemy and tower to stay
public class GameMap implements Writable {
    private int width;            // The width of the map
    private int height;           // The height of the map
    private List<Tower> towers;  // The list of Towers that can stay in the map
    private List<Enemy> enemies; // The list of Enemies that can stay in the map
    
    //REQUIRES: the width and the height should be positive
    //EFFECTS: construct a map that can take in enemies and towers
    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        towers = new ArrayList<>();
        enemies = new ArrayList<>();
    }

    //EFFECTS: return true if the object is in the map
    private boolean isWithinBounds(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return true;
        } else {
            return false;
        }
    }
    
    // MODIFIES: this
    // EFFECT: add new towers and find if it is in the bound
    public int addTower(Tower tower,int isinbound) {
        if (isWithinBounds(tower.getX2(), tower.getY2())) {
            this.towers.add(tower);
            System.out.println("Placed " + tower.getClass().getSimpleName() 
                    + " at (" + tower.getX2() + "," + tower.getY2() + ")");
            return isinbound = 1;
        } else {
            System.out.println("Tower position (" + tower.getX2() + 1 + ","  
                    + tower.getY2() + 1 + ") is out of bounds! Try another position!");
            return isinbound = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new enemy to the map and prints its spawn location 
    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
        System.out.println("Spawned Enemy at (" + enemy.getX1() + "," 
                + enemy.getY1() + ")");
    }
    
    public void towersAttack() {
        for (Tower tower : towers) {
            for (Enemy enemy : enemies) {
                if (enemy.isAlive() && tower.isInRange(enemy)) {
                    tower.attack(enemies);
                }
            }
        }
    }

    // MODIEFIES: this
    // EFFECTS: update the towers' and enemies' information on the map
    public void updateMap() {
        System.out.println("\n--- Map Update ---");
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                enemy.move();
            }
        }
         
        towersAttack();

        for (Enemy enemy : enemies) {
            if (enemy.reachedBase()) {
                throw new GameOverException("Enemy reached the base! Game over, you lose.");
            }
        }

        List<Enemy> aliveEnemies = new ArrayList<>();
        
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                aliveEnemies.add(enemy);
            } else {
                System.out.println("Enemy at (" + enemy.getX1() + ","  + enemy.getY1() + ") has been defeated.");
            }
        }
        enemies = aliveEnemies;
    }

    //EFFECTS: return if the enemies list is empty
    public boolean hasEnemies() {
        return !enemies.isEmpty();
    }
    
    // EFFECTS: returns an unmodifiable list of towers in gamemap
    //public List<Tower> getTower() {
    //    return Collections.unmodifiableList(towers);
    //}
    
    //EFFECTS: return the list of towers
    public List<Tower> getTowers() {
        return towers;
    }

    // MODIFIES: this
    // EFFECTS: remove the current tower
    public void removeTower(Tower tower) {
        towers.remove(tower);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // EFFECTS: returns number of towers in this game map
    public int numTowers() {
        return towers.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject(); 
        json.put("width", width);      
        json.put("height", height);
        json.put("towers", towersToJson());
        return json;
    }

    // EFFECTS: returns towers in this gamemap as a JSON array
    private JSONArray towersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Tower t : towers) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}

