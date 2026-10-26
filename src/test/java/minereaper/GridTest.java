package minereaper;

public class GridTest {
    static Grid createBeginner9x9Grid() {
        return createGridMaze(9, 9, 10);
    }

    private static Grid createGridMaze(int width, int height, int numOfMines) {
        Tile[][] tiles = new Tile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = new Tile(false);
            }
        }

        return new Grid(width, height, tiles, numOfMines);
    }


}
