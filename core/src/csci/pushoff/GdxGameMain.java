package csci.pushoff;

import com.badlogic.gdx.Game;
import csci.pushoff.screens.TitleScreen;

public class GdxGameMain extends Game {

	@Override
	public void create() {
		this.setScreen(new TitleScreen(this));
	}

	@Override
	public void render() {
		super.render(); // Important to call render on the current screen
	}

	@Override
	public void dispose() {
		// Dispose resources if needed
	}
}
