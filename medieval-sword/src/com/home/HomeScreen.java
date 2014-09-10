package com.home;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Constants;
import com.game.MyGdxGame;
import com.utils.ImageAlphaAccessor;

/**
 * This class is the screen of Home where it is rendered all windows of home:
 * - HomeWindow
 * - LevelsWindow
 * - OptionsWindow
 */
public class HomeScreen implements Screen {

  MyGdxGame game;
  TweenManager manager;
  Stage stage;
  Image black;
  float timer = -1;

  HomeWindow home_window;
  OptionsWindow options_window;
  LevelsWindow levels_window;

  public HomeScreen( MyGdxGame game ) {
    this.game = game;
    this.manager = new TweenManager();
    this.stage = new Stage( Constants.SIZE_W, Constants.SIZE_H, true);

    loadBackground();

    home_window = new HomeWindow( this, black, manager );
    stage.addActor( home_window );

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
    Tween.registerAccessor( Image.class, new ImageAlphaAccessor() );

    black.color.a = 1;
    Timeline line = Timeline.createSequence();
    line.push( Tween.to( black, ImageAlphaAccessor.POSITION_X, 0.5f ).target(0.f).ease( Linear.INOUT ) );
    line.start( manager );
  }

  public void changeScreen( int id ) {
    game.changeScreen( id );
  }

  public void showOptionsWindow() {
    home_window.visible = false;

    if( options_window != null )
      stage.removeActor( options_window );

    options_window = new OptionsWindow( this, black, manager );
    stage.addActor( options_window );
  }

  public void showLevelsWindow() {
    home_window.visible = false;

    if( levels_window != null )
      stage.removeActor( levels_window );

    levels_window = new LevelsWindow( this, black, manager );
    stage.addActor( levels_window );
  }

  public void showHomeWindow( boolean update ) {
    home_window.visible = true;

    if( options_window != null )
      options_window.visible = false;

    if( levels_window != null )
      levels_window.visible = false;

    if( update ) {
      stage.removeActor( home_window );
      home_window = new HomeWindow( this, black, manager );
      stage.addActor( home_window );
    }
  }

  public void setBlackTop() {
    stage.removeActor( black );
    stage.addActor( black );
  }

  @Override
  public void resize(int width, int height) {
    /*// Save aspect ratio
      stage.setViewport( Constants.SIZE_W, Constants.SIZE_H, false );
      float crop_x = ( Constants.SIZE_W - stage.getCamera().viewportWidth) / 2;
      stage.getCamera().translate( crop_x, 0, 0 );*/
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
