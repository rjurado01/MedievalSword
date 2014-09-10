package com.modules.map.objetives;

import java.util.List;

import com.game.Constants;
import com.game.Player;
import com.modules.castle.TopCastle;

/**
 * Objectives for level 1
 */
public class LevelObjectives1 extends LevelObjectives {

  public LevelObjectives1() {
    super(1,2);
  }

  protected void loadDescriptions() {
    descriptions[0].addString( "es", "Captura un castillo" );
    descriptions[0].addString( "en", "Capture a castle" );

    descriptions[1].addString( "es", "Construye una alcaldia" );
    descriptions[1].addString( "en", "Build a Town Hall" );
  }

  /**
   * Check Level 1 objectives
   * objective 1: player should capture a castle
   * objective 2: player should build a Town Hall
   *
   * @param player
   * @param castles
   *
   * @return number of objective completed or code (Win, Nothing, Lost)
   */
  public int checkObjetives( Player player, List <TopCastle> castles ) {
    if( completed_objetives[0] == false ) {
      for( int i=0; i < castles.size(); i++ )
        if( castles.get(i).getOwner() == player ) {
          completed_objetives[0] = true;
          return 0;
        }
    } else if( completed_objetives[1] == false ){
      for( int i=0; i < castles.size(); i++ )
        if( castles.get(i).isBuilt( 0, 1 ) ) {
          completed_objetives[1] = true;
          return Constants.OBJ_WIN;
        }
    }

    return Constants.OBJ_NOTHING;
  }
}
