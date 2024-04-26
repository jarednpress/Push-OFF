package csci.pushoff.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Baby extends Character {


    public Baby(float x, float y) {
        super(x, y, 250, 0.4f, 45f, 1f, 400, 70, 120, 0);
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("baby.png")));
        this.kickTexture = new TextureRegion(new Texture(Gdx.files.internal("baby_kick.png")));
        this.blockLowTexture = new TextureRegion(new Texture(Gdx.files.internal("baby_block_low.png")));
        this.shoveTexture = new TextureRegion(new Texture(Gdx.files.internal("baby_shove.png")));
        this.blockHighTexture = new TextureRegion(new Texture(Gdx.files.internal("baby_block_high.png")));
    }



    @Override
    public void dispose() {
        super.dispose();
    }
}
