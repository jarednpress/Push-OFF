import csci.pushoff.*;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import csci.pushoff.GdxGameMain;

import csci.pushoff.characters.Character;
import csci.pushoff.characters.CharacterFactory;
import csci.pushoff.screens.CharacterSelectScreen;
import csci.pushoff.screens.stages.StageTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

public class TestClass {
	private static final GdxGameMain game = new GdxGameMain();
	@BeforeAll
	public static void init () {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(1600, 960);
		config.setTitle("Push-OFF!");
		config.setResizable(false);

		new Lwjgl3Application(game, config);
	}
	@Test
	public void testTest(){
		System.out.println("hello");
	}
	@Test
	public void testAudioFacade(){
		AudioFacade audioFacade = new AudioFacade();
		audioFacade.play();
		audioFacade.setVolume(10);
		assertEquals(10, audioFacade.getVolume());
	}
	@Test
	public void testCharacters(){
		Character mainCharacter = CharacterFactory.makeCharacter(0,0,0);
		assertEquals(150,mainCharacter.getWidth());
		Character mainCharacter2 = CharacterFactory.makeCharacter(1,0,0);
		Character mainCharacter3 = CharacterFactory.makeCharacter(2,0,0);
		Character mainCharacter4 = CharacterFactory.makeCharacter(3,0,0);
		assertEquals(70,mainCharacter2.getWidth());
		assertEquals(80,mainCharacter3.getWidth());
		assertEquals(140,mainCharacter4.getWidth());
	}
	@Test
	public void stageTemplate(){
		StageTemplate stage = new StageTemplate(game);
		Character mainCharacter = CharacterFactory.makeCharacter(0,0,0);
		stage.freezeCharacter(mainCharacter, 10);
		assertTrue(mainCharacter.isFrozen);

	}


}
