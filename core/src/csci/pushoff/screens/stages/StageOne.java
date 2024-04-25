package csci.pushoff.screens.stages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import csci.pushoff.GdxGameMain;

public class StageOne extends StageTemplate {

    private Texture background;
    private Texture platform;

    public StageOne(GdxGameMain game) {
        super(game);
        this.stageWidth = 900; //specific width for StageOne
    }

    @Override
    public void show() {
        super.show();
        background = new Texture("stage_1_background.jpg");
        platform = new Texture("stage_1_platform.jpg");
    }

    @Override
    public void render(float delta) {
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
