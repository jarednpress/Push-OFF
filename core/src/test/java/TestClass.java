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
		System.out.println("Testing Suite Works");
	}

	@Test
	public void testFatman(){
		Character FatMan = CharacterFactory.makeCharacter(0,0,0);
		assertEquals(150,FatMan.getWidth());

	}
	@Test
	public  void testBaby(){
		Character baby = CharacterFactory.makeCharacter(1,0,0);
		assertEquals(70,baby.getWidth());

	}
	@Test
	public void testCaptainCash(){
		Character CaptainCash = CharacterFactory.makeCharacter(2,0,0);


		assertEquals(80,CaptainCash.getWidth());

	}
	@Test
	public void testWheelchairKid(){
		Character WheelChairKid = CharacterFactory.makeCharacter(3,0,0);
		assertEquals(140,WheelChairKid.getWidth());
	}




}
