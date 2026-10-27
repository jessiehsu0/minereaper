package minereaper.player;

import minereaper.Grid;
import minereaper.Minereaper;
import minereaper.Tile;
import minereaper.powerup.IPowerUp;
import org.slf4j.Logger;

import java.util.Random;

public class ComputerPlayer extends Player {
    private static final Random random = new Random();
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Minereaper.class);
    private static final String DEFAULT_COMPUTER_PLAYER_NAME = "Computa";

    public ComputerPlayer(Grid grid) {
        super(DEFAULT_COMPUTER_PLAYER_NAME, grid);
    }

    public void makeMove(Player opponent) {
        if (isDefeated()) return;

        boolean moved = false;
        while (!moved) {
            int x = random.nextInt(grid.getWidth());
            int y = random.nextInt(grid.getHeight());

            Tile target = grid.getTile(x, y);

            if (!target.isRevealed()) {
                handleRevealTile(x, y, opponent);
                moved = true;
            }
        }
     }

     // TODO: this should change with varying levels of computer difficulty
    public void handleRevealTile(int x, int y, Player opponent) {
        Tile target = grid.getTile(x, y);
        boolean safe = target.revealIsSafe();

        if (!safe) {
            isDefeated = true;
            logger.info("KABOOM! " + name + " hit a mine at " + "(" + x + ", " + y + ").");
        } else {
            logger.info(name + " revealed a safe tile at (" + x + ", " + y + ").");
            if (target.hasPowerUp()) {
                IPowerUp powerUp = target.getPowerUp();
                powerUp.execute(this, opponent);
                target.setPowerUp(null);
            }
        }
    }
}
