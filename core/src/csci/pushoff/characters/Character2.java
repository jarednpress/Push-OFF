
package csci.pushoff.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Character2 extends Character {
    //evil baby character
    public Character2(float x, float y) {
        super(x, y, 300, 0.5f, 10f, 1f, 5f, 120, 120);
        this.texture = new Texture("characterTwoTexture.png");
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

}
