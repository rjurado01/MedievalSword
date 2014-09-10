package com.modules.map.terrain;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Constants;
import com.modules.map.MapConstants;
import com.utils.Vector2i;

/**
 *	Basic class that save the properties of map structure.
 */
public abstract class Structure {

  protected int vision;

  public Vector2i square_number;
  public int type;
  public int color;

  // position from square_position_number for use structure
  protected Vector2i square_use_number;

  public String texture_name;
  public Vector2i size;
  public Vector2i squares_size;  // number of squares occupied
  public Vector2i position_correction;
  public Vector2i flag_position;

  Group actor;
  Image flag;
  Image image;

  public Structure( int type, Vector2i square_number ) {
    this.type = type;
    this.square_number = square_number;
    this.position_correction = new Vector2i(0, 0);
    this.color = Constants.GREY;
  }

  protected void createActor() {
    actor = new Group();
    actor.x = MapConstants.SQUARE_TERRAIN_W * square_number.x
      + position_correction.x;
    actor.y = MapConstants.SQUARE_TERRAIN_H * square_number.y
      + position_correction.y;

    image = new Image( Assets.getTextureRegion( texture_name ) );
    image.width = size.x;
    image.height = size.y;

    image.setClickListener( new ClickListener() {
      public void click(Actor actor, float x, float y) { clicked(); };
    });

    flag = new Image();
    flag.width = 50;
    flag.height = 60;
    flag.x = flag_position.x;
    flag.y = flag_position.y;

    actor.addActor( flag );
    actor.addActor( image );
  }

  protected abstract void clicked();

  public Actor getActor() {
    return actor;
  }

  public void showFlag( int color ) {
    flag.setRegion( Assets.getTextureRegion( "flag" + color ) );
    flag.visible = true;
    this.color = color;
  }

  public void removeFlag() {
    flag.visible = false;
  }

  public Vector2i getUseSquareNumber() {
    if( square_number != null && square_use_number != null )
      return new Vector2i(
          square_number.x + square_use_number.x,
          square_number.y + square_use_number.y );
    else
      return null;
  }
}
