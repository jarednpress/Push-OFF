package csci.pushoff.screens.stages;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import csci.pushoff.GdxGameMain;
import com.badlogic.gdx.Input;
import csci.pushoff.characters.Character;
import csci.pushoff.characters.Character1;
import csci.pushoff.characters.Character2;

public class StageTemplate implements Screen {

    protected GdxGameMain game;
    protected SpriteBatch batch;
    private BitmapFont font;
    protected Texture player1Icon;
    protected Texture player2Icon;
    protected Texture dot;
    protected Character characterOne;
    protected Character characterTwo;
    protected int stageWidth;
    protected float stageOffsetX;

    // Fixed dot size, doubled
    protected final float dotSize = 40f; // Size for normal dots
    protected final float largeDotSize = dotSize * 2f; // Size for the larger middle dot
    protected final float dotSpacing = 60f; // Space between dots

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

        stageOffsetX = (Gdx.graphics.getWidth() - stageWidth) / 2f;
        characterOne = new Character1(stageOffsetX + 50, 300);
        characterTwo = new Character2(stageOffsetX + stageWidth - 170, 300);
    }

    @Override
    public void render(float delta) {

        updateCharacters(delta);

        batch.begin();
        // Draw characters
        characterOne.draw(batch);
        characterTwo.draw(batch);
        batch.end();

        batch.begin();

        float hudSize = largeDotSize;
        batch.draw(player1Icon, 10, Gdx.graphics.getHeight() - hudSize - 10, hudSize, hudSize);
        batch.draw(player2Icon, Gdx.graphics.getWidth() - hudSize - 10, Gdx.graphics.getHeight() - hudSize - 10, hudSize, hudSize);

        float twoNum = 2; //this seems ridiculous but it wouldn't run without
        float centerX = Gdx.graphics.getWidth() / twoNum;
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

    protected void updateCharacters(float delta) {
        // Update character positions, states, etc.
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            characterOne.moveLeft(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            characterOne.moveRight(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            characterTwo.moveLeft(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            characterTwo.moveRight(delta);
        }
        checkCharacterStageBounds(characterOne);
        checkCharacterStageBounds(characterTwo);
    }

    protected void checkCharacterStageBounds(Character character) {
        float characterMidpoint = character.x + character.getWidth() / 2f;
        boolean fellOffLeft = characterMidpoint < stageOffsetX;
        boolean fellOffRight = characterMidpoint > (stageOffsetX + stageWidth);

        if (fellOffLeft || fellOffRight) {
            characterFall(character, fellOffLeft);
        }
    }


    protected void characterFall(Character character, boolean fellOffLeft) {
        // Set the Y position to 0 since they've fallen
        character.y = 0;

        if (fellOffLeft) {
            // Place the character to the left of the stage
            character.x = stageOffsetX - character.getWidth() - 10; // 10 pixels away from the edge
        } else {
            // Place the character to the right of the stage
            character.x = stageOffsetX + stageWidth + 10; // 10 pixels away from the edge
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
        player1Icon.dispose();
        player2Icon.dispose();
        dot.dispose();
    }
}
