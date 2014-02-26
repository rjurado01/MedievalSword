package com.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.MyGdxGame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "my-gdx-game";
		cfg.useGL20 = false;
		cfg.width = 1152;
		cfg.height = 768;
		cfg.fullscreen = false;
		
		/*DisplayMode[] modes = LwjglApplicationConfiguration.getDisplayModes();
		for( int i = 0; i < modes.length; i++ )
			System.out.println( modes[i] );*/
		
		new LwjglApplication(new MyGdxGame(), cfg);
	}
}
