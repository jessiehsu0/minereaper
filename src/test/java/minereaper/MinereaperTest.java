package minereaper;

import org.junit.jupiter.api.Test;

public class MinereaperTest {
    @Test
    void testGame() {
        HumanPlayer humanPlayer = new HumanPlayer("Bapple", GridTest.createBeginner9x9Grid());
        ComputerPlayer computerOpponent = new ComputerPlayer(GridTest.createBeginner9x9Grid());
        Minereaper minereaperGame = new Minereaper(humanPlayer, computerOpponent);
        minereaperGame.play();
    }
}
