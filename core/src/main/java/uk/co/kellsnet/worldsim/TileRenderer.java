package uk.co.kellsnet.worldsim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileRenderer {
    private final Texture wallTexture;
    private final Texture pillarTexture;
    private final Texture playerTexture;

    public TileRenderer(Texture wallTexture, Texture pillarTexture, Texture playerTexture) {
        this.wallTexture = wallTexture;
        this.pillarTexture = pillarTexture;
        this.playerTexture = playerTexture;
    }

    public void render(SpriteBatch batch, TileMap tileMap, Player player) {
        for (int y = 0; y < tileMap.getHeight(); y++) {
            for (int x = 0; x < tileMap.getWidth(); x++) {

                TileType tile = tileMap.getTile(x, y);

                switch (tile) {
                    case WALL -> batch.draw(wallTexture, x * tileMap.TILE_SIZE, y * tileMap.TILE_SIZE);
                    case PILLAR -> batch.draw(pillarTexture, x * tileMap.TILE_SIZE, y * tileMap.TILE_SIZE);
                    default -> {}
                }
            }
        }

        batch.draw(playerTexture, player.getX() * tileMap.TILE_SIZE, player.getY() * tileMap.TILE_SIZE);
    }
}
