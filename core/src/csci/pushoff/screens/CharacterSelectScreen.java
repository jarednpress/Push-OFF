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

public class CharacterSelectScreen implements Screen {

    private GdxGameMain game;
    private SpriteBatch batch;
    private BitmapFont font;
    // Placeholder for character selection logic

    public CharacterSelectScreen(GdxGameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2); // Adjust font size
        // Initialize your characters here
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 2, 1); // Set a neutral background color
        batch.begin();

        font.draw(batch, "Select Your Character", Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() * 0.75f);

        // Logic to display characters and handle selection goes here

        batch.end();

        // placeholder input handling to proceed to the next screen (this should be replaced with actual selection logic)
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new StageSelectScreen(game));
        }
    }

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
        font.dispose();
        // Dispose other resources if any
    }
}
