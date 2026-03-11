package uk.co.kellsnet.worldsim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileRenderer {

    private static final int TILE_SIZE = 32;

    private final Texture wallTexture;
    private final Texture pillarTexture;

    public TileRenderer(Texture wallTexture, Texture pillarTexture) {
        this.wallTexture = wallTexture;
        this.pillarTexture = pillarTexture;
    }

    public void render(SpriteBatch batch, int[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 1) {
                    batch.draw(wallTexture, x * TILE_SIZE, y * TILE_SIZE);
                } else if (map[y][x] == 2) {
                    batch.draw(pillarTexture, x * TILE_SIZE, y * TILE_SIZE);
                }
            }
        }
    }
}
