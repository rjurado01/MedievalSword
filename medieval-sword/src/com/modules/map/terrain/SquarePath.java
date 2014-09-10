package com.modules.map.terrain;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.modules.map.MapConstants;

/**
 * Save information about each square of hero path and draw it.
 */
public class SquarePath extends Image {
  static int HEIGHT = 38;
  static int WIDTH = 38;

  public SquarePath( Vector2 square_position, boolean attainable, boolean selected, boolean free ) {
    if( selected )
      setSelected( square_position, attainable, free );
    else
      setNormal( square_position, attainable );
  }

  /**
   * Draw last square of path with special texture
   * @param square_position the position where texture will be drawn
   * @param attainable if hero can reach it
   */
  private void setSelected( Vector2 square_position, boolean attainable, boolean free ) {
    this.x = square_position.x;
    this.y = square_position.y;
    this.width = MapConstants.SQUARE_TERRAIN_W;
    this.height = MapConstants.SQUARE_TERRAIN_H;

    if( attainable ) {
      if( free )
        setRegion( Assets.getFrame( "squareBlueSelected", 2 ) );
      else
        setRegion( Assets.getFrame( "squareBlueSelected", 1 ) );
    }
    else {
      if( free )
        setRegion( Assets.getFrame( "squareRedSelected", 2 ) );
      else
        setRegion( Assets.getFrame( "squareRedSelected", 1 ) );
    }
  }

  /**
   * Draw square path element
   * @param square_position the position where texture will be drawn
   * @param attainable if hero can reach it
   */
  private void setNormal( Vector2 square_position, boolean attainable ) {
    this.x = square_position.x + ( MapConstants.SQUARE_TERRAIN_W - WIDTH ) / 2;
    this.y = square_position.y + ( MapConstants.SQUARE_TERRAIN_H - HEIGHT ) / 2;
    this.width = WIDTH;
    this.height = HEIGHT;

    if( attainable )
      setRegion( Assets.getTextureRegion( "squareBlue" ) );
    else
      setRegion( Assets.getTextureRegion( "squareRed" ) );
  }
}
