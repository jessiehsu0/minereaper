package minereaper.player;

import minereaper.Grid;
import minereaper.Minereaper;
import minereaper.Tile;
import minereaper.powerup.IPowerUp;
import org.slf4j.Logger;

import java.util.Random;
import java.util.Scanner;

public class HumanPlayer extends Player {
    private static final Random random = new Random();
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Minereaper.class);
    private final Scanner scanner = new Scanner(System.in);

    public HumanPlayer(String name, Grid grid) {
        super(name, grid);
    }

//    @Override
    // this is what we'd actually use for user input
//    public void makeMove() {
//        if (isDefeated()) return;
//        userInputMove();
//    }

    // placeholder before we can get user input
    // also is making it mandatory for there to be an opponent to play a good design? idk bruh
    public void makeMove(Player opponent) {
        if (isDefeated()) return;

        boolean moved = false;
        while (!moved) {
            int x = random.nextInt(grid.getWidth());
            int y = random.nextInt(grid.getHeight());

            // need to fix this section cause wtf
            Tile target = grid.getTile(x, y);

            if (!target.isRevealed()) {
                handleRevealTile(x, y, opponent);
                moved = true;
            }
        }
    }

    // placeholder before we can get user input - THIS WILL CHANGE EVENTUALLY SO IT'S NOT THE SAME AS COMPUTER PLAYER'S
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

    // TODO: this is a placeholder for the UI implementation
    private void userInputMove() {
        boolean validMove = false;
        while (!validMove) {
            System.out.println(name + ", enter the X and Y coordinates of the tile you would like to reveal (e.g., 2 3): ");
            try {
                int x = scanner.nextInt();
                int y = scanner.nextInt();

                Tile target = grid.getTile(x, y);
                if (target.isRevealed()) {
                    System.out.println("This tile has already been revealed! Choose another.");
                    continue;
                }

                boolean safe = grid.revealIfTileIsSafe(x, y);
                if (!safe) {
                    isDefeated = true;
                    System.out.println("BOOM! " + name + " hit a mine at " + "(" + x + ", " + y + ").");
                } else {
                    validMove = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter your desired coordinate in the following format: (x y).");
                scanner.nextLine();
            }
        }
    }
}
