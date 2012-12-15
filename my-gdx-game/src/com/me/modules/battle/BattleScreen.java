package com.me.modules.battle;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.mygdxgame.*;

/**
 * Principal class of battle module that implements screen
 */
public class BattleScreen implements Screen {

	static final int SIZE_W = 480;
	static final int SIZE_H = 320;
	
	Board board;
	Player players [];
	BattlePanel panel;
	BattleController controller;
	BattleMenu menu;
	
	Stage stage;	
	TweenManager manager;
	
	/**
	 * Initialize battle screen
	 */
	public void show() {
		stage = new Stage( BattleScreen.SIZE_W, BattleScreen.SIZE_H, true);

		board = new Board( stage );
		
		players = new Player[2];
		
		players[0] = new Player( board, SquareBoard.UNIT_P1 );
		players[1] = new Player( board, SquareBoard.UNIT_P2 );
		
		panel = new BattlePanel( stage );
		
		manager = new TweenManager();
		Tween.registerAccessor(Unit.class, new UnitAccessor());
		Tween.registerAccessor(Image.class, new ImageAccessor());
		
		Gdx.input.setInputProcessor( stage );
		
		menu = new BattleMenu( stage );
		
		controller = new BattleController( board, players, manager, panel, stage, menu );
		
		controller.initBattle();
	}
	
	/**
	 * Update the game
	 */
	public void render(float delta) {
		manager.update( Gdx.graphics.getDeltaTime() );
		
		controller.update();
		
		players[0].update( Gdx.graphics.getDeltaTime() );
		players[1].update( Gdx.graphics.getDeltaTime() ); 
		
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
}
