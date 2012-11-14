package com.me.modules.battle;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.me.mygdxgame.*;

public class BattleScreen implements Screen {

	static final int SIZE_W = 480;
	static final int SIZE_H = 280;
	
	private Board board;
	private Player players [];
	private BattleRenderer renderer;
	private BattlePanel panel;
	TweenManager manager;
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		board = new Board();
		
		players = new Player[2];
		
		players[0] = new Player( board );
		players[1] = new Player( board );
		
		panel = new BattlePanel();
		
		renderer = new BattleRenderer( board, players, panel );
		
		manager = new TweenManager();
		Tween.registerAccessor(Unit.class, new UnitAccessor());
		
		Gdx.input.setInputProcessor( new BattleController( board, players, manager, panel, renderer ) );
	}
	
	@Override
	public void render(float delta) {
		manager.update(Gdx.graphics.getDeltaTime());
		
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
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
