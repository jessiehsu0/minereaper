package minereaper;

public class TileFactory {
    public static

    Tile createTile(boolean isMine) { return new Tile(isMine); }

    Tile createTile() { return createTile(false); }
}
