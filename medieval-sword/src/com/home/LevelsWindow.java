package com.home;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Constants;
import com.utils.Vector2i;

/**
 * This class represents the window that show the levels of game
 */
public class LevelsWindow extends Group {

  HomeScreen screen;
  TweenManager manager;
  Image black;

  final int N_LEVELS = 4;

  int row1_y = 470;

  public LevelsWindow( HomeScreen screen, Image black, TweenManager manager ) {
    this.screen = screen;
    this.black = black;
    this.manager = manager;

    loadTitle();
    loadExitButton();
    loadLevels();
  }

  private void loadTitle() {
    Image title = new Image( Assets.getTextureRegion(
          "levels" + Constants.LANGUAGE.toUpperCase() ));
    title.x = ( Constants.SIZE_W - title.width ) / 2;
    title.y = Constants.SIZE_H - 190;
    addActor( title );
  }

  private void loadExitButton() {
    Button exit_btn = new Button(
        Assets.getFrame( "btnExitLarge", 1 ),
        Assets.getFrame( "btnExitLarge", 2 ) );

    exit_btn.x = ( Constants.SIZE_W - exit_btn.width ) / 2;
    exit_btn.y = 100;

    exit_btn.setClickListener( new ClickListener() {
      public void click(Actor actor, float x, float y) {
        screen.showHomeWindow( false );
      }
    });

    addActor( exit_btn );
  }

  private void loadLevels() {
    LevelButton button;

    for( int i=0; i < N_LEVELS; i++ ) {
      Vector2i position = new Vector2i( 0, row1_y - 80 * i );

      if( i < Assets.levels.length )
        button = new LevelButton(i, position, screen, (i + 1), true );
      else
        button = new LevelButton(i, position, screen, (i + 1), false );

      addActor( button );
    }
  }
}
