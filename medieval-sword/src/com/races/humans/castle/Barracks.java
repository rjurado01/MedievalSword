package com.races.humans.castle;

import com.game.Constants;
import com.modules.castle.CastleBuilding;
import com.modules.castle.CastleBuildingLevel;
import com.modules.castle.TopCastle;

public class Barracks extends CastleBuilding {

  public Barracks() {
    buildings_level.add( getFirstLevel() );

    position_number = 4;
    levels = 1;
  }

  private CastleBuildingLevel getFirstLevel() {
    CastleBuildingLevel level_1 = new CastleBuildingLevel() {

      public void passDay( TopCastle castle ) {}

      public void up( TopCastle castle ) {
        castle.enableUnit( Constants.CRUSADER );
      }

      public void passWeek(TopCastle castle) {
        int number = 8 * Math.round( castle.getProductionPercent() );
        castle.addNumberUnits( Constants.CRUSADER, number );
      }
    };

    level_1.setGoldPrice(5000);
    level_1.setWoodPrice(5);
    level_1.setStonePrice(5);

    level_1.setName( "en", "Barracks" );
    level_1.setName( "es", "Barracas" );
    level_1.setBuildTexture( "humans-barracks" );
    level_1.setDescription( "en", "The Barracks allow recruit Crusaders." );
    level_1.setDescription( "es", "Las Barracas permiten reclutar crusados." );

    return level_1;
  }

}
