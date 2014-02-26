package com.home;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Constants;
import com.game.MyGdxGame;
import com.utils.ImageAlphaAccessor;

/**
 * This class show the Home of game page. This page contains this options:
 * - New game: play level 1 of game.
 * - Continue game: TODO
 * - Exit game: close the game.
 */
public class HomeScreen implements Screen {

	final int BUTTON_W = 220;
	final int BUTTON_H = 110;
	final int SWORD_W = 800;
	final int SWORD_H = 260;

	MyGdxGame game;
	TweenManager manager;
	Stage stage;
	Image black;
	float timer = -1;

	public HomeScreen( MyGdxGame game ) {
		this.game = game;

		manager = new TweenManager();
		stage = new Stage( Constants.SIZE_W, Constants.SIZE_H, true);
		Gdx.input.setInputProcessor( stage );
		Tween.registerAccessor( Image.class, new ImageAlphaAccessor() );

		loadBackground();
		loadSword();
		loadButtons();
		loadBlack();
	}

	private void loadBackground() {
		Image background = new Image( Assets.getTextureRegion( "homeBackground" ));
		background.width = Constants.SIZE_W;
		background.height = Constants.SIZE_H;
		background.x = 0;
		background.y = 0;
		stage.addActor( background );
	}

	private void loadSword() {
		Image sword = new Image( Assets.getTextureRegion( "homeSword" ));
		sword.width = SWORD_W;
		sword.height = SWORD_H;
		sword.x = ( Constants.SIZE_W - sword.width ) / 2;
		sword.y = ( Constants.SIZE_H - sword.height ) / 1.5f;;
		stage.addActor( sword );
	}

	private void loadButtons() {
		int space = 30;
		int aux = ( ( Constants.SIZE_W - BUTTON_W * 3 ) - space * 2 ) / 2;
		int pos_y = 130;

		Button new_game = new Button(
			Assets.getFrame( "btnNewGame" + Constants.LANGUAGE.toUpperCase(), 1 ),
			Assets.getFrame( "btnNewGame" + Constants.LANGUAGE.toUpperCase(), 2 ) );

		new_game.width = BUTTON_W;
		new_game.height = BUTTON_H;
		new_game.x = aux;
		new_game.y = pos_y;

		new_game.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				Assets.playSound( "button", false );

				Timeline line = Timeline.createSequence();
				line.push( Tween.to( black, ImageAlphaAccessor.POSITION_X, 0.6f ).target(1).ease( Linear.INOUT ) );
				line.push( Tween.call( new TweenCallback() {
					public void onEvent(int type, BaseTween<?> source) {
						game.changeScreen( Constants.MAP_SCREEN );
					}
				} ) );
				line.push( Tween.to( black, ImageAlphaAccessor.POSITION_X, 0.5f ).target(0).ease( Linear.INOUT ) );

				line.start( manager );
			}
		});

		Button continue_game = new Button(
				Assets.getFrame( "btnContinueGame" + Constants.LANGUAGE.toUpperCase(), 2 ),
				Assets.getFrame( "btnContinueGame" + Constants.LANGUAGE.toUpperCase(), 2 ) );

		continue_game.width = BUTTON_W;
		continue_game.height = BUTTON_H;
		continue_game.x = aux + BUTTON_W + space;
		continue_game.y = pos_y;

		Button exit_game = new Button(
				Assets.getFrame( "btnExitGame" + Constants.LANGUAGE.toUpperCase(), 1 ),
				Assets.getFrame( "btnExitGame" + Constants.LANGUAGE.toUpperCase(), 2 ) );

		exit_game.width = BUTTON_W;
		exit_game.height = BUTTON_H;
		exit_game.x = Constants.SIZE_W - BUTTON_W - aux;
		exit_game.y = pos_y;

		exit_game.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				Assets.playSound( "button", false );

				Timeline line = Timeline.createSequence();
				line.push( Tween.to( black, ImageAlphaAccessor.POSITION_X, 0.5f ).target(1.f).ease( Linear.INOUT ) );
				line.push( Tween.call( new TweenCallback() {
					public void onEvent(int type, BaseTween<?> source) {
						Gdx.app.exit();
					}
				} ) );

				line.start( manager );
			}
		});

		stage.addActor( new_game );
		stage.addActor( continue_game );
		stage.addActor( exit_game );
	}

	public void loadBlack() {
		black = new Image( Assets.getTextureRegion( "black" ) );
		black.width = Constants.SIZE_W;
		black.height = Constants.SIZE_H;
		black.x = 0;
		black.y = 0;
		black.color.a = 0;

		stage.addActor( black );
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		manager.update( Gdx.graphics.getDeltaTime() );
		stage.act( Gdx.graphics.getDeltaTime() );
		stage.draw();
	}

	public void show() {
		Gdx.input.setInputProcessor( stage );
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
