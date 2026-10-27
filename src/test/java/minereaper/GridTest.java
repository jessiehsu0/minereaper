package minereaper;

import minereaper.player.ComputerPlayer;
import minereaper.player.HumanPlayer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridTest {
    private static final Random random = new Random();
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Minereaper.class);

    private final TileFactory tileFactory = new TileFactory();

    @Test
    void testPlayerDefeatedOnHitMine() {
        Grid oneByOneGrid = Grid.newGridBuilder(tileFactory)
                .createTiles(1, 1)
                .addRandomlyDispersedMines(1)
                .build();
        ComputerPlayer computerPlayer = new ComputerPlayer(oneByOneGrid);
        HumanPlayer humanPlayer = new HumanPlayer("Human", oneByOneGrid);
        humanPlayer.makeMove(computerPlayer);
        assertTrue(humanPlayer.isDefeated());
    }

    @Test
    void testToString() {
        Grid oneByOneGrid = Grid.newGridBuilder(tileFactory)
                .createTiles(2, 2)
                .addRandomlyDispersedMines(1)
                .build();
        ComputerPlayer computerPlayer = new ComputerPlayer(oneByOneGrid);
        HumanPlayer humanPlayer = new HumanPlayer("Human", oneByOneGrid);
        humanPlayer.makeMove(computerPlayer);
        String gridString = oneByOneGrid.toString();
        logger.info(gridString);

        assertTrue(gridString.contains("*") || gridString.contains("1"));
    }

    @Test
    void testRevealTile() {
        Grid oneByOneGrid = Grid.newGridBuilder(tileFactory)
                .createTiles(3, 3)
                .addRandomlyDispersedMines(0)
                .build();
        int randomX = random.nextInt(3);
        int randomY = random.nextInt(3);
        assertTrue(oneByOneGrid.revealIfTileIsSafe(randomX, randomY));
    }
}
