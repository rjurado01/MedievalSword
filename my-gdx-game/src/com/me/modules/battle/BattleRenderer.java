package com.me.modules.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BattleRenderer {
	
	public static Stage stage;	

	/**
	 * Class constructor
	 */
	public BattleRenderer() {
		stage = new Stage( BattleScreen.SIZE_W, BattleScreen.SIZE_H, true);
		
		Gdx.gl.glClearColor(0,0,0,1);
	}

	/**
	 * Render game
	 */
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(Gdx.graphics.getDeltaTime());
		
		stage.draw();
	}
}