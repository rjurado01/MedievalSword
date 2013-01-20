package com.me.modules.battle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.mygdxgame.*;

/**
 * Principal class of battle module that implements screen
 */
public class BattleScreen implements Screen {
	
	Army armies [];
	BattleController controller;
	Stage stage;
	Game game;
	
	/**
	 * Class constructor
	 */
	public BattleScreen( Game game, Army player, Army enemy ) {
		this.game = game;
		
		armies = new Army[2];
		armies[0] = player;
		armies[1] = enemy;
	}
	
	private void addArmiesToStage() {
		for( int i = 0; i < 2; i++ )
			for( Stack stack : armies[i].getStacks() )
				stage.addActor( stack.getView() );
	}
	
	/**
	 * Initialize battle screen
	 */
	public void show() {
		stage = new Stage( Constants.SIZE_W, Constants.SIZE_H, true);	
		Gdx.input.setInputProcessor( stage );
		
		addArmiesToStage();
		
		controller = new BattleController( armies, stage );		
		controller.initBattle();
	}
	
	/**
	 * Update the game
	 */
	public void render(float delta) {
		controller.update();
		
		armies[0].update( Gdx.graphics.getDeltaTime() );
		armies[1].update( Gdx.graphics.getDeltaTime() ); 
		
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act( Gdx.graphics.getDeltaTime() );
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	public void setInput() {
		Gdx.input.setInputProcessor( stage );
	}
}
