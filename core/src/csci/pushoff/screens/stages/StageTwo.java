package csci.pushoff.screens.stages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import csci.pushoff.GdxGameMain;

public class StageTwo extends StageTemplate {

    private Texture background;
    private Texture platform;

    public StageTwo(GdxGameMain game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        background = new Texture("stage_2_background.jpg");
        platform = new Texture("stage_1_platform.jpg"); //will be stage_2_platform.jpg
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(platform, (Gdx.graphics.getWidth() - platform.getWidth()) / 2f, 0); // You'll need to set the correct Y position for the platform
        batch.end();

        // HUD
        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
        background.dispose();
        platform.dispose();
    }
}
