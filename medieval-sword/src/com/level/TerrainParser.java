package com.level;

import com.modules.map.terrain.Terrain;
import com.utils.Vector2i;

/**
 * This class allow us load terrain from LevelTerrain
 */
public class TerrainParser {

  public static int getSquareType( int id ) {
    return id;
  }

  public static String getSquareTextureName( int id ) {
    switch ( id ) {
      case 1: return "mapWater2";
      case 2: return "mapRoad";
      default: return "block-grass";
    }
  }

  public Terrain getTerrain( LevelTerrain level_terrain ) {
    Terrain terrain = new Terrain(
      new Vector2i( level_terrain.SQUARES_X, level_terrain.SQUARES_Y ));

    for( int i = 0; i < level_terrain.SQUARES_Y; i++)
      for( int j = 0; j < level_terrain.SQUARES_X; j++)
        terrain.addSquareTerrain(
          new Vector2i( j, i ),
          level_terrain.terrain[i][j],
          getSquareTextureName( level_terrain.terrain[i][j] ) );

    return terrain;
  }
}
