package uk.co.kellsnet.worldsim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private static final boolean DEBUG = true;

    private SpriteBatch batch;
    private Texture wallTexture;
    private Texture pillarTexture;
    private Texture playerTexture;
    private Player player;
    private TileRenderer tileRenderer;
    private TileMap tileMap;
    private OrthographicCamera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        tileMap = new TileMap();

        debug("[INIT] create() called");
        debug("[MAP] Map size = " + tileMap.getWidth() + " x " + tileMap.getHeight());
        debug("[MAP] World pixels  = " + tileMap.getWidth() * TileMap.TILE_SIZE + " x " + tileMap.getHeight() * TileMap.TILE_SIZE);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        batch.setProjectionMatrix(camera.combined);

        Pixmap wallPixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        wallPixmap.setColor(1, 1, 1, 1);
        wallPixmap.fill();
        wallTexture = new Texture(wallPixmap);
        wallPixmap.dispose();

        Pixmap pillarPixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        pillarPixmap.setColor(1, 0, 0, 1);
        pillarPixmap.fill();
        pillarTexture = new Texture(pillarPixmap);
        pillarPixmap.dispose();

        Pixmap playerPixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        playerPixmap.setColor(0, 0, 1, 1);
        playerPixmap.fill();
        playerTexture = new Texture(playerPixmap);
        playerPixmap.dispose();

        player = new Player(2, 1);
        debug("[PLAYER] Starting position = (" + player.getX() + ", " + player.getY() + ")");

        updateCamera();

        tileRenderer = new TileRenderer(wallTexture, pillarTexture, playerTexture);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) tryMovePlayer(0, 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) tryMovePlayer(0, -1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) tryMovePlayer(-1, 0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) tryMovePlayer(1, 0);

        batch.begin();
        tileRenderer.render(batch, tileMap, player);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        wallTexture.dispose();
        pillarTexture.dispose();
        playerTexture.dispose();
    }

    private void tryMovePlayer(int dx, int dy) {
        int targetX = player.getX() + dx;
        int targetY = player.getY() + dy;

        debug("[MOVE] Attempting move to (" + targetX + ", " + targetY + ")");

        if (!inBounds(targetX, targetY)) {
            debug("[MOVE] Blocked: target out of bounds");
            return;
        }

        TileType tile = tileMap.getTile(targetX, targetY);
        debug("[MOVE] Target tile is " + tile);

        if (tile.isWalkable()) {
            player.move(dx, dy);
            debug("[MOVE] Success: player now at (" + player.getX() + ", " + player.getY() + ")");
            debug("[MOVE] Stood on tile type: " + tile);
            updateCamera();
        } else {
            debug("[MOVE] Blocked: tile is not walkable");
        }

    }

    private boolean inBounds(int dx, int dy) {
        return dx >= 0 && dx < tileMap.getWidth() && dy >= 0 && dy < tileMap.getHeight();
    }

    private void updateCamera() {
        float playerCenterX = player.getX() * TileMap.TILE_SIZE + TileMap.TILE_SIZE / 2f;
        float playerCenterY = player.getY() * TileMap.TILE_SIZE + TileMap.TILE_SIZE / 2f;

        float worldWidth = tileMap.getWidth() * TileMap.TILE_SIZE;
        float worldHeight = tileMap.getHeight() * TileMap.TILE_SIZE;

        float halfViewportWidth = camera.viewportWidth / 2;
        float halfViewportHeight = camera.viewportHeight / 2;

        playerCenterX = MathUtils.clamp(playerCenterX, halfViewportWidth, worldWidth - halfViewportWidth);
        playerCenterY = MathUtils.clamp(playerCenterY, halfViewportHeight, worldHeight - halfViewportHeight);

        camera.position.set(playerCenterX, playerCenterY, 0);
        camera.update();

        debug("[CAMERA] Clamped center = (" + playerCenterX + ", " + playerCenterY + ")");
    }

    private void debug(String message) {
        if (DEBUG) {
            System.out.println(message);
        }
    }
}
