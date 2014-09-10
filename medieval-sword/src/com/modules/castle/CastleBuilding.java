package com.modules.castle;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a building from a castle.
 * It may have one or more levels.
 */
public class CastleBuilding {

  static final int LEVEL_1 = 0;
  static final int LEVEL_2 = 1;
  static final int LEVEL_3 = 2;

  Castle castle;

  // number that identify the position of building in the buildings page
  protected int position_number;

  // number of levels
  protected int levels;

  protected List<CastleBuildingLevel> buildings_level;

  public CastleBuilding() {
    buildings_level = new ArrayList<CastleBuildingLevel>();
  }

  public CastleBuildingLevel getBuildingLevel( int level ) {
    if( level == -1 )
      return buildings_level.get( 0 );
    else if( level < levels )
      return buildings_level.get( level );
    else
      return buildings_level.get( buildings_level.size() - 1 );
  }

  public int getPositionNumber() {
    return position_number;
  }

  public void up( TopCastle castle, int level ) {
    buildings_level.get( level ).up( castle );
  }
}
