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

    public void render(SpriteBatch batch, TileMap tileMap) {
        for (int y = 0; y < tileMap.getHeight(); y++) {
            for (int x = 0; x < tileMap.getWidth(); x++) {

                TileType tile = tileMap.getTile(x, y);

                switch (tile) {
                    case WALL -> batch.draw(wallTexture, x * TILE_SIZE, y * TILE_SIZE);
                    case PILLAR -> batch.draw(pillarTexture, x * TILE_SIZE, y * TILE_SIZE);
                    default -> {}
                }
            }
        }
    }
}
