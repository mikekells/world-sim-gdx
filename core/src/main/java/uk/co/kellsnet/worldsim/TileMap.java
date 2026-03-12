package uk.co.kellsnet.worldsim;

public class TileMap {

    private final int[][] tiles = {
        {1, 1, 1, 1, 1},
        {1, 0, 0, 0, 1},
        {1, 0, 2, 0, 1},
        {1, 0, 0, 0, 1},
        {1, 1, 1, 1, 1}
    };

    public int[][] getTiles() {
        return tiles;
    }

}
