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
 * This class represents the window that show the options of game
 */
public class OptionsWindow extends Group {

  HomeScreen screen;
  TweenManager manager;
  Image black;

  String languages [] = { "English", "Spanish" };
  String difficulties [] = { "Normal" };
  String resolutions [] = { "----" };

  String language_tag [] = { "Language", "Idioma" };
  String difficulty_tag [] = { "Difficulty", "Dificultad" };
  String resolution_tag [] = { "Resolution", "Resolucion" };

  int col1_x = 370;
  int row1_y = 450;
  int row2_y = 350;
  int row3_y = 250;

  OptionSelector language_option;
  OptionSelector difficulty_option;
  OptionSelector resolution_option;

  boolean update = false;

  public OptionsWindow( HomeScreen screen, Image black, TweenManager manager ) {
    this.screen = screen;
    this.black = black;
    this.manager = manager;

    loadTitle();
    loadExitButton();
    loadOptions();
  }

  private void loadTitle() {
    Image title = new Image( Assets.getTextureRegion(
          "options" + Constants.LANGUAGE.toUpperCase() ));
    title.x = ( Constants.SIZE_W - title.width ) / 2;
    title.y = Constants.SIZE_H - 190;
    addActor( title );
  }

  private void loadOptions() {
    language_option = new OptionSelector(
        new Vector2i( col1_x, row1_y ),
        language_tag[ Constants.LANGUAGE_CODE ],
        languages,
        Constants.LANGUAGE_CODE );

    difficulty_option = new OptionSelector(
        new Vector2i( col1_x, row2_y ),
        difficulty_tag[ Constants.LANGUAGE_CODE ],
        difficulties,
        0 );

    resolution_option = new OptionSelector(
        new Vector2i( col1_x, row3_y ),
        resolution_tag[ Constants.LANGUAGE_CODE ],
        resolutions,
        0 );

    addActor( language_option );
    addActor( difficulty_option );
    addActor( resolution_option );
  }

  private void loadExitButton() {
    Button exit_btn = new Button(
        Assets.getFrame( "okButton", 1 ),
        Assets.getFrame( "okButton", 2 ) );

    exit_btn.width = 100;
    exit_btn.height = 50;
    exit_btn.x = ( Constants.SIZE_W - exit_btn.width ) / 2;
    exit_btn.y = 100;

    exit_btn.setClickListener( new ClickListener() {
      public void click(Actor actor, float x, float y) {
        if( Constants.LANGUAGE_CODE != language_option.selected ) {
          Constants.setLanguage( language_option.selected );
          screen.showHomeWindow( true );
          Assets.saveConfiguration();
        }
        else {
          screen.showHomeWindow( false );
        }
        
        Assets.playSound("button", false);
      }
    });

    addActor( exit_btn );
  }
}
