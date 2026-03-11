package uk.co.kellsnet.worldsim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture square;
    private static final int TILE_SIZE = 32;

    @Override
    public void create() {
        batch = new SpriteBatch();
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();

        square = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        batch.begin();

        batch.draw(square, 0 * TILE_SIZE, 0 * TILE_SIZE);
        batch.draw(square, 1 * TILE_SIZE, 0 * TILE_SIZE);
        batch.draw(square, 2 * TILE_SIZE, 0 * TILE_SIZE);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        square.dispose();
    }
}
