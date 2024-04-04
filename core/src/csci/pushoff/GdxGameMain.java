package csci.pushoff;

import com.badlogic.gdx.Game;
import csci.pushoff.screens.TitleScreen;

public class GdxGameMain extends Game {

	private int playerOneCharacterIndex = -1;
	private int playerTwoCharacterIndex = -1;
	private int stageSelectionIndex = -1;

	public void setStageSelectionIndex(int index){
		this.stageSelectionIndex = index;
	}

	public void setPlayerOneCharacterIndex(int index) {
		this.playerOneCharacterIndex = index;
	}

	public void setPlayerTwoCharacterIndex(int index) {
		this.playerTwoCharacterIndex = index;
	}

	public int getPlayerOneCharacterIndex() {
		return playerOneCharacterIndex;
	}

	public int getPlayerTwoCharacterIndex() {
		return playerTwoCharacterIndex;
	}
	public int getStageIndex() {
		return stageSelectionIndex;
	}

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
