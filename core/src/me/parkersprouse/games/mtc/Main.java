package me.parkersprouse.games.mtc;

import com.badlogic.gdx.Game;

public class Main extends Game {
	
	public static int WIDTH = 1024;
	public static int HEIGHT = 768;
	
	@Override
	public void create () {
		this.setScreen(new MainMenu(this));
	}
	
}
