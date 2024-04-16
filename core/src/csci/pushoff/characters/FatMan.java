package csci.pushoff.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FatMan extends Character {

    public FatMan(float x, float y) {
        super(x, y, 200, 0.7f, 40f, 1f, 5f, 150, 150);
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("characterOneTexture.png")));
        this.kickTexture = new TextureRegion(new Texture(Gdx.files.internal("characterOneKick.png")));
        this.blockLowTexture = new TextureRegion(new Texture(Gdx.files.internal("characterOneLow.png")));
        this.shoveTexture = new TextureRegion(new Texture(Gdx.files.internal("characterOneShove.png")));
        this.blockHighTexture = new TextureRegion(new Texture(Gdx.files.internal("characterOneHigh.png")));
    }


    @Override
    public void dispose() {
        super.dispose();
        //if (kickTexture != null && kickTexture.getTexture() != null) kickTexture.getTexture().dispose();
        // dispose of low block and other textures also
    }
}
