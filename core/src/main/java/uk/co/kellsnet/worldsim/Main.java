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
import org.w3c.dom.Text;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private GameState state;
    private Texture floorTexture;
    private Texture wallTexture;
    private Texture pillarTexture;
    private Texture playerTexture;
    private Texture npcTexture;
    private Texture goalTexture;
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

        floorTexture = new Texture(Gdx.files.internal("floor.png"));
        floorTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        wallTexture = new Texture(Gdx.files.internal("wall.png"));
        wallTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        pillarTexture = new Texture(Gdx.files.internal("pillar.png"));
        pillarTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        playerTexture = new Texture(Gdx.files.internal("player.png"));
        playerTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        npcTexture = new Texture(Gdx.files.internal("npc.png"));
        npcTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        goalTexture = new Texture(Gdx.files.internal("goal.png"));
        goalTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        debug("[PLAYER] Starting position = (" + state.getPlayer().getPosition().getX() + ", " + state.getPlayer().getPosition().getY() + ")");

        updateCamera(0f);

        tileRenderer = new TileRenderer(wallTexture, pillarTexture, playerTexture, npcTexture, floorTexture, goalTexture);

    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        if (state.isGameOver() || state.isGameWon()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                state.resetGame();
            }
        } else {
            handleInput(delta);
        }

        state.update(delta);
        updateCamera(delta);

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
        goalTexture.dispose();
        font.dispose();
    }

    private void updateCamera(float delta) {
        float playerCenterX = state.getPlayer().getRenderX() * TileMap.TILE_SIZE + TileMap.TILE_SIZE / 2f;
        float playerCenterY = state.getPlayer().getRenderY() * TileMap.TILE_SIZE + TileMap.TILE_SIZE / 2f;

        float worldWidth = state.getTileMap().getWidth() * TileMap.TILE_SIZE;
        float worldHeight = state.getTileMap().getHeight() * TileMap.TILE_SIZE;

        float halfViewportWidth = camera.viewportWidth / 2f;
        float halfViewportHeight = camera.viewportHeight / 2f;

        playerCenterX = MathUtils.clamp(playerCenterX, halfViewportWidth, worldWidth - halfViewportWidth);
        playerCenterY = MathUtils.clamp(playerCenterY, halfViewportHeight, worldHeight - halfViewportHeight);

        float cameraLerpSpeed = 10f;
        float alpha = Math.min(1f, cameraLerpSpeed * delta);

        float smoothedX = MathUtils.lerp(camera.position.x, playerCenterX, alpha);
        float smoothedY = MathUtils.lerp(camera.position.y, playerCenterY, alpha);

        camera.position.set(Math.round(smoothedX), Math.round(smoothedY), 0);
        camera.update();
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
            moveTimer = moveDelay;
        }
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
            font.draw(batch, "Total Moves: " + state.getTotalMoves(), 290, 250);
        } else if (state.isGameWon()) {
            font.draw(batch, "YOU WIN!", 300, 300);
            font.draw(batch, "Press 'R' to restart", 280, 270);
            font.draw(batch, "Total Moves: " + state.getTotalMoves(), 290, 250);
        }

        batch.end();
    }

    public void resize(int width, int height) {
        uiMatrix.setToOrtho2D(0, 0, width, height);
    }

    private void debug(String message) {
        if (Debug.ENABLED) {
            System.out.println(message);
        }
    }

}
