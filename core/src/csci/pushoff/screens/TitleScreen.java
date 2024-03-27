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
        img = new Texture("placeholder.jpg"); // logo image placeholder
        font = new BitmapFont();
        font.getData().setScale(2); // Font size
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 2, 1); // RGB Value for title screen background
        batch.begin();

        // Center the logo image
        batch.draw(img, Gdx.graphics.getWidth() / 2f - img.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - img.getHeight() / 2f);

        // Draw the title text above the logo
        font.draw(batch, "Push-OFF!", Gdx.graphics.getWidth() / 2f - 60, Gdx.graphics.getHeight() * 0.75f);

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
