package uk.co.kellsnet.worldsim;

public class TileMap {

    public static final int TILE_SIZE = 32;

    private final TileType[][] tiles = {
        {TileType.WALL, TileType.WALL, TileType.EMPTY, TileType.WALL, TileType.WALL},
        {TileType.WALL,  TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.WALL},
        {TileType.WALL,  TileType.EMPTY, TileType.PILLAR, TileType.EMPTY, TileType.WALL},
        {TileType.WALL,  TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.WALL},
        {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL}
    };

    public TileType getTile(int x, int y) {
        return tiles[y][x];
    }

    public int getWidth() {
        return tiles[0].length;
    }

    public int getHeight() {
        return tiles.length;
    }

    public void setTile(int x, int y, TileType tile) {
        tiles[y][x] = tile;
    }

}
