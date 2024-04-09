
package csci.pushoff.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Character1 extends Character {
    //this is the evil little fat kid - short arms so cant shove hard
    //but he is heavy with good traction
    public Character1(float x, float y) {
        super(x, y, 200, 0.5f, 10f, 1f, 5f, 150, 150);
        this.texture = new Texture("characterOneTexture.png");
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

}
