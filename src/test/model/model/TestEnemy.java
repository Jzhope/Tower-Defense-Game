package model.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Enemy;

public class TestEnemy {

    private Enemy enemy;
    
    @BeforeEach
    void runBefore() {
        enemy = new Enemy(10,5,5);
    }

     @Test
    public void testIsAlive() { 
        assertTrue(enemy.isAlive(), "Enemy should be alive with health > 0");
         
        enemy.takeDamage(10);
        assertFalse(enemy.isAlive(), "Enemy should not be alive with health <= 0");
    }

    @Test
    public void testTakeDamage() { 
        assertTrue(enemy.isAlive(), "Enemy should be alive initially");
         
        enemy.takeDamage(5);
        assertEquals(5, enemy.getHealth(), "Enemy health should be 5 after taking 5 damage");
 
        enemy.takeDamage(10);
        assertEquals(0, enemy.getHealth(), "Enemy health should not go below 0 after taking damage");
    }

    @Test
    public void testMove() { 
        enemy.move();
        assertEquals(4, enemy.getX1(), "Enemy x-position should decrease by MOVE_STEP after move");
         
        enemy.move();
        assertEquals(3, enemy.getX1(), "Enemy x-position should decrease further after move");
 
        enemy = new Enemy(10, 1, 5);
        enemy.move();
        assertEquals(0, enemy.getX1(), "Enemy x-position should be 0 after moving when close to the edge");
    }

    @Test
    public void testReachedBase() { 
        assertFalse(enemy.reachedBase(), "Enemy should not have reached the base initially");
 
        enemy = new Enemy(10, 0, 5);
        assertTrue(enemy.reachedBase(), "Enemy should have reached the base when x is 0");
    }
}
