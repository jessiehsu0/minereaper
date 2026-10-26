package minereaper;

import minereaper.player.ComputerPlayer;
import minereaper.player.HumanPlayer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

public class MinereaperTest {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Minereaper.class);

    @Test
    void testGame() {
        HumanPlayer humanPlayer = new HumanPlayer("Bapple", GridTest.createBeginner9x9Grid());
        ComputerPlayer computerOpponent = new ComputerPlayer(GridTest.createBeginner9x9Grid());
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
            HumanPlayer humanPlayer = new HumanPlayer("Bapple", GridTest.createBeginner9x9Grid());
            ComputerPlayer computerOpponent = new ComputerPlayer(GridTest.createBeginner9x9Grid());
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
