package com.modules.map.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Constants;
import com.game.Player;
import com.modules.map.MapConstants;
import com.modules.map.terrain.SquareTerrain;
import com.modules.map.terrain.Terrain;
import com.utils.Vector2i;

/**
 * Little map that are drown it the top of HUD.
 * Show information about all the map.
 */
public class MiniMap extends Group {

  public static final int FOG = 0;
  public static final int GRASS = 1;
  public static final int WHATER = 2;
  public static final int ROAD = 3;
  public static final int BLUE = 4;
  public static final int RED = 5;
  public static final int GREY = 6;
  public static final int WHITE = 7;

  final float WIDTH = Constants.SIZE_W / 7.16f;
  final float HEIGHT = Constants.SIZE_H / 4.64f;

  Terrain terrain;
  Image mini_map [][];

  float pixel_width;
  float pixel_height;

  Vector2 position;

  public MiniMap( Stage stage, Terrain terrain ) {
    this.terrain = terrain;

    pixel_width = WIDTH / terrain.SQUARES_X;
    pixel_height = HEIGHT / terrain.SQUARES_Y;
    position = new Vector2(
        ( MapConstants.HUD_WIDTH - WIDTH ) / 2,
        MapConstants.HUD_HEIGHT / 1.3241f );

    createMiniMap( terrain );
  }

  private void createMiniMap( Terrain terrain ) {
    mini_map = new Image[ terrain.getHeight() ][];

    for( int i = 0; i < terrain.SQUARES_Y; i++ ) {
      mini_map[i] = new Image[ terrain.SQUARES_X ];

      for( int j = 0; j < terrain.SQUARES_X; j++ ) {
        mini_map[i][j] = createItemImage(
            terrain.getSquareTerrain( new Vector2i( j, i ) ) );
      }
    }
  }

  private Image createItemImage( SquareTerrain square ) {
    Image image = new Image( Assets.minimap_textures.get( square.getMiniMapColor() ) );
    image.width = pixel_width;
    image.height = pixel_height;
    image.x = position.x + ( pixel_width * square.getNumber().x );
    image.y = position.y + ( pixel_height * square.getNumber().y );

    addActor( image );

    return image;
  }

  public void updatePosition( Vector2i number ) {
    SquareTerrain square = terrain.getSquareTerrain( number );

    mini_map[number.y][number.x].setRegion(
        new TextureRegion( Assets.minimap_textures.get( square.getMiniMapColor() ) ) );
  }

  public void drawStructure( Vector2i position, Vector2i size, Player owner ) {
    for( int y = position.y; y < position.y + size.y; y++ )
      for( int x = position.x; x < position.x + size.x; x++ )
        mini_map[y][x].setRegion( getOwnerTexture( owner) );
  }

  private TextureRegion getOwnerTexture( Player owner ) {
    if( owner == null )
      return new TextureRegion( Assets.minimap_textures.get( WHITE ) );
    else if( owner.color == Constants.BLUE )
      return new TextureRegion( Assets.minimap_textures.get( BLUE ) );
    else if( owner.color == Constants.RED )
      return new TextureRegion( Assets.minimap_textures.get( RED ) );
    else
      return null;
  }

  public void updateRange( Vector2i position, Vector2i size ) {
    for( int y = position.y; y < position.y + size.y; y++ )
      for( int x = position.x; x < position.x + size.x; x++ )
        updatePosition( new Vector2i( x, y ) );
  }
}
