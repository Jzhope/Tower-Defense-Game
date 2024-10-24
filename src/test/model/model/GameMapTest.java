package model.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Enemy;
import model.GameMap;
import model.GameOverException;
import model.Tower;

public class GameMapTest {

    private GameMap gameMap;
    private Tower mockTower;
    private Enemy enemy1;

    @BeforeEach
    public void setUp() {
        gameMap = new GameMap(10, 10);
        mockTower = new Tower(5, 5);

        enemy1 = new Enemy(10, 9, 9);
    }

    @Test
    public void constructor() {
        assertEquals(10, gameMap.getWidth());
        assertEquals(10, gameMap.getHeight());
    }

    @Test
    public void testAddTowerWithinBounds() {
        int result = gameMap.addTower(mockTower, 0);
        assertEquals(1, result);
        assertTrue(gameMap.getTowers().contains(mockTower));
    }

    @Test
    public void testAddTowerOutOfBounds() {
        Tower outOfBoundsTower = new Tower(11, 11);
        Tower outOfBoundsTower1 = new Tower(-1, 9);
        Tower outOfBoundsTower2 = new Tower(5, 11);
        Tower outOfBoundsTower3 = new Tower(11, 9);
        Tower outOfBoundsTower4 = new Tower(1, -1);
        int result = gameMap.addTower(outOfBoundsTower, 0);
        assertEquals(0, result);
        assertFalse(gameMap.getTowers().contains(outOfBoundsTower));

        result = gameMap.addTower(outOfBoundsTower1, 0);
        assertEquals(0, result);
        assertFalse(gameMap.getTowers().contains(outOfBoundsTower1));

        result = gameMap.addTower(outOfBoundsTower2, 0);
        assertEquals(0, result);
        assertFalse(gameMap.getTowers().contains(outOfBoundsTower2));

        result = gameMap.addTower(outOfBoundsTower3, 0);
        assertEquals(0, result);
        assertFalse(gameMap.getTowers().contains(outOfBoundsTower3));

        result = gameMap.addTower(outOfBoundsTower4, 0);
        assertEquals(0, result);
        assertFalse(gameMap.getTowers().contains(outOfBoundsTower4));
    }

    @Test
    public void testAddEnemy() {
        gameMap.addEnemy(enemy1);
        assertTrue(gameMap.hasEnemies());
    }

    @Test
    public void testTowersAttack() {
        Tower attackableTower = new Tower(8, 9);
        Enemy enemy2 = new Enemy(100, 9, 9);
        gameMap.addTower(attackableTower, 1);
        gameMap.addEnemy(enemy1);
        gameMap.addEnemy(enemy2);

        gameMap.towersAttack();
        assertFalse(enemy1.isAlive());
        assertTrue(enemy2.isAlive());

        Tower unattackableTower = new Tower(1, 1);
        gameMap.addTower(unattackableTower, 1);
        gameMap.addEnemy(enemy1);

        gameMap.towersAttack();
        assertFalse(unattackableTower.isInRange(enemy1));

    }

    @Test
    public void testUpdateMap() {
        gameMap.addEnemy(enemy1);

        gameMap.updateMap();
        assertEquals(8, enemy1.getX1());

        Tower attackingTower = new Tower(8, 9);
        gameMap.addTower(attackingTower, 1);
        gameMap.updateMap();
        assertFalse(enemy1.isAlive());
    }

    @Test
    public void testReachBaseMap() {
        Enemy enemy2 = new Enemy(100, 1, 9);
        gameMap.addEnemy(enemy2);

        try {
            gameMap.updateMap(); 
            gameMap.updateMap();
        } catch (GameOverException e) { 
            assertTrue(enemy2.reachedBase());
            assertTrue(e.getMessage().contains("Enemy reached the base! Game over, you lose."));
        }
    }

     
    @Test
    public void testAliveMap() {
        Enemy enemy2 = new Enemy(0, 1, 9);
        gameMap.addEnemy(enemy2);

        gameMap.updateMap();
        assertEquals(1, enemy2.getX1());
    }

    @Test
    public void testRemoveTower() {
        gameMap.addTower(mockTower, 1);
        assertTrue(gameMap.getTowers().contains(mockTower));

        gameMap.removeTower(mockTower);
        assertFalse(gameMap.getTowers().contains(mockTower));
    }

    @Test
    public void testHasEnemies() {
        assertFalse(gameMap.hasEnemies());

        gameMap.addEnemy(enemy1);
        assertTrue(gameMap.hasEnemies());
    }
}
