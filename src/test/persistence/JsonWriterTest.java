package persistence;

 
import model.Tower;
import model.GameMap;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest  {
    @Test
    void testWriterInvalidFile() {
        try {
            GameMap gm = new GameMap(5,6);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGameMap() {
        try {
            GameMap gm = new GameMap(5,6);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGameMap.json");
            writer.open();
            writer.write(gm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGameMap.json");
            gm = reader.read();
            assertEquals(5, gm.getWidth());
            assertEquals(6, gm.getHeight());
            assertEquals(0, gm.numTowers());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGameMap() {
        try {
            GameMap gm = new GameMap(5,6);
            gm.addTower(new Tower(2,4),1);
            gm.addTower(new Tower(3,1),1);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGameMap.json");
            writer.open();
            writer.write(gm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGameMap.json");
            gm = reader.read();
            assertEquals(5, gm.getWidth());
            assertEquals(6, gm.getHeight());
            List<Tower> towers = gm.getTowers();
            assertEquals(2, towers.size());
            checkTower(2, 4,towers.get(0));
            checkTower(3, 1,towers.get(1));
             

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
