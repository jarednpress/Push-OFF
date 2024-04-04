package csci.pushoff;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(1600, 960);
		config.setTitle("Push-OFF!");
		config.setResizable(false);
		new Lwjgl3Application(new GdxGameMain(), config);
	}
}
