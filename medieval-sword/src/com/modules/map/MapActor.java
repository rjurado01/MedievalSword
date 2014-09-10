package com.modules.map;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.modules.map.terrain.MapObject;
import com.utils.Vector2i;

/**
 *	Used in the Parser class for load Map objects ( trees, rocks... ).
 */
public class MapActor {
  MapObject object;
  Image image;

  Vector2i square_position_number;

  public MapActor( Vector2i square_number, MapObject object ) {
    this.square_position_number = square_number;
    this.object = object;

    createActor();
  }

  protected void createActor() {
    image = new Image( Assets.getTextureRegion( object.texture_name ) );

    image.x = MapConstants.SQUARE_TERRAIN_W * square_position_number.x
      + object.position_correction.x;
    image.y = MapConstants.SQUARE_TERRAIN_H * square_position_number.y
      + object.position_correction.y;

    image.width = object.size.x;
    image.height = object.size.y;
  }

  public Image getActor() {
    return image;
  }

  public Vector2i getPositionNumber() {
    return square_position_number;
  }

  public MapObject getObject() {
    return object;
  }
}
