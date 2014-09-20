package com.modules.map.objetives;

import java.util.List;

import com.game.Constants;
import com.game.Player;
import com.modules.castle.TopCastle;
import com.modules.map.heroes.CreaturesGroup;

public class LevelObjectives1 extends LevelObjectives {

  public LevelObjectives1() {
    super(1,1);
  }

  protected void loadDescriptions() {
    descriptions[0].addString( "es", "Destrulle todos los\nenemigos" );
    descriptions[0].addString( "en", "Destroy all enemies" );
  }

  /**
   * Check Level 1 objectives
   * objective 1: player should destroy all enemies
   *
   * @param player
   * @param castles
   *
   * @return number of objective completed or code (Win, Nothing, Lost)
   */
  public int checkObjetives( Player player, List <TopCastle> castles,
      List<CreaturesGroup> creatures ) {
    if( completed_objetives[0] == false && creatures.size() == 0 ){
      completed_objetives[0] = true;
      return Constants.OBJ_WIN;
    }

    return Constants.OBJ_NOTHING;
  }
}
