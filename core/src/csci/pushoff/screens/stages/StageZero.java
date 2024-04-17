package csci.pushoff.screens.stages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import csci.pushoff.GdxGameMain;

public class StageZero extends StageTemplate {

    private Texture background;
    private Texture platform;

    public StageZero(GdxGameMain game) {
        super(game);
        this.stageWidth = 500; //will be different
    }

    @Override
    public void show() {
        super.show();
        background = new Texture("stage_zero_background.jpg");
        platform = new Texture("stage_0_platform.png"); //will be stage_zero_platform.jpg
    }

    @Override
    public void render(float delta) {
        // render background
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(platform, stageOffsetX, 0, stageWidth, 300);
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
