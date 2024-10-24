package persistence;

 
import model.Tower;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTower(int num1, int num2, Tower tower) {
        assertEquals(num1, tower.getX2()); 
        assertEquals(num2, tower.getY2()); 
    }
}