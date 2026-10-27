package minereaper;

import minereaper.powerup.HideTilesPowerUp;
import minereaper.powerup.IPowerUp;
import minereaper.powerup.RevealSafePowerUp;

public class Tile {
    private static final int INITIAL_NUM_OF_ADJACENT_MINES = 0;

    private boolean isMine;
    private boolean isRevealed = false;
    private boolean isFlagged = false;
    private int numOfAdjacentMines = INITIAL_NUM_OF_ADJACENT_MINES;

    private IPowerUp powerUp = null;

    // old constructor - potentially will bring it back
    public Tile(boolean isMine) {
        this.isMine = isMine;
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

    public String toDebugString() {
        if (isMine) {
            return "*";
        }

        if (hasPowerUp()) {
            if (powerUp instanceof RevealSafePowerUp) {
                return "R";
            } else if (powerUp instanceof HideTilesPowerUp) {
                return "F";
            }
        }

        return numOfAdjacentMines == 0 ? "_" : String.valueOf(numOfAdjacentMines);
    }

    public boolean hasPowerUp() { return powerUp != null; }

    public void setPowerUp(IPowerUp powerUp) { this.powerUp = powerUp; }

    public IPowerUp getPowerUp() { return powerUp; }

    public void toggleFlag() {
        if (!isRevealed) { isFlagged = !isFlagged; }
    }

    public boolean isFlagged() { return isFlagged; }

    public boolean revealIsSafe() {
        isRevealed = true;
        return !isMine;
    }

    public boolean isRevealed() { return isRevealed; }

    // idk this feels weird because why are we allowing ppl to set isMine after instantiation?
    void setIsMine(boolean isMine) { this.isMine = isMine; }
    public void setIsRevealed(boolean isRevealed) { this.isRevealed = isRevealed; }
    public boolean isMine() { return isMine; }

    public int getAdjacentMines() { return numOfAdjacentMines; }
    void setNumOfAdjacentMines(int adjacentMines) { numOfAdjacentMines = adjacentMines; }
}
