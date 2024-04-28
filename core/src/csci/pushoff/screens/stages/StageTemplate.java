package csci.pushoff.screens.stages;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import csci.pushoff.GameState;
import csci.pushoff.GdxGameMain;
import com.badlogic.gdx.Input;
import csci.pushoff.characters.Character;
import csci.pushoff.characters.CharacterFactory;
import csci.pushoff.screens.WinScreen;

public class StageTemplate implements Screen {

    protected GdxGameMain game;
    protected SpriteBatch batch;
    private BitmapFont font;
    protected Texture player1Icon;
    protected Texture player2Icon;
    protected Texture dot;
    protected Texture dotWinTexture;
    protected Texture logoBlank;
    protected Texture logo;
    protected Character playerOne;
    protected Character playerTwo;
    protected int stageWidth;
    protected float stageOffsetX;
    protected float p1Width;
    protected float p1WidthWide;
    protected float p2Width;
    protected float p2WidthWide;
    protected GameState gameState;


    // Fixed dot size, doubled
    protected final float dotSize = 40f; // Size for normal dots
    protected final float largeDotSize = dotSize * 2f; // Size for the larger middle dot
    protected final float dotSpacing = 60f; // Space between dots

    protected ShapeRenderer shapeRenderer;

    // Constants for stamina bar
    private static final float MAX_STAMINA = 400f;
    private static final float STAMINA_BAR_WIDTH = 200f; // Double the icon width for example
    private static final float STAMINA_BAR_HEIGHT = 20f;

    public StageTemplate(GdxGameMain game) {
        this.game = game;
        // Initialization in constructor or show method
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        gameState = new GameState();
        player1Icon = new Texture("character" + game.getPlayerOneCharacterIndex() + "Preview.png");
        player2Icon = new Texture("character" + game.getPlayerTwoCharacterIndex() + "Preview.png");
        dot = new Texture("dot.png");
        dotWinTexture = new Texture("dotWin.png");
        logoBlank = new Texture("logo.png");
        logo = new Texture("logoWin.png");

        stageOffsetX = (Gdx.graphics.getWidth() - stageWidth) / 2f;
        playerOne = CharacterFactory.makeCharacter(game.getPlayerOneCharacterIndex(),stageOffsetX + 50, 300);
        playerTwo = CharacterFactory.makeCharacter(game.getPlayerTwoCharacterIndex(), stageOffsetX + stageWidth - 170, 300);
        p1Width = playerOne.getWidth();
        p1WidthWide = p1Width + 30;
        p2Width = playerTwo.getWidth();
        p2WidthWide = p2Width + 30;
    }

    @Override
    public void render(float delta) {

        updateCharacters(delta);

        // Draw characters and face towards middle
        batch.begin();
        playerOne.draw(batch);
        playerTwo.draw(batch);
        playerOne.moveRight(0); //face right
        playerTwo.moveLeft(0);//face left
        batch.end();

        // draw score-keeping dots
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
        Texture middleDotTexture = logoBlank;
        if (scorePlayerOne >= 3 || scorePlayerTwo >= 3) {
            middleDotTexture = logo;
        }
        batch.draw(middleDotTexture, middleDotX, Gdx.graphics.getHeight() - largeDotSize - 20, largeDotSize, largeDotSize);
        batch.end();

        //stamina bar
        float p1StaminaX = 10; // Same X as player 1 icon
        float p2StaminaX = Gdx.graphics.getWidth() - STAMINA_BAR_WIDTH - 10; // Adjust for player 2
        float staminaY = Gdx.graphics.getHeight() - largeDotSize - STAMINA_BAR_HEIGHT - 30; // Below the icon
        // Draw background and filled part of the stamina bar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // Draw background (black)
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(p1StaminaX, staminaY, STAMINA_BAR_WIDTH, STAMINA_BAR_HEIGHT);
        shapeRenderer.rect(p2StaminaX, staminaY, STAMINA_BAR_WIDTH, STAMINA_BAR_HEIGHT);
        // Draw filled part (red)
        shapeRenderer.setColor(Color.RED);
        float p1FillWidth = STAMINA_BAR_WIDTH * (playerOne.getStamina() / MAX_STAMINA);
        float p2FillWidth = STAMINA_BAR_WIDTH * (playerTwo.getStamina() / MAX_STAMINA);
        shapeRenderer.rect(p1StaminaX, staminaY, p1FillWidth, STAMINA_BAR_HEIGHT);
        shapeRenderer.rect(p2StaminaX, staminaY, p2FillWidth, STAMINA_BAR_HEIGHT);
        shapeRenderer.end();
    }

    protected void updateCharacters(float delta) {
        // player 1 controls
        float p1StartStamina = playerOne.getStamina();
        if (playerOne.getStamina() > 1) {
            if (playerOne.getFrames() > 1){ playerOne.setWidth(p1WidthWide); }
            else{playerOne.setWidth(p1Width);}
            playerOne.setFrames(playerOne.getFrames() - 1);
            if(playerOne.getFrames() < 1){
                if (!playerOne.isFrozen) {
                    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                        if (Gdx.input.isKeyPressed(Input.Keys.E) || Gdx.input.isKeyPressed(Input.Keys.Q)) {
                            delta = delta / 4;
                        }
                        playerOne.moveLeft(delta);
                        playerOne.loseStamina(1);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                        if (Gdx.input.isKeyPressed(Input.Keys.E) || Gdx.input.isKeyPressed(Input.Keys.Q)) {
                            delta = delta / 4;
                        }
                        playerOne.moveRight(delta);
                        playerOne.loseStamina(1);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
                        playerOne.currentState = Character.State.BLOCKING_LOW;
                        playerOne.loseStamina(1);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.E)) {
                        playerOne.currentState = Character.State.BLOCKING_HIGH;
                        playerOne.loseStamina(1);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                        playerOne.currentState = Character.State.KICKING;
                        performAction(playerOne, playerTwo, true);
                        playerOne.setFrames(30);
                        playerOne.loseStamina(40);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                        playerOne.currentState = Character.State.SHOVING;
                        performAction(playerOne, playerTwo, false);
                        playerOne.setFrames(60.5f); //.5 is to identify shove
                        playerOne.loseStamina(80);
                    }
                }
            }
        }
        else { freezeCharacter(playerOne, 3); } //punishment for running out of stamina
        if ((playerOne.getStamina() == p1StartStamina) && (playerOne.getStamina() < 400)) {
            playerOne.addStamina(); // if character doesn't move and stamina isn't already full
        }

        //player 2 controls
        float p2StartStamina = playerTwo.getStamina();
        if (playerTwo.getStamina() > 1){
            if (playerTwo.getFrames() > 1){ playerTwo.setWidth(p2WidthWide); }
            else{playerTwo.setWidth(p2Width);}
            playerTwo.setFrames(playerTwo.getFrames() - 1);
            if(playerTwo.getFrames() < 1) {
                if (!playerTwo.isFrozen) {
                    if (Gdx.input.isKeyPressed(Input.Keys.J)) {
                        if (Gdx.input.isKeyPressed(Input.Keys.U) || Gdx.input.isKeyPressed(Input.Keys.O)) {
                            delta = delta / 4;
                        }
                        playerTwo.moveLeft(delta);
                        playerTwo.loseStamina(1);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.L)) {
                        if (Gdx.input.isKeyPressed(Input.Keys.U) || Gdx.input.isKeyPressed(Input.Keys.O)) {
                            delta = delta / 4;
                        }
                        playerTwo.moveRight(delta);
                        playerTwo.loseStamina(1);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.U)) {
                        playerTwo.currentState = Character.State.BLOCKING_LOW;
                        playerTwo.loseStamina(1);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.O)) {
                        playerTwo.currentState = Character.State.BLOCKING_HIGH;
                        playerTwo.loseStamina(1);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.K)) {
                        playerTwo.currentState = Character.State.KICKING;
                        performAction(playerTwo, playerOne, true);
                        playerTwo.setFrames(30);
                        playerTwo.loseStamina(40);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.I)) {
                        playerTwo.currentState = Character.State.SHOVING;
                        performAction(playerTwo, playerOne, false);
                        playerTwo.setFrames(60.5f); //.5 is to identify shove
                        playerTwo.loseStamina(80);
                    }
                }
            }
        }
        else { freezeCharacter(playerTwo, 3); } //punishment for running out of stamina
        if ((playerTwo.getStamina() == p2StartStamina) && (playerTwo.getStamina() < 400)) {
            playerTwo.addStamina(); // if character doesn't move and stamina isn't already full
        }

        // now that movement happened for this frame, see what's going on and react accordingly
        handleCharacterCollision(delta);
        checkCharacterStageBounds(playerOne, playerTwo);
        checkCharacterStageBounds(playerTwo, playerOne);
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
            checkCharacterStageBounds(receiver, initiator);
            freezeCharacter(receiver, initiator.hitstunDuration / 4); // Freeze receiver briefly
        }

        // Always freeze the initiator after action
        freezeCharacter(initiator, initiator.hitstunDuration / 2);
    }

    protected void checkCharacterStageBounds(Character character, Character opponent) {
        float characterMidpoint = character.x + character.getWidth() / 2f;
        boolean fellOffLeft = characterMidpoint < stageOffsetX;
        boolean fellOffRight = characterMidpoint > (stageOffsetX + stageWidth);

        if ((fellOffLeft || fellOffRight) && !gameState.isWaitingForReset()) {
            gameState.setWaitingForReset(true); // Prevent further score updates until reset

            //set fallen character to be 10 pixels away from side they fell from
            character.y = 0;
            if (fellOffLeft) { character.x = stageOffsetX - character.getWidth() - 10;
            } else { character.x = stageOffsetX + stageWidth + 10; }

            if (character == playerOne) {
                gameState.incrementScore(false); // Score for Player Two
            } else if (character == playerTwo) {
                gameState.incrementScore(true); // Score for Player One
            }

            // Delay reset to show who won the round
            freezeCharacter(opponent, 2);
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

    protected void handleCharacterCollision(float delta) {
        float overlap = (playerOne.getWidth() / 2 + playerTwo.getWidth() / 2) - Math.abs((playerOne.x + playerOne.getWidth() / 2) - (playerTwo.x + playerTwo.getWidth() / 2));

        if (overlap > 0) {
            // Determine if either character is idle
            boolean characterOneIdle = playerOne.currentState == Character.State.IDLE;
            boolean characterTwoIdle = playerTwo.currentState == Character.State.IDLE;

            if (!characterOneIdle && !characterTwoIdle) {
                //resolve head on collision
                float characterOneEffectiveSpeed = playerOne.speed * playerTwo.friction;
                float characterTwoEffectiveSpeed = playerTwo.speed * playerOne.friction;

                // Both characters are moving, split the overlap resolution and apply friction to speed
                playerOne.x -= (overlap / 2) + (characterOneEffectiveSpeed * delta);
                playerTwo.x += (overlap / 2) + (characterTwoEffectiveSpeed * delta);
            }

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
        playerOne.refillStamina();
        playerTwo.refillStamina();

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
        dotWinTexture.dispose();
        logo.dispose();
        logoBlank.dispose();
    }
}
