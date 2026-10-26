package minereaper.player;

import minereaper.Grid;
import minereaper.Tile;

import java.util.Random;
import java.util.Scanner;

public class HumanPlayer extends Player {
    private static final Random random = new Random();
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

    public void makeMove() {
        if (isDefeated()) return;

        boolean moved = false;
        while (!moved) {
            int x = random.nextInt(grid.getWidth());
            int y = random.nextInt(grid.getHeight());

            Tile target = grid.getTile(x, y);

            if (!target.isRevealed()) {
                boolean safe = grid.revealIfTileIsSafe(x, y);
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

    // Ex. of Strategy Pattern - will likely be extended further for harder computer strategies as well
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
