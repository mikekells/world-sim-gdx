package uk.co.kellsnet.worldsim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture wallTexture;
    private Texture pillarTexture;
    private TileRenderer tileRenderer;
    private TileMap tileMap;

    @Override
    public void create() {
        batch = new SpriteBatch();
        tileMap = new TileMap();

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

        tileRenderer = new TileRenderer(wallTexture, pillarTexture);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        batch.begin();
        tileRenderer.render(batch, tileMap);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        wallTexture.dispose();
        pillarTexture.dispose();
    }
}
