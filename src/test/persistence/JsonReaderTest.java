package persistence;
 
import model.Tower;
import model.GameMap;

 
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameMap gm = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGameMap() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGameMap.json");
        try {
            GameMap gm = reader.read();
            assertEquals(5, gm.getWidth());
            assertEquals(6, gm.getHeight());
            assertEquals(0, gm.numTowers());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGameMap() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGameMap.json");
        try {
            GameMap gm = reader.read();
            assertEquals(5, gm.getWidth());
            assertEquals(6, gm.getHeight());
            List<Tower> towers = gm.getTowers();
            assertEquals(2, towers.size());
            checkTower(2, 4,towers.get(0));
            checkTower(3, 1,towers.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
