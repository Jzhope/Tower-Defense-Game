package model.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Enemy;
import model.Tower;

public class TowerTest {

    private Tower tower;
    private Enemy enemyInRange1;
    private Enemy enemyOutOfRange1;
    private Enemy enemyOutOfRange2;
    private Enemy enemyOutOfRange3;
    

    @BeforeEach
    public void setUp() { 
        tower = new Tower(5, 5); 
        enemyInRange1 = new Enemy(10, 7, 7);
        
        enemyOutOfRange1 = new Enemy(10, 15, 15);
        enemyOutOfRange2 = new Enemy(10, 8, 15);
        enemyOutOfRange3 = new Enemy(10, 15, 8);
    }

    @Test
    public void testIsInRange() { 
        assertTrue(tower.isInRange(enemyInRange1));
        assertFalse(tower.isInRange(enemyOutOfRange1));
        assertFalse(tower.isInRange(enemyOutOfRange3));
        assertFalse(tower.isInRange(enemyOutOfRange2));
    }

    @Test
    public void testAttack() { 
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(enemyInRange1);
        enemies.add(enemyOutOfRange1);
 
        assertEquals(10, enemyInRange1.getHealth());
        assertEquals(10, enemyOutOfRange1.getHealth());
 
        tower.attack(enemies);
 
        assertEquals(0, enemyInRange1.getHealth());
 
        assertEquals(10, enemyOutOfRange1.getHealth());
    }

    @Test
    public void testAttackMultipleEnemies() { 
        Enemy enemyInRange2 = new Enemy(10, 6, 6);
        Enemy enemyInRange3 = new Enemy(10, 6, 6);  
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(enemyInRange1);
        enemies.add(enemyInRange2);
        enemies.add(enemyInRange3);
        enemies.add(enemyOutOfRange1);   
 
        tower.attack(enemies);
 
        assertEquals(0, enemyInRange1.getHealth());
        assertEquals(0, enemyInRange2.getHealth());
        assertEquals(10, enemyInRange3.getHealth());
        assertEquals(10, enemyOutOfRange1.getHealth());

        tower.attack(enemies);
        assertEquals(0, enemyInRange1.getHealth());
        assertEquals(0, enemyInRange2.getHealth());
        assertEquals(0, enemyInRange3.getHealth());
        assertEquals(10, enemyOutOfRange1.getHealth());
    }
}