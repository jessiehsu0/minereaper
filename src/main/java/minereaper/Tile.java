package minereaper;

public class Tile {
    private boolean isMine;
    private boolean isRevealed;
    private int numOfAdjacentMines;

    public static int INITIAL_NUM_OF_ADJACENT_MINES = 0;

    // old constructor - potentially will bring it back
    public Tile(boolean isMine) {
        this.isMine = isMine;
        isRevealed = false;
        numOfAdjacentMines = INITIAL_NUM_OF_ADJACENT_MINES;
    }

    @Override
    public String toString() {
        if (!isRevealed) {
            return ".";
        } else if (isMine) {
            return "*";
        } else {
            return numOfAdjacentMines == 0 ? "_" : String.valueOf(numOfAdjacentMines);
        }
    }

    public boolean revealIsSafe() {
        isRevealed = true;
        return !isMine;
    }

    public boolean isRevealed() { return isRevealed;
    }

    // idk this feels weird because why are we allowing ppl to set isMine after instantiation?
    void setIsMine(boolean isMine) { this.isMine = isMine; }
    public boolean isMine() { return isMine; }

    public int getAdjacentMines() { return numOfAdjacentMines; }
    void setNumOfAdjacentMines(int adjacentMines) { numOfAdjacentMines = adjacentMines; }
}
