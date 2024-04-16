package csci.pushoff.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Baby extends Character {


    public Baby(float x, float y) {
        super(x, y, 250, 0.4f, 45f, 1f, 5f, 70, 120);
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("characterTwoTexture.png")));
        this.kickTexture = new TextureRegion(new Texture(Gdx.files.internal("characterTwoKick.png")));
        this.blockLowTexture = new TextureRegion(new Texture(Gdx.files.internal("characterTwoLow.png")));
        this.shoveTexture = new TextureRegion(new Texture(Gdx.files.internal("characterTwoShove.png")));
        this.blockHighTexture = new TextureRegion(new Texture(Gdx.files.internal("characterTwoHigh.png")));
    }



    @Override
    public void dispose() {
        super.dispose();
        //if (kickTexture != null && kickTexture.getTexture() != null) kickTexture.getTexture().dispose();
        // dispose of low block and other textures also
    }
}
