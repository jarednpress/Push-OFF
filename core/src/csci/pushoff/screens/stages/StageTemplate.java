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
        characterOne = new Character2(stageOffsetX + 50, 300);
        characterTwo = new Character1(stageOffsetX + stageWidth - 170, 300);
    }

    @Override
    public void render(float delta) {

        updateCharacters(delta);

        batch.begin();
        // Draw characters
        characterOne.draw(batch);
        characterTwo.draw(batch);
        characterOne.moveRight(0); //face right
        characterTwo.moveLeft(0);//face left
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
        // player 1 controls
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            characterOne.moveLeft(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            characterOne.moveRight(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            characterOne.currentState = Character.State.KICKING;
            performKick(characterOne, characterTwo);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            characterOne.currentState = Character.State.BLOCKING_LOW;
        }

        //player 2 controls
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1)) {
            characterTwo.moveLeft(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_3)) {
            characterTwo.moveRight(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2)) {
            characterTwo.currentState = Character.State.KICKING;
            performKick(characterTwo, characterOne);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) {
            characterTwo.currentState = Character.State.BLOCKING_LOW;
        }

        handleCharacterCollision(delta);

        checkCharacterStageBounds(characterOne);
        checkCharacterStageBounds(characterTwo);
    }

    protected void performKick(Character kicker, Character receiver) {
        if (kicker.currentState != Character.State.KICKING) return;

        if (Gdx.input.isKeyPressed(Input.Keys.Q) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) {
            receiver.currentState = Character.State.BLOCKING_LOW;
        }

        // Check if receiver is within kick range and not blocking low
        float distanceBetween = Math.abs(kicker.x - receiver.x);
        if (distanceBetween <= (kicker.getWidth() + receiver.getWidth()) / 2 && receiver.currentState != Character.State.BLOCKING_LOW) {
            // The receiver is kicked instantly at half the kicker's knockback speed adjusted by receiver's friction
            float knockbackDistance = (kicker.shoveKnockback / 2) * receiver.friction;

            // Determine direction of the kick
            if (kicker.getFacingRight()) {
                receiver.x += knockbackDistance;
            } else {
                receiver.x -= knockbackDistance;
            }

            checkCharacterStageBounds(receiver);
        }
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
    protected void handleCharacterCollision(float delta) {
        float distanceBetween = Math.abs(characterOne.x - (characterOne.getWidth()/1.5f) - characterTwo.x);
        float overlap = (characterOne.getWidth() / 2 + characterTwo.getWidth() / 2) - distanceBetween;

        if (overlap > 0) {
            // Determine if either character is idle
            boolean characterOneIdle = characterOne.currentState == Character.State.IDLE;
            boolean characterTwoIdle = characterTwo.currentState == Character.State.IDLE;

            // Push characters based on their states and speed
            if (!characterOneIdle && !characterTwoIdle) resolveHeadOnCollision(overlap, delta);
            else {
                // One character is idle, determine the pusher and push accordingly
                if (characterOneIdle) {
                    pushCharacter(characterOne, characterTwo, delta, overlap);
                } else {
                    pushCharacter(characterTwo, characterOne, delta, overlap);
                }
            }
        }
    }


    protected void pushCharacter(Character pusher, Character idleCharacter, float delta, float overlap) {
        // If the idle character is being pushed, adjust the speed by their friction
        float effectiveSpeed = pusher.speed * idleCharacter.friction;

        // Calculate new position based on effective speed
        float moveAmount = effectiveSpeed * delta;
        if (pusher.x < idleCharacter.x) { // pusher is on the left
            pusher.x -= overlap / 2; // Resolve overlap
            idleCharacter.x += moveAmount; // Push the idle character
        } else { // pusher is on the right
            pusher.x += overlap / 2;
            idleCharacter.x -= moveAmount;
        }
    }


    protected void resolveHeadOnCollision(float overlap, float delta) {
        // Both characters are moving, split the overlap resolution and apply friction to speed
        float characterOneEffectiveSpeed = characterOne.speed * characterTwo.friction;
        float characterTwoEffectiveSpeed = characterTwo.speed * characterOne.friction;

        characterOne.x -= (overlap / 2) + (characterOneEffectiveSpeed * delta);
        characterTwo.x += (overlap / 2) + (characterTwoEffectiveSpeed * delta);
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
