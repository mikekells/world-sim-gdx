package uk.co.kellsnet.worldsim;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.List;

public class TileRenderer {
    private final Texture floorTexture;
    private final Texture wallTexture;
    private final Texture pillarTexture;
    private final Texture playerTexture;
    private final Texture npcTexture;
    private final Texture goalTexture;

    public TileRenderer(Texture wallTexture, Texture pillarTexture, Texture playerTexture, Texture npcTexture, Texture floorTexture, Texture goalTexture) {
        this.floorTexture = floorTexture;
        this.wallTexture = wallTexture;
        this.pillarTexture = pillarTexture;
        this.playerTexture = playerTexture;
        this.npcTexture = npcTexture;
        this.goalTexture = goalTexture;
    }

    public void render(SpriteBatch batch, TileMap tileMap, Player player, List<Entity> entities, OrthographicCamera camera) {
        float leftWorld = camera.position.x - camera.viewportWidth / 2f;
        float rightWorld = camera.position.x + camera.viewportWidth / 2f;
        float bottomWorld = camera.position.y - camera.viewportHeight / 2f;
        float topWorld = camera.position.y + camera.viewportHeight / 2f;

        int startX = MathUtils.floor(leftWorld / TileMap.TILE_SIZE);
        int endX = MathUtils.floor(rightWorld / TileMap.TILE_SIZE);
        int startY = MathUtils.floor(bottomWorld / TileMap.TILE_SIZE);
        int endY = MathUtils.floor(topWorld / TileMap.TILE_SIZE);

        startX = Math.max(0, startX);
        startY = Math.max(0, startY);
        endX = Math.min(tileMap.getWidth() - 1, endX);
        endY = Math.min(tileMap.getHeight() - 1, endY);

        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                int drawX = x * TileMap.TILE_SIZE;
                int drawY = y * TileMap.TILE_SIZE;

                batch.draw(floorTexture, drawX, drawY);
            }
        }

        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {

                int drawX = x * TileMap.TILE_SIZE;
                int drawY = y * TileMap.TILE_SIZE;

                TileType tile = tileMap.getTile(x, y);

                switch (tile) {
                    case WALL -> batch.draw(wallTexture, drawX, drawY);
                    case PILLAR -> batch.draw(pillarTexture, drawX, drawY);
                    case GOAL -> batch.draw(goalTexture, drawX, drawY);
                    default -> {}
                }
            }
        }

        batch.draw(playerTexture, player.getPosition().getX() * TileMap.TILE_SIZE, player.getPosition().getY() * TileMap.TILE_SIZE);

        for (Entity entity : entities) {
            batch.draw(npcTexture, entity.getPosition().getX() * TileMap.TILE_SIZE, entity.getPosition().getY() * TileMap.TILE_SIZE);
        }
    }
}
