package csci.pushoff.screens.stages;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import csci.pushoff.GdxGameMain;

public class StageTemplate implements Screen {

    private GdxGameMain game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture player1Icon;
    private Texture player2Icon;
    private Texture dot; // Texture for the round indicators

    // Fixed dot size, doubled
    private final float dotSize = 40f; // Size for normal dots
    private final float largeDotSize = dotSize * 2f; // Size for the larger middle dot
    private final float dotSpacing = 60f; // Space between dots

    public StageTemplate(GdxGameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        player1Icon = new Texture("character" + game.getPlayerOneCharacterIndex() + "Preview.jpg");
        player2Icon = new Texture("character" + game.getPlayerTwoCharacterIndex() + "Preview.jpg");
        dot = new Texture("dot.jpg"); // Placeholder for dot texture
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 2, 1);

        batch.begin();

        float hudSize = largeDotSize;
        batch.draw(player1Icon, 10, Gdx.graphics.getHeight() - hudSize - 10, hudSize, hudSize);
        batch.draw(player2Icon, Gdx.graphics.getWidth() - hudSize - 10, Gdx.graphics.getHeight() - hudSize - 10, hudSize, hudSize);

        float centerX = Gdx.graphics.getWidth() / 2;
        float middleDotX = centerX - largeDotSize / 2; // Center the middle dot
        float spaceBetweenDots = dotSpacing + dotSize; // Total space between the centers of the dots

        // Draw the larger middle dot
        batch.draw(dot, middleDotX, Gdx.graphics.getHeight() - largeDotSize - 20, largeDotSize, largeDotSize);

        // Draw the smaller dots to the left and right of the middle dot
        for (int i = 1; i <= 2; i++) {
            // Left side dots
            batch.draw(dot, centerX - (spaceBetweenDots * i) - largeDotSize / 2, Gdx.graphics.getHeight() - dotSize - 20, dotSize, dotSize);
            // Right side dots
            batch.draw(dot, centerX + (spaceBetweenDots * i) - dotSize / 2, Gdx.graphics.getHeight() - dotSize - 20, dotSize, dotSize);
        }

        batch.end();
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
        player1Icon.dispose();
        player2Icon.dispose();
        dot.dispose();
    }
}
