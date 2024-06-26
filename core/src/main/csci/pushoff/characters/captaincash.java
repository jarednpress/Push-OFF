package csci.pushoff.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class captaincash extends Character {

    public captaincash(float x, float y) {
        super(x, y, 260, 0.7f, 40f, 1f, 400, 80, 170, 0);
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("captaincash.png")));
        this.kickTexture = new TextureRegion(new Texture(Gdx.files.internal("captaincash_kick.png")));
        this.blockLowTexture = new TextureRegion(new Texture(Gdx.files.internal("captaincash_block_low.png")));
        this.shoveTexture = new TextureRegion(new Texture(Gdx.files.internal("captaincash_shove.png")));
        this.blockHighTexture = new TextureRegion(new Texture(Gdx.files.internal("captaincash_block_high.png")));

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
