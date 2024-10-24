package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents a tower has a x-postion, y-position, range that can attack enemy and
//the damage it can take
public class Tower implements Writable {
    private int x2;        // the position of the TOWER
    private int y2;        // the position of the TOWER
    private int range;    // Tower can attack enemy within this range
    private int damage;   // the damage the Tower can cause
    
    //REQUIRES: x and y should be > 0
    //EFFECT: construct a Tower that have a postion on map and a range that can 
    //attack enemies and the damage it can cause
    public Tower(int x, int y) {
        this.x2 = x;
        this.y2 = y;
        this.range = 5;   
        this.damage = 20;   
    }
    
    //EFFECT: return true if the enemy is within range
    public boolean isInRange(Enemy enemy) {
        if (Math.abs(enemy.getX1() - x2) <= range && Math.abs(enemy.getY1() - y2) <= range) {
            return true;
        } else {
            return false;        
        }
    }
    
    // EFFECT: make the enemy get damage and print out the information
    public void attack(java.util.List<Enemy> enemies) {
        int attackedCount = 0;  
        for (Enemy enemy : enemies) {
            if (attackedCount < 2 && enemy.isAlive() && isInRange(enemy)) {
                System.out.println("Tower at (" + x2 + "," + y2 + ") attacks enemy at (" 
                        + enemy.getX1() + "," + enemy.getY1() + ") for " + damage + " damage.");
                enemy.takeDamage(damage);
                attackedCount++;
            }
        }
    }   

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("The column of Tower", x2);
        json.put("The row of Tower", y2);
        return json;
    }
}
