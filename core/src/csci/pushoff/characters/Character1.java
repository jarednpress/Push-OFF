package csci.pushoff.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Character1 extends Character {
    private TextureRegion kickTexture;
    private TextureRegion blockLowTexture;
    private TextureRegion shoveTexture;
    private TextureRegion blockHighTexture;

    public Character1(float x, float y) {
        super(x, y, 200, 0.7f, 40f, 1f, 5f, 150, 150);
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("characterOneTexture.png")));
        this.kickTexture = new TextureRegion(new Texture(Gdx.files.internal("characterOneKick.png")));
        this.blockLowTexture = new TextureRegion(new Texture(Gdx.files.internal("characterOneLow.png")));
        this.shoveTexture = new TextureRegion(new Texture(Gdx.files.internal("characterOneShove.png")));
        this.blockHighTexture = new TextureRegion(new Texture(Gdx.files.internal("characterOneHigh.png")));
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
