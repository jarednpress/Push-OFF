package csci.pushoff.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import csci.pushoff.GameState;
import csci.pushoff.GdxGameMain;

public class WinScreen implements Screen {

    protected GdxGameMain game;
    protected GameState gameState;
    private SpriteBatch batch;
    private Texture player1Icon;
    private Texture player2Icon;
    private Texture player_one_wins;
    private Texture player_two_wins;
    private Texture img;
    private BitmapFont font;

    public WinScreen(GdxGameMain game, GameState gameState) {

        this.game = game;
        this.gameState = gameState;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("selectionBackground.jpg");
        player1Icon = new Texture("character" + game.getPlayerOneCharacterIndex() + "Preview.png");
        player2Icon = new Texture("character" + game.getPlayerTwoCharacterIndex() + "Preview.png");
        player_one_wins = new Texture("Player_1_wins.png");
        player_two_wins = new Texture("Player_2_wins.png");
        font = new BitmapFont();
        font.getData().setScale(2); // Font size
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 2, 1);

        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        batch.begin();

        if(gameState.getScorePlayerOne()>gameState.getScorePlayerTwo()){
            batch.draw(player1Icon, Gdx.graphics.getWidth() / 2f - player2Icon.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - player1Icon.getHeight() / 2f);
            batch.draw(player_one_wins,Gdx.graphics.getWidth() / 2f - player_one_wins.getWidth() / 2f, Gdx.graphics.getHeight() * 0.75f );
        }
        else{
            batch.draw(player2Icon, Gdx.graphics.getWidth() / 2f - player2Icon.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - player2Icon.getHeight() / 2f);
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
        font.dispose();
        img.dispose();
        player_one_wins.dispose();
        player_two_wins.dispose();
        player1Icon.dispose();
        player2Icon.dispose();
    }
}
