package csci.pushoff.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import csci.pushoff.GdxGameMain;
import csci.pushoff.screens.stages.*;

public class CharacterSelectScreen implements Screen {

    protected GdxGameMain game;
    private SpriteBatch batch;
    private Texture font;
    private Texture player_one_title;
    private Texture player_two_title;
    private Texture img;
    private float height;

    private ShapeRenderer shapeRenderer;
    protected Texture[] characterPreviews = new Texture[4]; // 4 characters
    protected Rectangle[] buttonRects = new Rectangle[8]; // 4 buttons per side
    private int playerOneSelection = -1;
    private int playerTwoSelection = -1;

    public CharacterSelectScreen(GdxGameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new Texture("Choose_Your_Character.png");
        player_one_title = new Texture("Player_1.png");
        player_two_title = new Texture("Player_2.png");
        img = new Texture("selectionBackground.jpg");
        shapeRenderer = new ShapeRenderer();
        //font.getData().setScale(2); // Adjust font size

        // Load character preview images
        for (int i = 0; i < characterPreviews.length; i++) {
            characterPreviews[i] = new Texture(Gdx.files.internal("character" + i + "Preview.png"));
        }

        float buttonSize = 100;
        float spacing = 20; // Spacing between buttons
        int buttonsPerSide = buttonRects.length / 2;
        float totalHeight = buttonsPerSide * buttonSize + (buttonsPerSide - 1) * spacing; // Total height of buttons and spacing

        for (int i = 0; i < buttonRects.length; i++) {
            float x = i < 4 ? (Gdx.graphics.getWidth() * 0.25f - buttonSize * 0.5f) : (Gdx.graphics.getWidth() * 0.75f - buttonSize * 0.5f);
            // Calculate starting y position to center buttons vertically
            float startY = (Gdx.graphics.getHeight() - totalHeight) / 6 + totalHeight - buttonSize; // Top position of the top button
            height = (Gdx.graphics.getHeight() - totalHeight) / 2.7f + totalHeight - buttonSize;
            float y = startY - (i % 4) * (buttonSize + spacing); // Adjust y for each button based on index
            buttonRects[i] = new Rectangle(x, y, buttonSize, buttonSize);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 2, 1);

        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Determine which button, if any, is currently being hovered or selected
        // Index of the button being hovered over
        int hoveredIndex = -1;
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        for (int i = 0; i < buttonRects.length; i++) {
            if (buttonRects[i].contains(mouseX, mouseY)) {
                hoveredIndex = i;
                break;
            }
        }

        batch.begin();
        //Draw in player one and two Subtitles
        batch.draw(player_one_title,Gdx.graphics.getWidth() * 0.25f - player_one_title.getWidth() * 0.5f,  height);
        batch.draw(player_two_title,Gdx.graphics.getWidth() * 0.75f - player_two_title.getWidth() * 0.5f, height);
        // Draw the enlarged image of the selected character for each player
        if (playerOneSelection != -1) {
            Texture selectedPreview = characterPreviews[playerOneSelection % characterPreviews.length];
            batch.draw(selectedPreview, 0, 0, (float) Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        }
        if (playerTwoSelection != -1) {
            Texture selectedPreview = characterPreviews[playerTwoSelection % characterPreviews.length];
            batch.draw(selectedPreview, (float) Gdx.graphics.getWidth() / 2, 0, (float) Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        }

        // Draw the hovered character preview, if a button is being hovered and no character is selected yet
        if (hoveredIndex != -1 && (playerOneSelection == -1 || playerTwoSelection == -1)) {
            Texture preview = characterPreviews[hoveredIndex % characterPreviews.length];
            float previewX = hoveredIndex < 4 ? 0 : (float) Gdx.graphics.getWidth() / 2;
            batch.draw(preview, previewX, 0, (float) Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        }
        batch.end();

        batch.begin();
        // Draw "Select Your Character" text and the character preview images onto the buttons
        batch.draw(font,Gdx.graphics.getWidth() / 2f - font.getWidth() / 2f, Gdx.graphics.getHeight() * 0.75f);
        for (int i = 0; i < buttonRects.length; i++) {
            Rectangle rect = buttonRects[i];
            Texture preview = characterPreviews[i % characterPreviews.length];
            batch.draw(preview, rect.x, rect.y, rect.width, rect.height);
        }
        batch.end();

        // Handle button clicks for selecting characters
        if (hoveredIndex != -1 && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (hoveredIndex < 4) { playerOneSelection = hoveredIndex;
            } else { playerTwoSelection = hoveredIndex - 4; }

            // Check if both players have selected
            if (playerOneSelection != -1 && playerTwoSelection != -1) {
                game.setPlayerOneCharacterIndex(playerOneSelection);
                game.setPlayerTwoCharacterIndex(playerTwoSelection);
                //this logic could be cleaned up, not very scalable
                if (game.getStageIndex() == 0){
                    game.setScreen(new StageZero(game));
                }
                else if (game.getStageIndex() == 1){
                    game.setScreen(new StageOne(game));
                }
                else if (game.getStageIndex() == 2){
                    game.setScreen(new StageTwo(game));
                }
                else{
                    game.setScreen(new StageThree(game));
                }
            }
        }
    }


    @Override
    public void resize(int width, int height) {
        // I have window resizing off to make things simple, but it seems this has to be here
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
