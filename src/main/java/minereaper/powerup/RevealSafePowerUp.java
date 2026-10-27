package minereaper.powerup;

import minereaper.Grid;
import minereaper.Minereaper;
import minereaper.Tile;
import minereaper.player.Player;
import org.slf4j.Logger;

import java.util.Random;

public class RevealSafePowerUp implements IPowerUp {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Minereaper.class);
    private static final Random random = new Random();
    private static final String REVEAL_SAFE_AREA_NAME = "Radar Scan";

    @Override
    public String getName() {
        return REVEAL_SAFE_AREA_NAME;
    }

    // TODO: lowkey need to decompose
    @Override
    public void execute(Player user, Player opponent) {
        Grid grid = user.getGrid();

        int totalNumOfTiles = grid.getWidth() * grid.getHeight();
        int tilesToReveal = Math.max(2, (int) (totalNumOfTiles * 0.03));

        int revealedCount = 0;
        int attemptCount = 0;

        while (revealedCount < tilesToReveal && attemptCount < 50) {
            int x = random.nextInt(grid.getWidth());
            int y = random.nextInt(grid.getHeight());

            Tile tile = grid.getTile(x, y);

            if (!tile.isRevealed() && !tile.isMine()) {
                tile.setIsRevealed(true);
                revealedCount++;
            }
            attemptCount++;
        }

        logger.info(user.getName() + " used " + getName() + "! Uncovered " + revealedCount + " safe tiles.");
    }
}
