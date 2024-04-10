package csci.pushoff.screens.stages;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
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
        if (!characterOne.isFrozen){
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                if (Gdx.input.isKeyPressed(Input.Keys.E) || Gdx.input.isKeyPressed(Input.Keys.Q)){
                    delta = delta / 4;
                }
                characterOne.moveLeft(delta);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                if (Gdx.input.isKeyPressed(Input.Keys.E) || Gdx.input.isKeyPressed(Input.Keys.Q)){
                    delta = delta / 4;
                }
                characterOne.moveRight(delta);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
                characterOne.currentState = Character.State.BLOCKING_LOW;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.E)) {
                characterOne.currentState = Character.State.BLOCKING_HIGH;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                characterOne.currentState = Character.State.KICKING;
                performAction(characterOne, characterTwo, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                characterOne.currentState = Character.State.SHOVING;
                performAction(characterOne, characterTwo, false);
            }
        }

        //player 2 controls
        if(!characterTwo.isFrozen){
            if (Gdx.input.isKeyPressed(Input.Keys.J)) {
                if (Gdx.input.isKeyPressed(Input.Keys.U) || Gdx.input.isKeyPressed(Input.Keys.O)){
                    delta = delta / 4;
                }
                characterTwo.moveLeft(delta);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.L)) {
                if (Gdx.input.isKeyPressed(Input.Keys.U) || Gdx.input.isKeyPressed(Input.Keys.O)){
                    delta = delta / 4;
                }
                characterTwo.moveRight(delta);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.U)) {
                characterTwo.currentState = Character.State.BLOCKING_LOW;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.O)) {
                characterTwo.currentState = Character.State.BLOCKING_HIGH;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.K)) {
                characterTwo.currentState = Character.State.KICKING;
                performAction(characterTwo, characterOne, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.I)) {
                characterTwo.currentState = Character.State.SHOVING;
                performAction(characterTwo, characterOne, false);
            }
        }

        handleCharacterCollision(delta);

        checkCharacterStageBounds(characterOne);
        checkCharacterStageBounds(characterTwo);
    }

    protected void freezeCharacter(Character character, float duration) {
        character.isFrozen = true;

        // Schedule a task to unfreeze the character after the given duration
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                character.isFrozen = false;
            }
        }, duration); // Duration is in seconds
    }


    protected void performAction(Character initiator, Character receiver, boolean isKick) {
        // `isKick` true for kick, false for shove
        if (Gdx.input.isKeyPressed(Input.Keys.E) || Gdx.input.isKeyPressed(Input.Keys.O)) {
            receiver.currentState = Character.State.BLOCKING_HIGH;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q) || Gdx.input.isKeyPressed(Input.Keys.U)) {
            receiver.currentState = Character.State.BLOCKING_LOW;
        }

        Character.State blockState = isKick ? Character.State.BLOCKING_LOW : Character.State.BLOCKING_HIGH;
        float actionPenaltyMultiplier = isKick ? 1.0f : 1.6f; // Higher penalty for shove being blocked

        if (receiver.currentState == blockState) {
            freezeCharacter(initiator, initiator.hitstunDuration * actionPenaltyMultiplier);
            return; // Action blocked, early exit
        }

        // Compute knockback and perform action
        float distanceBetween = Math.abs(initiator.x - receiver.x);
        if (distanceBetween <= (initiator.getWidth() + receiver.getWidth()) / 2) {
            float knockbackDistance = (initiator.shoveKnockback * receiver.friction) + (isKick ? 0 : 100);
            receiver.x += initiator.getFacingRight() ? knockbackDistance : -knockbackDistance;
            checkCharacterStageBounds(receiver);
            freezeCharacter(receiver, initiator.hitstunDuration / 4); // Freeze receiver briefly
        }

        // Always freeze the initiator after action
        freezeCharacter(initiator, initiator.hitstunDuration / 2);
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
