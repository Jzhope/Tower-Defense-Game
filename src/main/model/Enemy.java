package model;

// Represents an enemy having a health, a position(x,y)
public class Enemy {
    private int health;                     //health of an enemy
    private int x1;                          // x-postion of an enemy
    private int y1;                          //position of an enemy
    private static final int MOVE_STEP = 1; //speed of an enemy
  
    //REQUIRE: 0 < x < the width of the map and 0 < y < height
    //EFFECT: construct an enemy object
    public Enemy(int health, int x, int y) {
        this.health = health;
        this.x1 = x;
        this.y1 = y;
    }

    //EFFECT: return the postivity or negativity of the enmey's health  
    public boolean isAlive() {
        return health > 0;
    }
   
    //MODIEFIES: this
    //EFFECT: decrease the health of enemy by damage if it was attacked and print out the effect
    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
        System.out.println("Enemy at (" + x1 + "," + y1 + ") takes " + damage + " damage. Current health: " + health);
    }
    
    //MODIEFIES: this
    //EFFECT: decrease the x-postion by move_step and print ou the effect 
    public void move() { 
        x1 -= MOVE_STEP;   
        System.out.println("Enemy at (" + x1 + "," + y1 + ") moves " + MOVE_STEP + " steps to the left.");
    }
    
    //EFFECT: returns if the x-postion is 0 (enemy reach the base)
    public boolean reachedBase() {
        return x1 == 0;   
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getHealth() {
        return health;
    }
}