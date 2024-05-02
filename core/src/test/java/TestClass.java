import csci.pushoff.*;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
//import static csci.arcane.Main.main;
import static org.junit.jupiter.api.Assertions.*;

public class TestClass {
	@BeforeAll
	public static void init () {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(1600, 960);
		config.setTitle("Push-OFF!");
		config.setResizable(false);
		new Lwjgl3Application(new GdxGameMain(), config);
	}
	@Test
	public void testTest(){
		System.out.println("hello");
	}
}
