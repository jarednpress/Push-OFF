package csci.pushoff.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import csci.pushoff.GdxGameMain;
import csci.pushoff.screens.stages.StageTemplate;

import java.util.concurrent.TimeUnit;

public class CharacterSelectScreen implements Screen {

    private GdxGameMain game;
    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private Texture[] characterPreviews = new Texture[4]; // 4 characters
    private Rectangle[] buttonRects = new Rectangle[8]; // 4 buttons per side
    private int playerOneSelection = -1;
    private int playerTwoSelection = -1;
    private int hoveredIndex = -1; // Index of the button being hovered over

    public CharacterSelectScreen(GdxGameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        font.getData().setScale(2); // Adjust font size

        // Load character preview images
        for (int i = 0; i < characterPreviews.length; i++) {
            characterPreviews[i] = new Texture(Gdx.files.internal("character" + i + "Preview.jpg"));
        }

        float buttonSize = 100;
        float spacing = 20; // Spacing between buttons
        int buttonsPerSide = buttonRects.length / 2;
        float totalHeight = buttonsPerSide * buttonSize + (buttonsPerSide - 1) * spacing; // Total height of buttons and spacing

        for (int i = 0; i < buttonRects.length; i++) {
            float x = i < 4 ? (Gdx.graphics.getWidth() * 0.25f - buttonSize * 0.5f) : (Gdx.graphics.getWidth() * 0.75f - buttonSize * 0.5f);
            // Calculate starting y position to center buttons vertically
            float startY = (Gdx.graphics.getHeight() - totalHeight) / 2 + totalHeight - buttonSize; // Top position of the top button
            float y = startY - (i % 4) * (buttonSize + spacing); // Adjust y for each button based on index
            buttonRects[i] = new Rectangle(x, y, buttonSize, buttonSize);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 2, 1);

        // Determine which button, if any, is currently being hovered or selected
        hoveredIndex = -1;
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        for (int i = 0; i < buttonRects.length; i++) {
            if (buttonRects[i].contains(mouseX, mouseY)) {
                hoveredIndex = i;
                break;
            }
        }

        batch.begin();

        // Draw the enlarged image of the selected character for each player
        if (playerOneSelection != -1) {
            Texture selectedPreview = characterPreviews[playerOneSelection % characterPreviews.length];
            batch.draw(selectedPreview, 0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        }
        if (playerTwoSelection != -1) {
            Texture selectedPreview = characterPreviews[playerTwoSelection % characterPreviews.length];
            batch.draw(selectedPreview, Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        }

        // Draw the hovered character preview, if a button is being hovered and no character is selected yet
        if (hoveredIndex != -1 && (playerOneSelection == -1 || playerTwoSelection == -1)) {
            Texture preview = characterPreviews[hoveredIndex % characterPreviews.length];
            float previewX = hoveredIndex < 4 ? 0 : Gdx.graphics.getWidth() / 2;
            batch.draw(preview, previewX, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        }

        batch.end();

        // Now draw the button borders on top of everything
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1); // Black color for rectangles
        shapeRenderer.rectLine(0, 0, 10, 10, 4); // Slightly thicker border
        for (Rectangle rect : buttonRects) {
            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        }
        shapeRenderer.end();

        batch.begin();
        // Draw "Select Your Character" text and the character preview images onto the buttons
        font.draw(batch, "Select Your Character", Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() - 20);
        for (int i = 0; i < buttonRects.length; i++) {
            Rectangle rect = buttonRects[i];
            Texture preview = characterPreviews[i % characterPreviews.length];
            batch.draw(preview, rect.x, rect.y, rect.width, rect.height);
        }
        batch.end();


        // Handle button clicks for selecting characters
        if (hoveredIndex != -1 && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (hoveredIndex < 4) {
                playerOneSelection = hoveredIndex;
            } else {
                playerTwoSelection = hoveredIndex - 4;
            }

            // Check if both players have selected
            if (playerOneSelection != -1 && playerTwoSelection != -1) {
                playerOneSelection = -1; // Reset selection for demonstration
                playerTwoSelection = -1;
                //before continuing, load time allows players to see who the opponent chose
                try {
                    Thread.sleep(600); //fake load time adjustment
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                game.setScreen(new StageTemplate(game)); // Loop back to StageSelectScreen for now
            }
        }
    }


    @Override
    public void resize(int width, int height) {
        // i have window resizing off to make things simple, but it seems this has to be here
    }

    @Override
    public void pause() {
        // Handle game pausing here if necessary
    }

    @Override
    public void resume() {
        // Handle game resuming here if necessary
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();
        for (Texture preview : characterPreviews) {
            preview.dispose();
        }
    }
}
