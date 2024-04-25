package csci.pushoff.screens.stages;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import csci.pushoff.GameState;
import csci.pushoff.GdxGameMain;
import com.badlogic.gdx.Input;
import csci.pushoff.characters.Character;
import csci.pushoff.characters.CharacterFactory;
import csci.pushoff.characters.FatMan;
import csci.pushoff.characters.Baby;
import csci.pushoff.screens.WinScreen;

public class StageTemplate implements Screen {

    protected GdxGameMain game;
    protected SpriteBatch batch;
    private BitmapFont font;
    protected Texture player1Icon;
    protected Texture player2Icon;
    protected Texture dot;
    protected Texture dotWinTexture;
    protected Character playerOne;
    protected Character playerTwo;
    protected int stageWidth;
    protected float stageOffsetX;
    protected GameState gameState;


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
        gameState = new GameState();
        player1Icon = new Texture("character" + game.getPlayerOneCharacterIndex() + "Preview.jpg");
        player2Icon = new Texture("character" + game.getPlayerTwoCharacterIndex() + "Preview.jpg");
        dot = new Texture("dot.jpg");
        dotWinTexture = new Texture("dotWin.jpg");

        stageOffsetX = (Gdx.graphics.getWidth() - stageWidth) / 2f;
        playerOne = CharacterFactory.makeCharacter(game.getPlayerOneCharacterIndex(),stageOffsetX + 50, 300);
        playerTwo = CharacterFactory.makeCharacter(game.getPlayerTwoCharacterIndex(), stageOffsetX + stageWidth - 170, 300);
    }

    @Override
    public void render(float delta) {

        updateCharacters(delta);

        batch.begin();
        // Draw characters
        playerOne.draw(batch);
        playerTwo.draw(batch);
        playerOne.moveRight(0); //face right
        playerTwo.moveLeft(0);//face left
        batch.end();

        batch.begin();

        float hudSize = largeDotSize;
        batch.draw(player1Icon, 10, Gdx.graphics.getHeight() - hudSize - 10, hudSize, hudSize);
        batch.draw(player2Icon, Gdx.graphics.getWidth() - hudSize - 10, Gdx.graphics.getHeight() - hudSize - 10, hudSize, hudSize);

        // Drawing dots
        float centerX = Gdx.graphics.getWidth() / 2f;
        float middleDotX = centerX - largeDotSize / 2; // Center the middle dot
        float spaceBetweenDots = dotSpacing + dotSize; // Total space between the centers of the dots

        // Update drawing based on the current scores
        int scorePlayerOne = gameState.getScorePlayerOne();
        int scorePlayerTwo = gameState.getScorePlayerTwo();

        // Draw player one's dots
        for (int i = 0; i < 2; i++) {
            Texture currentTexture = i < scorePlayerOne ? dotWinTexture : dot;
            batch.draw(currentTexture, centerX - (spaceBetweenDots * (i + 1)) - largeDotSize / 2, Gdx.graphics.getHeight() - dotSize - 20, dotSize, dotSize);
        }

        // Draw player two's dots
        for (int i = 0; i < 2; i++) {
            Texture currentTexture = i < scorePlayerTwo ? dotWinTexture : dot;
            batch.draw(currentTexture, centerX + (spaceBetweenDots * (i + 1)) - dotSize / 2, Gdx.graphics.getHeight() - dotSize - 20, dotSize, dotSize);
        }

        // Draw the middle dot. Change it if one player wins.
        Texture middleDotTexture = dot;
        if (scorePlayerOne >= 3 || scorePlayerTwo >= 3) {
            middleDotTexture = dotWinTexture;
        }
        batch.draw(middleDotTexture, middleDotX, Gdx.graphics.getHeight() - largeDotSize - 20, largeDotSize, largeDotSize);



        batch.end();
    }

    protected void updateCharacters(float delta) {
        // player 1 controls
        if (!playerOne.isFrozen){
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                if (Gdx.input.isKeyPressed(Input.Keys.E) || Gdx.input.isKeyPressed(Input.Keys.Q)){
                    delta = delta / 4;
                }
                playerOne.moveLeft(delta);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                if (Gdx.input.isKeyPressed(Input.Keys.E) || Gdx.input.isKeyPressed(Input.Keys.Q)){
                    delta = delta / 4;
                }
                playerOne.moveRight(delta);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
                playerOne.currentState = Character.State.BLOCKING_LOW;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.E)) {
                playerOne.currentState = Character.State.BLOCKING_HIGH;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                playerOne.currentState = Character.State.KICKING;
                performAction(playerOne, playerTwo, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                playerOne.currentState = Character.State.SHOVING;
                performAction(playerOne, playerTwo, false);
            }
        }

        //player 2 controls
        if(!playerTwo.isFrozen){
            if (Gdx.input.isKeyPressed(Input.Keys.J)) {
                if (Gdx.input.isKeyPressed(Input.Keys.U) || Gdx.input.isKeyPressed(Input.Keys.O)){
                    delta = delta / 4;
                }
                playerTwo.moveLeft(delta);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.L)) {
                if (Gdx.input.isKeyPressed(Input.Keys.U) || Gdx.input.isKeyPressed(Input.Keys.O)){
                    delta = delta / 4;
                }
                playerTwo.moveRight(delta);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.U)) {
                playerTwo.currentState = Character.State.BLOCKING_LOW;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.O)) {
                playerTwo.currentState = Character.State.BLOCKING_HIGH;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.K)) {
                playerTwo.currentState = Character.State.KICKING;
                performAction(playerTwo, playerOne, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.I)) {
                playerTwo.currentState = Character.State.SHOVING;
                performAction(playerTwo, playerOne, false);
            }
        }

        handleCharacterCollision(delta);

        checkCharacterStageBounds(playerOne);
        checkCharacterStageBounds(playerTwo);
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

        float overlap = (playerOne.getWidth() / 2 + playerTwo.getWidth() / 2) - Math.abs((playerOne.x + playerOne.getWidth() / 2) - (playerTwo.x + playerTwo.getWidth() / 2));
        if (overlap > -1) {
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

        if ((fellOffLeft || fellOffRight) && !gameState.isWaitingForReset()) {
            gameState.setWaitingForReset(true); // Prevent further score updates until reset
            characterFall(character, fellOffLeft);
            if (character == playerOne) {
                gameState.incrementScore(false); // Score for Player Two
            } else if (character == playerTwo) {
                gameState.incrementScore(true); // Score for Player One
            }
            // Delay reset to show who won the round
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (gameState.getScorePlayerOne() >= 3) {
                        game.setScreen(new WinScreen(game,gameState));
                    } else if (gameState.getScorePlayerTwo() >= 3) {
                        game.setScreen(new WinScreen(game,gameState));
                    }
                    resetStage();
                }
            }, 2); // 2 seconds delay
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
        float overlap = (playerOne.getWidth() / 2 + playerTwo.getWidth() / 2) - Math.abs((playerOne.x + playerOne.getWidth() / 2) - (playerTwo.x + playerTwo.getWidth() / 2));

        if (overlap > 0) {
            // Determine if either character is idle
            boolean characterOneIdle = playerOne.currentState == Character.State.IDLE;
            boolean characterTwoIdle = playerTwo.currentState == Character.State.IDLE;

            // Push characters based on their states and speed
            if (!characterOneIdle && !characterTwoIdle) resolveHeadOnCollision(overlap, delta);
            else {
                // One character is idle, determine the pusher and push accordingly
                if (characterOneIdle) {
                    pushCharacter(playerOne, playerTwo, delta, overlap);
                } else {
                    pushCharacter(playerTwo, playerOne, delta, overlap);
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
        float characterOneEffectiveSpeed = playerOne.speed * playerTwo.friction;
        float characterTwoEffectiveSpeed = playerTwo.speed * playerOne.friction;

        playerOne.x -= (overlap / 2) + (characterOneEffectiveSpeed * delta);
        playerTwo.x += (overlap / 2) + (characterTwoEffectiveSpeed * delta);
    }


    protected void resetStage() {
        // Reset characters to their starting positions
        playerOne.x = stageOffsetX + 50;
        playerOne.y = 300;
        playerTwo.x = stageOffsetX + stageWidth - 170;
        playerTwo.y = 300;

        playerOne.currentState = Character.State.IDLE;
        playerTwo.currentState = Character.State.IDLE;

        playerOne.isFrozen = false;
        playerTwo.isFrozen = false;

        gameState.setWaitingForReset(false); // Allow score updates again
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
