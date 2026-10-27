package minereaper;

import minereaper.player.ComputerPlayer;
import minereaper.player.HumanPlayer;
import minereaper.player.Player;
import org.slf4j.Logger;

public class Minereaper {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Minereaper.class);

    private final HumanPlayer humanPlayer;
    private final ComputerPlayer computerOpponent;

    private int turnCount = 0;

    public Minereaper(HumanPlayer humanPlayer, ComputerPlayer computerOpponent) {
        this.humanPlayer = humanPlayer;
        this.computerOpponent = computerOpponent;
    }

    @Override
    public String toString() {
        return humanPlayer.getName() + "'s Board:\n" + humanPlayer.getGrid().toString() +
                computerOpponent.getName() + "'s Board:\n" + computerOpponent.getGrid().toString() +
                humanPlayer.getGrid().toDebugString() +
                computerOpponent.getGrid().toDebugString();
    }

    public void play() {
        logger.info("Minereaper: Starting...");
        logger.info(this.toString());
        while (!isOver()) {
            turnCount++;
            logger.info("Minereaper: turn " + turnCount + "\n");
            playTurn();
            logger.info(this.toString());
        }
        if (getWinner() != null)
            logger.info(getWinner().getName() + " won!");
        else {
            logger.info("No one won!");
        }
    }

    private void playTurn() {
        // this feels a little clumsy ngl - should try to fix
        humanPlayer.makeMove(computerOpponent);
        computerOpponent.makeMove(humanPlayer);
    }

    public Player getWinner() {
        if (computerOpponent.isDefeated() && !humanPlayer.isDefeated()) {
            return humanPlayer;
        } else if (humanPlayer.isDefeated() && !computerOpponent.isDefeated()) {
            return computerOpponent;
        } else {
            return null;
        }
    }

    public boolean isOver() {
        return humanPlayer.isDefeated() || computerOpponent.isDefeated();
    }
}
