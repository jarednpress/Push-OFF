package csci.pushoff.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import csci.pushoff.GdxGameMain;

public class TitleScreen implements Screen {

    private GdxGameMain game;
    private SpriteBatch batch;
    private Texture img;
    private BitmapFont font;

    public TitleScreen(GdxGameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("logo.png");
        font = new BitmapFont();
        font.getData().setScale(2); // Font size
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 2, 1); // RGB Value for title screen background
        batch.begin();

        // Center the logo image
        float x = Gdx.graphics.getWidth() / 2f - 250;
        float y = Gdx.graphics.getHeight() / 2f - 250;
        batch.draw(img, x, y, 500, 500);

        // Draw the title text above the logo
        font.draw(batch, "Push-OFF!", Gdx.graphics.getWidth() / 2f - 60, Gdx.graphics.getHeight() * 0.80f);

        batch.end();

        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new StageSelectScreen(game));
        }
    }

    // Implement other required methods (resize, pause, resume, hide, dispose) as needed

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        font.dispose();
    }
}
