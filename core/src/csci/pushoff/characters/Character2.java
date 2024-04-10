package csci.pushoff.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Character2 extends Character {
    private TextureRegion kickTexture;
    private TextureRegion blockLowTexture;
    private TextureRegion shoveTexture;
    private TextureRegion blockHighTexture;

    public Character2(float x, float y) {
        super(x, y, 250, 0.4f, 45f, 1f, 5f, 70, 120);
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("characterTwoTexture.png")));
        this.kickTexture = new TextureRegion(new Texture(Gdx.files.internal("characterTwoKick.png")));
        this.blockLowTexture = new TextureRegion(new Texture(Gdx.files.internal("characterTwoLow.png")));
        this.shoveTexture = new TextureRegion(new Texture(Gdx.files.internal("characterTwoShove.png")));
        this.blockHighTexture = new TextureRegion(new Texture(Gdx.files.internal("characterTwoHigh.png")));
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Determine which texture to use based on the character's state
        TextureRegion currentTexture = this.texture; // Default texture
        if (this.currentState == State.KICKING) {
            currentTexture = this.kickTexture;
        } else if (this.currentState == State.BLOCKING_LOW) {
            currentTexture = this.blockLowTexture;
        } else if (this.currentState == State.SHOVING)  {
            currentTexture = this.shoveTexture;
        } else if (this.currentState == State.BLOCKING_HIGH) {
            currentTexture = this.blockHighTexture;
        }

        // Check if the texture needs to be flipped based on the character's facing direction
        if ((currentTexture.isFlipX() && facingRight) || (!currentTexture.isFlipX() && !facingRight)) {
            currentTexture.flip(true, false);
        }

        batch.draw(currentTexture, x, y, width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (kickTexture != null && kickTexture.getTexture() != null) kickTexture.getTexture().dispose();
        // dispose of low block and other textures also
    }
}
