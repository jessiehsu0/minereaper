package minereaper.player;

import minereaper.Grid;

public abstract class Player {
    protected final String name;
    protected boolean isDefeated = false;
    protected Grid grid;

    public Player(String name, Grid grid) {
        this.name = name;
        this.grid = grid;
    }

    public abstract void makeMove();

    public boolean isDefeated() { return isDefeated; }
    public Grid getGrid() { return grid; }
    public String getName() { return name; }
}
