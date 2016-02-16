package me.parkersprouse.games.mtc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import me.parkersprouse.games.mtc.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Main.WIDTH;
		config.height = Main.HEIGHT;
		config.resizable = true;
		config.title = "Match The Colors";
		new LwjglApplication(new Main(), config);
	}
}
