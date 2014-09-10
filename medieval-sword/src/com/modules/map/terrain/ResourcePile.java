package com.modules.map.terrain;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Player;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.utils.Vector2i;

/**
 * Representation of each resource pile in the map.
 * When hero get it, add resources to hero player.
 */
public abstract class ResourcePile {
  public int amount;
  public int type;

  public Vector2i square_position_number;
  public String texture_name;
  public Vector2i size;
  public Vector2i position_correction;

  Image image;

  public ResourcePile() {
    position_correction = new Vector2i(0,0);
  }

  protected void createActor() {
    image = new Image( Assets.getTextureRegion( texture_name ) );

    image.x = MapConstants.SQUARE_TERRAIN_W * square_position_number.x
      + position_correction.x;
    image.y = MapConstants.SQUARE_TERRAIN_H * square_position_number.y
      + position_correction.y;

    image.width = size.x;
    image.height = size.y;

    image.setClickListener( new ClickListener() {
      public void click(Actor actor, float x, float y) { clicked(); };
    });
  }

  public Image getActor() {
    return image;
  }

  protected void clicked() {
    MapController.addEvent( MapConstants.RESOURCE_PILE, this );
  };

  public abstract void use( Player player );

}
