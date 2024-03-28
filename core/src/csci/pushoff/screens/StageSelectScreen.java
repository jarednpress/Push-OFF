package csci.pushoff.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import csci.pushoff.GdxGameMain;

public class StageSelectScreen implements Screen {

    private GdxGameMain game;
    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private Texture[] stagePreviews = new Texture[4]; // 4 stages
    private Rectangle[] stageButtons = new Rectangle[4]; // 4 buttons
    private int hoveredIndex = -1; // Index of the stage button being hovered over

    public StageSelectScreen(GdxGameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        font.getData().setScale(2); // font size

        // Initialize stage previews
        for (int i = 0; i < stagePreviews.length; i++) {
            stagePreviews[i] = new Texture(Gdx.files.internal("stage" + i + "Preview.jpg"));
        }

        float buttonWidth = 150;
        float buttonHeight = 100;
        float spacing = 50;
        float totalWidth = stageButtons.length * buttonWidth + (stageButtons.length - 1) * spacing;
        float startX = (Gdx.graphics.getWidth() - totalWidth) / 2; // Center horizontally

        // Center buttons vertically
        float startY = (Gdx.graphics.getHeight() - buttonHeight) / 2;

        for (int i = 0; i < stageButtons.length; i++) {
            float x = startX + i * (buttonWidth + spacing);
            stageButtons[i] = new Rectangle(x, startY, buttonWidth, buttonHeight);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 2, 1);

        // Draw button outlines
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1); // Black
        for (Rectangle rect : stageButtons) {
            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        }
        shapeRenderer.end();

        batch.begin();

        font.draw(batch, "Select Your Stage", Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() * 0.75f);

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // LibGDX origin is top-left
        hoveredIndex = -1;
        for (int i = 0; i < stageButtons.length; i++) {
            if (stageButtons[i].contains(mouseX, mouseY)) {
                hoveredIndex = i;
                // Enlarge the hovered stage preview
                batch.draw(stagePreviews[i], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                break;
            } else {
                batch.draw(stagePreviews[i], stageButtons[i].x, stageButtons[i].y, stageButtons[i].width, stageButtons[i].height);
            }
        }

        batch.end();

        // Change screens on click
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (hoveredIndex != -1) {
                //before continuing, load time allows players to see chosen stage
                try {
                    Thread.sleep(600); //fake load time adjustment
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                game.setScreen(new CharacterSelectScreen(game));
            }
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
        shapeRenderer.dispose();
        for (Texture stagePreview : stagePreviews) {
            stagePreview.dispose();
        }
    }
}
