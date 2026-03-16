package uk.co.kellsnet.worldsim;

public class TileMap {

    public static final int TILE_SIZE = 32;

    private final TileType[][] tiles;
    private final int width;
    private final int height;

    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new TileType[height][width];
        generateMap();
    }

    private void generateMap() {
        fillWithFloor();
        buildWalls();
        placePillar();
    }

    private void placePillar() {
        int centerX = width / 2;
        int centerY = height / 2;

        tiles[centerY][centerX] = TileType.PILLAR;
        tiles[8][8] = TileType.PILLAR;
    }

    private void buildWalls() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean isEdge = x == 0 || x == width - 1 || y == 0 || y == height - 1;

                if (isEdge) {
                    tiles[y][x] = TileType.WALL;
                }
            }
        }
    }

    private void fillWithFloor() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = TileType.FLOOR;
            }
        }
    }

    public TileType getTile(int x, int y) {
        return tiles[y][x];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setTile(int x, int y, TileType tile) {
        tiles[y][x] = tile;
    }

    public boolean inBounds(int dx, int dy) {
        return dx >= 0 && dx < width && dy >= 0 && dy < height;
    }

}
