package com.races.humans.castle;

import com.game.Constants;
import com.modules.castle.CastleBuilding;
import com.modules.castle.CastleBuildingLevel;
import com.modules.castle.TopCastle;

public class Farm extends CastleBuilding {

  public Farm() {
    buildings_level.add( getFirstLevel() );

    position_number = 3;
    levels = 1;
  }

  private CastleBuildingLevel getFirstLevel() {
    CastleBuildingLevel level_1 = new CastleBuildingLevel() {

      public void passWeek( TopCastle castle ) {
        int number = Math.round( 16 * castle.getProductionPercent() );
        castle.addNumberUnits( Constants.VILLAGER, number );
      }

      public void passDay(TopCastle castle) {
        // nothing to do
      }

      public void up( TopCastle castle ) {
        castle.enableUnit( Constants.VILLAGER );
      }
    };

    level_1.setGoldPrice(2000);
    level_1.setWoodPrice(5);
    level_1.setStonePrice(0);

    level_1.setName("en", "Farm");
    level_1.setName("es", "Granja");
    level_1.setBuildTexture("humans-farm");
    level_1.setDescription("en", "The Farm allows recruit Villagers.");
    level_1.setDescription("es", "La granja permite reclutar Aldeanos.");

    return level_1;
  }

}
