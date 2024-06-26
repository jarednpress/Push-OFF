package csci.pushoff.screens;
import csci.pushoff.AudioFacade;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import csci.pushoff.GdxGameMain;

public class TitleScreen implements Screen {

    protected GdxGameMain game;
    private SpriteBatch batch;
    private Texture img;
    private BitmapFont font;
    private AudioFacade Theme;

    public TitleScreen(GdxGameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("TitleBackground.png");
        font = new BitmapFont();
        font.getData().setScale(2); // Font size
        Theme = new AudioFacade();
        Theme.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 2, 1); // RGB Value for title screen background

        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
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
        //Theme.stop();
    }
}
