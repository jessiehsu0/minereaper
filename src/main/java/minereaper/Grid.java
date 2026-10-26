package minereaper;

import java.util.Random;

public class Grid {
    private static final Random random = new Random();

    private final int width;
    private final int height;
    private final Tile[][] tiles;

    // lowkey not sure if we should be delegating the disperse mine logic to the grid
    public Grid(int width, int height, Tile[][] tiles, int numOfMines) {
        this.width = width;
        this.height = height;
        this.tiles = tiles;
        randomlyDisperseMines(numOfMines);
        calculateAllAdjacentMines();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("    ");
        for (int x = 0; x < width; x++) {
            sb.append(x).append(" ");
        }
        sb.append("\n");

        sb.append("   ").append("-".repeat(width * 2 + 1)).append("\n");

        for (int y = 0; y < height; y++) {
            sb.append(y).append(" | ");
            for (int x = 0; x < width; x++) {
                sb.append(tiles[x][y].toString()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String toDebugString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- DEBUG BOARD VIEW ---\n");
        sb.append("    ");
        for (int x = 0; x < width; x++) {
            sb.append(x).append(" ");
        }
        sb.append("\n");
        sb.append("   ").append("-".repeat(width * 2 + 1)).append("\n");

        for (int y = 0; y < height; y++) {
            sb.append(y).append(" | ");
            for (int x = 0; x < width; x++) {
                sb.append(tiles[x][y].toDebugString()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void randomlyDisperseMines(int numOfMines) {
        int minesPlaced = 0;

        int totalNumOfTiles = width * height;
        if (numOfMines > totalNumOfTiles) {
            throw new IllegalArgumentException("Cannot place more mines than total tiles!");
        }

        while (minesPlaced < numOfMines) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            if (!tiles[x][y].isMine()) {
                tiles[x][y].setIsMine(true);
                minesPlaced++;
            }
        }
    }

    private void calculateAllAdjacentMines() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                calculateAdjacentMinesForTile(x, y);
            }
        }
    }

    private void calculateAdjacentMinesForTile(int x, int y) {
        Tile current = tiles[x][y];
        if (current.isMine()) return;

        int mineCount = 0;
        for (int offsetX = -1; offsetX <= 1; offsetX++) {
            for (int offsetY = -1; offsetY <= 1; offsetY++) {
                if (offsetX == 0 && offsetY == 0) continue;

                int neighborX = x + offsetX;
                int neighborY = y + offsetY;

                if (neighborX >= 0 && neighborX < width && neighborY >= 0 && neighborY < height) {
                    if (tiles[neighborX][neighborY].isMine()) {
                        mineCount++;
                    }
                }
            }
        }
        current.setNumOfAdjacentMines(mineCount);
    }

    public boolean revealTileIsSafe(int x, int y) {
        validateCoordinates(x, y);
        return tiles[x][y].revealIsSafe();
    }

    public Tile getTile(int x, int y) {
        validateCoordinates(x, y);
        return tiles[x][y];
    }

    private void validateCoordinates(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Coordinates out of bounds: (" + x + ", " + y + ")");
        }
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
