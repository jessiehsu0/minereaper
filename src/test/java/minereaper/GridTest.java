package minereaper;

import minereaper.player.HumanPlayer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridTest {
    private static final Random random = new Random();
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Minereaper.class);

    @Test
    void testPlayerDefeatedOnHitMine() {
        Grid oneByOneGrid = createGridMaze(1, 1, 1);
        HumanPlayer humanPlayer = new HumanPlayer("Human", oneByOneGrid);
        humanPlayer.makeMove();
        assertTrue(humanPlayer.isDefeated());
    }

    @Test
    void testToString() {
        Grid oneByOneGrid = createGridMaze(2, 2, 1);
        HumanPlayer humanPlayer = new HumanPlayer("Human", oneByOneGrid);
        humanPlayer.makeMove();
        String gridString = oneByOneGrid.toString();
        logger.info(gridString);

        assertTrue(gridString.contains("*") || gridString.contains("1"));
    }

    @Test
    void testRevealTile() {
        Grid oneByOneGrid = createGridMaze(3, 3, 0);
        int randomX = random.nextInt(3);
        int randomY = random.nextInt(3);
        assertTrue(oneByOneGrid.revealIfTileIsSafe(randomX, randomY));
    }

    static Grid createBeginner9x9Grid() {
        return createGridMaze(9, 9, 10);
    }

    private static Grid createGridMaze(int width, int height, int numOfMines) {
        Tile[][] tiles = new Tile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = new Tile(false);
            }
        }

        return new Grid(width, height, tiles, numOfMines);
    }
}
