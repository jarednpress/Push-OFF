package csci.pushoff.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class wheelchairKid extends Character {

    public wheelchairKid(float x, float y) {
        super(x, y, 300, 0.7f, 40f, 1f, 400, 140, 155, 0);
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("wheelchairKid.png")));
        this.kickTexture = new TextureRegion(new Texture(Gdx.files.internal("wheelchairKid_kick.png")));
        this.blockLowTexture = new TextureRegion(new Texture(Gdx.files.internal("wheelchairKid_block_low.png")));
        this.shoveTexture = new TextureRegion(new Texture(Gdx.files.internal("wheelchairKid_shove.png")));
        this.blockHighTexture = new TextureRegion(new Texture(Gdx.files.internal("wheelchairKid_block_high.png")));

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
