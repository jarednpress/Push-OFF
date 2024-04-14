package csci.pushoff.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import csci.pushoff.GameState;
import csci.pushoff.GdxGameMain;

public class WinScreen implements Screen {

    private GdxGameMain game;
    private GameState gameState;
    private SpriteBatch batch;
    private Texture img;
    private Texture player_one_wins;
    private Texture player_two_wins;
    private BitmapFont font;

    public WinScreen(GdxGameMain game, GameState gameState) {

        this.game = game;
        this.gameState = gameState;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("placeholder.jpg"); // logo image placeholder
        player_one_wins = new Texture("Player_1_wins.png");
        player_two_wins = new Texture("Player_2_wins.png");
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
        //ont.draw(batch, "GAME OVER", Gdx.graphics.getWidth() / 2f - 60, Gdx.graphics.getHeight() * 0.75f);
        if(gameState.getScorePlayerOne()>gameState.getScorePlayerTwo()){
            batch.draw(player_one_wins,Gdx.graphics.getWidth() / 2f - player_one_wins.getWidth() / 2f, Gdx.graphics.getHeight() * 0.75f );
        }
        else{
            batch.draw(player_two_wins,Gdx.graphics.getWidth() / 2f - player_two_wins.getWidth() / 2f, Gdx.graphics.getHeight() * 0.75f );
        }

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
