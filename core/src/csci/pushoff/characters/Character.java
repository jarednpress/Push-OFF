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
    protected boolean facingRight;
    public boolean isFrozen = false;
    protected float width, height;

    // Constructor
    public Character(float x, float y, float speed, float friction, float shoveKnockback, float hitstunDuration, float shieldStrength, float width, float height) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.friction = friction;
        this.shoveKnockback = shoveKnockback;
        this.hitstunDuration = hitstunDuration;
        this.shieldStrength = shieldStrength;
        this.width = width;
        this.height = height;
    }

    public float getWidth(){
        return this.width;
    }
    public boolean getFacingRight() { return this.facingRight; }

    public abstract void draw(SpriteBatch batch);

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

    public void dispose() {
        if (texture != null && texture.getTexture() != null) texture.getTexture().dispose();
    }

    // Methods for actions like shove, trip, block, etc., can also be abstract if they vary by character
}
