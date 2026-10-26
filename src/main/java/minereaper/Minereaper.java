package minereaper;

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
        return "Minereaper: turn " + turnCount + "\n" +
                humanPlayer.getName() + "'s Board:\n" + humanPlayer.getGrid().toString() +
                computerOpponent.getName() + "'s Board:\n" + computerOpponent.getGrid().toString() +
                humanPlayer.getGrid().toDebugString() +
                computerOpponent.getGrid().toDebugString();
    }

    public void play() {
        logger.info("Minereaper: Starting...");
        while (!isOver()) {
            playTurn();
            turnCount++;
            logger.info(this.toString());
        }
        logger.info(getWinner().getName() + " won!");
    }

    private void playTurn() {
        humanPlayer.makeMove();
        computerOpponent.makeMove();
    }

    private Player getWinner() {
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
