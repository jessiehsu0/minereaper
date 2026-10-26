package minereaper;

import java.util.Random;

public class ComputerPlayer extends Player {
    private static final Random random = new Random();
    private static final String DEFAULT_COMPUTER_PLAYER_NAME = "Computa";

    public ComputerPlayer(Grid grid) {
        super(DEFAULT_COMPUTER_PLAYER_NAME, grid);
    }

    public void makeMove() {
        if (isDefeated()) return;

        boolean moved = false;
        while (!moved) {
            int x = random.nextInt(grid.getWidth());
            int y = random.nextInt(grid.getHeight());

            Tile target = grid.getTile(x, y);

            if (!target.isRevealed()) {
                boolean safe = grid.revealTileIsSafe(x, y);
                if (!safe) {
                    isDefeated = true;
                    System.out.println("KABOOM! " + name + " hit a mine at " + "(" + x + ", " + y + ").");
                } else {
                    System.out.println(name + " revealed a safe tile at (" + x + ", " + y + ").");
                }
                moved = true;
            }
        }
     }
}
