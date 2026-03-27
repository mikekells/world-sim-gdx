package uk.co.kellsnet.worldsim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private GameState state;
    private Texture floorTexture;
    private Texture wallTexture;
    private Texture pillarTexture;
    private Texture playerTexture;
    private Texture npcTexture;
    private TileRenderer tileRenderer;
    private OrthographicCamera camera;
    private BitmapFont font;
    private Matrix4 uiMatrix;
    private float moveTimer = 0f;
    private final float moveDelay = 0.18f;

    @Override
    public void create() {
        batch = new SpriteBatch();

        TileMap tileMap = new TileMap(30, 30);
        Position position = new Position(5, 5);
        font = new BitmapFont();
        uiMatrix = new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        state = new GameState(tileMap, position);

        debug("[INIT] create() called");
        debug("[MAP] Map size = " + state.getTileMap().getWidth() + " x " + state.getTileMap().getHeight());
        debug("[MAP] World pixels  = " + state.getTileMap().getWidth() * TileMap.TILE_SIZE + " x " + state.getTileMap().getHeight() * TileMap.TILE_SIZE);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        batch.setProjectionMatrix(camera.combined);

        Pixmap floorPixmap = new Pixmap(TileMap.TILE_SIZE, TileMap.TILE_SIZE, Pixmap.Format.RGBA8888);
        floorPixmap.setColor(0.1f, 0.5f, 0.1f, 1);
        floorPixmap.fill();
        floorTexture = new Texture(floorPixmap);
        floorPixmap.dispose();

        Pixmap wallPixmap = new Pixmap(TileMap.TILE_SIZE, TileMap.TILE_SIZE, Pixmap.Format.RGBA8888);
        wallPixmap.setColor(1, 1, 1, 1);
        wallPixmap.fill();
        wallTexture = new Texture(wallPixmap);
        wallPixmap.dispose();

        Pixmap pillarPixmap = new Pixmap(TileMap.TILE_SIZE, TileMap.TILE_SIZE, Pixmap.Format.RGBA8888);
        pillarPixmap.setColor(1, 0, 0, 1);
        pillarPixmap.fill();
        pillarTexture = new Texture(pillarPixmap);
        pillarPixmap.dispose();

        Pixmap playerPixmap = new Pixmap(TileMap.TILE_SIZE, TileMap.TILE_SIZE, Pixmap.Format.RGBA8888);
        playerPixmap.setColor(0, 0, 1, 1);
        playerPixmap.fill();
        playerTexture = new Texture(playerPixmap);
        playerPixmap.dispose();

        Pixmap npcPixmap = new Pixmap(TileMap.TILE_SIZE, TileMap.TILE_SIZE, Pixmap.Format.RGBA8888);
        npcPixmap.setColor(1, 1, 0, 1);
        npcPixmap.fill();
        npcTexture = new Texture(npcPixmap);
        npcPixmap.dispose();

        debug("[PLAYER] Starting position = (" + state.getPlayer().getPosition().getX() + ", " + state.getPlayer().getPosition().getY() + ")");

        updateCamera();

        tileRenderer = new TileRenderer(wallTexture, pillarTexture, playerTexture, npcTexture, floorTexture);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        if (state.isGameOver()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                state.resetGame();
                updateCamera();
            }
        } else {
            handleInput(delta);
        }

        boolean playerReset = state.update(delta);

        if (playerReset) {
            updateCamera();
        }

        ScreenUtils.clear(0f, 0f, 0f, 1f);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        tileRenderer.render(batch, state.getTileMap(), state.getPlayer(), state.getEntities(), camera);
        batch.end();

        renderUi();
    }

    @Override
    public void dispose() {
        batch.dispose();
        floorTexture.dispose();
        wallTexture.dispose();
        pillarTexture.dispose();
        playerTexture.dispose();
        npcTexture.dispose();
        font.dispose();
    }

    private void updateCamera() {
        float playerCenterX = state.getPlayer().getPosition().getX() * TileMap.TILE_SIZE + TileMap.TILE_SIZE / 2f;
        float playerCenterY = state.getPlayer().getPosition().getY() * TileMap.TILE_SIZE + TileMap.TILE_SIZE / 2f;

        float worldWidth = state.getTileMap().getWidth() * TileMap.TILE_SIZE;
        float worldHeight = state.getTileMap().getHeight() * TileMap.TILE_SIZE;

        float halfViewportWidth = camera.viewportWidth / 2;
        float halfViewportHeight = camera.viewportHeight / 2;

        playerCenterX = MathUtils.clamp(playerCenterX, halfViewportWidth, worldWidth - halfViewportWidth);
        playerCenterY = MathUtils.clamp(playerCenterY, halfViewportHeight, worldHeight - halfViewportHeight);

        camera.position.set(playerCenterX, playerCenterY, 0);
        camera.update();

        debug("[CAMERA] Clamped center = (" + playerCenterX + ", " + playerCenterY + ")");
    }

    private void handleInput(float delta) {
        moveTimer -= delta;

        if (moveTimer <= 0f) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                attemptMove(0, 1);
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                attemptMove(0, -1);
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                attemptMove(-1, 0);
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                attemptMove(1, 0);
            }
        }
    }

    private void attemptMove(int dx, int dy) {
        boolean moved = state.tryMovePlayer(dx, dy);
        if (moved) {
            updateCamera();
        }
        moveTimer = moveDelay;
    }

    private void renderUi() {
        batch.setProjectionMatrix(uiMatrix);
        batch.begin();

        font.draw(batch, "Health: " + state.getPlayer().getHealth(), 20, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Caught: " + state.getTimesCaught(), 20, Gdx.graphics.getHeight() - 50);
        font.draw(batch, "Moves: " + state.getSuccessfulMoves(), 20, Gdx.graphics.getHeight() - 80);

        if (state.isGameOver()) {
            font.draw(batch, "GAME OVER!", 300, 300);
            font.draw(batch, "Press 'R' to restart", 280, 270);
        }

        batch.end();
    }

    private void debug(String message) {
        if (Debug.ENABLED) {
            System.out.println(message);
        }
    }

}
