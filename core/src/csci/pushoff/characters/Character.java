package csci.pushoff.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Character {
    // Position
    public float x, y;

    // Movement speed
    public float speed;

    // Character state
    public enum State {
        IDLE, MOVING_LEFT, MOVING_RIGHT, SHOVING, KICKING, BLOCKING_HIGH, BLOCKING_LOW
    }
    public State currentState = State.IDLE;

    // Game-specific parameters
    public float friction;
    public float shoveKnockback;
    public float hitstunDuration;
    public float shieldStrength;
    protected TextureRegion texture;
    protected TextureRegion kickTexture;
    protected TextureRegion blockLowTexture;
    protected TextureRegion shoveTexture;
    protected TextureRegion blockHighTexture;
    protected boolean facingRight;
    public boolean isFrozen = false;
    protected float width, height;
    protected float frames;


    // Constructor
    public Character(float x, float y, float speed, float friction, float shoveKnockback, float hitstunDuration, float shieldStrength, float width, float height, float frames) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.friction = friction;
        this.shoveKnockback = shoveKnockback;
        this.hitstunDuration = hitstunDuration;
        this.shieldStrength = shieldStrength;
        this.width = width;
        this.height = height;
        this.frames = frames; //frame counter makes character animations stick during recovery
    }

    public float getFrames(){
        return this.frames;
    }
    public void setFrames(float f){
        this.frames = f;
    }

    public void setWidth(float w){
        this.width = w;
    }

    public float getWidth(){
        return this.width;
    }
    public boolean getFacingRight() { return this.facingRight; }

    //public abstract void draw(SpriteBatch batch);

    public void moveLeft(float delta) {
        x -= speed * delta;
        currentState = State.MOVING_LEFT;
        facingRight = false;
    }

    public void moveRight(float delta) {
        x += speed * delta;
        currentState = State.MOVING_RIGHT;
        facingRight = true;
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentTexture = this.texture; // Default texture
        if (frames < 1){
            // Determine which texture to use based on the character's state
            if (this.currentState == State.KICKING) {
                currentTexture = this.kickTexture;
            } else if (this.currentState == State.BLOCKING_LOW) {
                currentTexture = this.blockLowTexture;
            } else if (this.currentState == State.SHOVING)  {
                currentTexture = this.shoveTexture;
            } else if (this.currentState == State.BLOCKING_HIGH) {
                currentTexture = this.blockHighTexture;
            }
        }
        else{
            if (frames % 1 != 0){
                currentTexture = this.shoveTexture;
            }
            else{ currentTexture = this.kickTexture; }
        }
        // Check if the texture needs to be flipped based on the character's facing direction
        if ((currentTexture.isFlipX() && facingRight) || (!currentTexture.isFlipX() && !facingRight)) {
            currentTexture.flip(true, false);
        }
        batch.draw(currentTexture, x, y, width, height);
    }
    public void dispose() {
        if (texture != null && texture.getTexture() != null) texture.getTexture().dispose();
    }
}
