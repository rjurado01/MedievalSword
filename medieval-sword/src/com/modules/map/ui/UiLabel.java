package com.modules.map.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;
import com.game.Constants;

/**
 * Box that includes a label.
 */
public class UiLabel extends Group {
  Image background;
  Label text;

  public UiLabel( int x, int y ) {

    background = new Image(Assets.getTextureRegion( "rect" ) );
    background.height = 36;
    background.width = 144;

    text = new Label( "0", Assets.font2 );
    text.y = 5;

    addActor( background );
    addActor( text );

    this.x = x;
    this.y = y;
  }

  public void updateText( String new_text ) {
    text.setText( new_text );
    text.x = ( background.width - new_text.length() * Constants.FONT1_WIDTH ) / 2;
  }
}
