package minereaper.powerup;

import minereaper.Grid;
import minereaper.Minereaper;
import minereaper.Tile;
import minereaper.player.Player;
import org.slf4j.Logger;

import java.util.Random;

public class HideTilesPowerUp implements IPowerUp{
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Minereaper.class);
    private static final Random random = new Random();
    private static final String OBSCURE_OPPONENT_TILES_NAME = "Fog Bomb";

    @Override
    public String getName() {
        return OBSCURE_OPPONENT_TILES_NAME;
    }

    // TODO: extend functionality once we have player input functionality so player can choose which tiles to hide
    // TODO: also this power up is tweaking a little lowkey i need to debug this
    // lowkey also need to decompose
    @Override
    public void execute(Player user, Player opponent) {
        Grid opponentGrid = opponent.getGrid();

        int totalNumOfTiles = opponentGrid.getWidth() * opponentGrid.getHeight();
        int tilesToHide = Math.max(2, (int) (totalNumOfTiles * 0.03));

        int obscuredCount = 0;
        int attemptCount = 0;

        // TODO: make sure to cover edge case when tilesToHide > numRevealedTiles on opponent's board
        while (obscuredCount < tilesToHide && attemptCount < 50) {
            int x = random.nextInt(opponentGrid.getWidth());
            int y = random.nextInt(opponentGrid.getHeight());

            Tile tile = opponentGrid.getTile(x,y);
            if (tile.isRevealed() && !tile.isMine()) {
                tile.setIsRevealed(false);
                obscuredCount++;
            }
            attemptCount++;
        }

        logger.info(user.getName() + " launched " + getName() + "! Hid " + obscuredCount + " tiles on " + opponent.getName() + "'s board.");
    }
}
