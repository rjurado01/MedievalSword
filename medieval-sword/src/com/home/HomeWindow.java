package com.home;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Constants;
import com.utils.ImageAlphaAccessor;

/**
 * This class show the Home window with. This page contains this options:
 * - New game: play level 1 of game.
 * - Continue game: TODO
 * - Exit game: close the game.
 */
public class HomeWindow extends Group {

  final int BUTTON_W = 214;
  final int BUTTON_H = 110;
  final int SWORD_W = 800;
  final int SWORD_H = 260;

  HomeScreen screen;
  TweenManager manager;
  Image black;

  public HomeWindow( HomeScreen screen, Image black, TweenManager manager ) {
    this.screen = screen;
    this.black = black;
    this.manager = manager;

    loadSword();
    loadButtons();
  }

  private void loadSword() {
    Image sword = new Image( Assets.getTextureRegion( "homeSword" ));
    sword.width = SWORD_W;
    sword.height = SWORD_H;
    sword.x = ( Constants.SIZE_W - sword.width ) / 2;
    sword.y = ( Constants.SIZE_H - sword.height ) / 1.5f;;
    addActor( sword );
  }

  private void loadButtons() {
    int space = 25;
    int aux = ( ( Constants.SIZE_W - BUTTON_W * 4 ) - space * 3 ) / 2;
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
        screen.showLevelsWindow();
      }
    });

    Button continue_game = new Button(
        Assets.getFrame( "btnContinueGame" + Constants.LANGUAGE.toUpperCase(), 1 ),
        Assets.getFrame( "btnContinueGame" + Constants.LANGUAGE.toUpperCase(), 2 ) );

    continue_game.width = BUTTON_W;
    continue_game.height = BUTTON_H;
    continue_game.x = aux + BUTTON_W + space;
    continue_game.y = pos_y;

    continue_game.setClickListener( new ClickListener() {
      public void click(Actor actor, float x, float y) {
        Assets.playSound( "button", false );
        Timeline line = Timeline.createSequence();
        line.push( Tween.to( black, ImageAlphaAccessor.POSITION_X, 0.5f ).target(1.f).ease( Linear.INOUT ) );
        line.start( manager );

        screen.game.loadSavedGame();
      }
    });

    Button options = new Button(
        Assets.getFrame( "btnOptionsGame" + Constants.LANGUAGE.toUpperCase(), 1 ),
        Assets.getFrame( "btnOptionsGame" + Constants.LANGUAGE.toUpperCase(), 2 ) );

    options.width = BUTTON_W;
    options.height = BUTTON_H;
    options.x = aux + ( BUTTON_W + space ) * 2;
    options.y = pos_y;

    options.setClickListener( new ClickListener() {
      public void click(Actor actor, float x, float y) {
        Assets.playSound( "button", false );
        screen.showOptionsWindow();
      }
    });

    Button exit_game = new Button(
        Assets.getFrame( "btnExitGame" + Constants.LANGUAGE.toUpperCase(), 1 ),
        Assets.getFrame( "btnExitGame" + Constants.LANGUAGE.toUpperCase(), 2 ) );

    exit_game.width = BUTTON_W;
    exit_game.height = BUTTON_H;
    exit_game.x = Constants.SIZE_W - BUTTON_W - aux;
    exit_game.y = pos_y;

    exit_game.setClickListener( new ClickListener() {
      public void click(Actor actor, float x, float y) {
        screen.setBlackTop();
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

    addActor( new_game );
    addActor( continue_game );
    addActor( options );
    addActor( exit_game );
  }
}
