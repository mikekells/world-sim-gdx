package uk.co.kellsnet.worldsim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
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

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        int worldWidth = tileMap.getWidth() * 32;
        int worldHeight = tileMap.getHeight() * 32;

        camera.position.set(worldWidth / 2f, worldHeight / 2f, 0);
        camera.update();

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

        TileType tile = tileMap.getTile(targetX, targetY);

        if (tile != TileType.WALL) {
            player.move(dx, dy);
        }
    }
}
