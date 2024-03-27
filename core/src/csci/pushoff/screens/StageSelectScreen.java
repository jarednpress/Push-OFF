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

public class StageSelectScreen implements Screen {

    private GdxGameMain game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture img; // You might want to use a specific texture for this screen

    public StageSelectScreen(GdxGameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2); // font size
        img = new Texture("clouds.jpg"); // Placeholder stage selection background
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();

        // Display the background
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.draw(batch, "Select Your Stage", Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() * 0.75f);
        batch.end();

        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new CharacterSelectScreen(game)); // Or move to the next logical screen in your game flow
        }
    }

    // Implement other required methods

    @Override
    public void resize(int width, int height) {
        // Adjust any components based on screen size changes
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose(); // Optionally, dispose resources when hidden
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        img.dispose(); // Ensure you dispose of any resources you create
    }
}
