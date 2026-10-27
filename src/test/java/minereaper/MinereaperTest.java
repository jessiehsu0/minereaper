package minereaper;

import minereaper.player.ComputerPlayer;
import minereaper.player.HumanPlayer;
import minereaper.powerup.PowerUpFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

public class MinereaperTest {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Minereaper.class);

    private final TileFactory tileFactory = new TileFactory();
    private final PowerUpFactory powerUpFactory = new PowerUpFactory();

    @Test
    void testGame() {

        Grid humanPlayerGrid = Grid.newGridBuilder(tileFactory)
                .createBeginnerGrid()
                .addRandomlyDispersedPowerUps(powerUpFactory.createRevealSafePowerUps(10))
                .addRandomlyDispersedPowerUps(powerUpFactory.createHideTilesPowerUps(30))
                .build();

        Grid computerOpponentGrid = Grid.newGridBuilder(tileFactory)
                .createBeginnerGrid()
                .addRandomlyDispersedPowerUps(powerUpFactory.createRevealSafePowerUps(10))
                .addRandomlyDispersedPowerUps(powerUpFactory.createHideTilesPowerUps(30))
                .build();

        HumanPlayer humanPlayer = new HumanPlayer("Bapple", humanPlayerGrid);
        ComputerPlayer computerOpponent = new ComputerPlayer(computerOpponentGrid);
        Minereaper minereaperGame = new Minereaper(humanPlayer, computerOpponent);
        minereaperGame.play();
        assert minereaperGame.isOver();
    }

    @Test
    void testFairPlay() {
        int humanWins = 0;
        int computerWins = 0;
        int numTies = 0;
        int TOTAL_GAMES = 100;

        for (int i = 0; i < TOTAL_GAMES; i++) {
            Grid humanPlayerGrid = Grid.newGridBuilder(tileFactory)
                    .createBeginnerGrid()
                    .build();

            Grid computerOpponentGrid = Grid.newGridBuilder(tileFactory)
                    .createBeginnerGrid()
                    .build();

            HumanPlayer humanPlayer = new HumanPlayer("Bapple", humanPlayerGrid);
            ComputerPlayer computerOpponent = new ComputerPlayer(computerOpponentGrid);
            Minereaper game = new Minereaper(humanPlayer, computerOpponent);

            game.play();

            if (game.getWinner() == null) {
                numTies++;
            } else if (game.getWinner().getClass() == HumanPlayer.class) {
                humanWins++;
            } else {
                computerWins++;
            }
        }

        logger.info("Human won " + humanWins + " and computer won " + computerWins);
        logger.info("There were " + numTies + " games with no winners");
    }
}
