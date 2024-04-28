package csci.pushoff.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import csci.pushoff.GdxGameMain;

public class StageSelectScreen implements Screen {

    protected GdxGameMain game;
    private SpriteBatch batch;
    private Texture font;
    private ShapeRenderer shapeRenderer;
    private Texture img;
    protected Texture[] stagePreviews = new Texture[4]; // 4 stages
    protected Rectangle[] stageButtons = new Rectangle[4]; // 4 buttons

    public StageSelectScreen(GdxGameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new Texture("Select_Your_Stage.png");
        img = new Texture("selectionBackground.jpg");
        shapeRenderer = new ShapeRenderer();
        stagePreviews[0] = new Texture(Gdx.files.internal("stage_zero_background.jpg"));
        stagePreviews[1] = new Texture(Gdx.files.internal("stage_1_background.jpg"));
        stagePreviews[2] = new Texture(Gdx.files.internal("stage_2_background.jpg"));
        stagePreviews[3] = new Texture(Gdx.files.internal("stage_3_background.jpg"));

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

        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        // Index of the stage button being hovered over
        int hoveredIndex = -1;

        // Check which button is hovered
        for (int i = 0; i < stageButtons.length; i++) {
            if (stageButtons[i].contains(mouseX, mouseY)) {
                hoveredIndex = i;
            }
        }

        // Render enlarged stage preview first if any button is hovered
        if (hoveredIndex != -1) {
            batch.begin();
            batch.draw(stagePreviews[hoveredIndex], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
        }

        batch.begin();
        for (int i = 0; i < stageButtons.length; i++) {
            batch.draw(stagePreviews[i], stageButtons[i].x, stageButtons[i].y, stageButtons[i].width, stageButtons[i].height);
        }

        // Draw "Select Your Stage" text
        batch.draw(font, Gdx.graphics.getWidth() / 2f - font.getWidth() / 2f, Gdx.graphics.getHeight() * 0.75f);
        batch.end();

        // Change screens on click
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (hoveredIndex != -1) {
                game.setStageSelectionIndex(hoveredIndex);
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
