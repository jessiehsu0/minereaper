package minereaper;

import minereaper.powerup.IPowerUp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

public class Grid {
    private static final Random random = new Random();

    private int width;
    private int height;
    private Tile[][] tiles;

    private Grid() {}

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

    public static GridBuilder newGridBuilder(TileFactory tileFactory) {
        return new GridBuilder(tileFactory);
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

    // TODO: this'll be of more use when the actual UI comes into play
    public void toggleFlagTile(int x, int y) {
        validateCoordinates(x, y);
        tiles[x][y].toggleFlag();
    }

    public boolean revealIfTileIsSafe(int x, int y) {
        validateCoordinates(x, y);
        return tiles[x][y].revealIsSafe();
    }

    private void validateCoordinates(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Coordinates out of bounds: (" + x + ", " + y + ")");
        }
    }

    public Tile getTile(int x, int y) {
        validateCoordinates(x, y);
        return tiles[x][y];
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public static class GridBuilder {
        static Logger logger = LoggerFactory.getLogger(GridBuilder.class);

        private final TileFactory tileFactory;
        final Grid grid = new Grid();

        private GridBuilder(TileFactory tileFactory) { this.tileFactory = tileFactory; }

        public GridBuilder createNxMGridWithMines(int n, int m, int numMines) { return createTiles(n, m).addRandomlyDispersedMines(numMines); }

        public GridBuilder createBeginnerGrid() { return createNxMGridWithMines(9, 9, 10); }
        public GridBuilder createIntermediateGrid() { return createNxMGridWithMines(16, 16, 40); }
        public GridBuilder createExpertGrid() { return createNxMGridWithMines(30, 16, 99); }

        public GridBuilder createTiles(int width, int height) {
            Tile[][] tileGrid = new Tile[width][height];

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    tileGrid[x][y] = TileFactory.createTile(false);
                }
            }

            grid.width = width;
            grid.height = height;
            grid.tiles = tileGrid;

            return this;
        }

        public GridBuilder addRandomlyDispersedMines(int numOfMines) {
            int minesPlaced = 0;

            int totalNumOfTiles = grid.width * grid.height;

            if (numOfMines > totalNumOfTiles) {
                throw new IllegalArgumentException("Cannot place more mines than total tiles!");
            }

            while (minesPlaced < numOfMines) {
                Tile randomTile = findRandomTileInGrid();

                if (!randomTile.isMine()) {
                    randomTile.setIsMine(true);
                    minesPlaced++;
                }
            }

            return this;
        }

        // TODO: need to add a check to make sure we're not trying to place over mines (powerUps.size() > total num of tiles - num mines)
        public GridBuilder addRandomlyDispersedPowerUps(List<IPowerUp> powerUps) {
            int totalNumOfTiles = grid.width * grid.height;

            if (powerUps.size() > totalNumOfTiles) {
                throw new IllegalArgumentException("Cannot place more power ups than total tiles!");
            }

            for (IPowerUp powerUp : powerUps) {
                Tile randomTile = findRandomTileInGrid();
                if (!randomTile.isMine() && !randomTile.hasPowerUp()) {
                    randomTile.setPowerUp(powerUp);
                }
            }

            return this;
        }

        private Tile findRandomTileInGrid() {
            int x = random.nextInt(grid.width);
            int y = random.nextInt(grid.height);
            return grid.getTile(x, y);
        }

        public Grid build() {
            grid.calculateAllAdjacentMines();
            return grid;
        }
    }
}
