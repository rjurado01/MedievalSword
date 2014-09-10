package com.races.humans.castle;

import com.modules.castle.CastleBuilding;
import com.modules.castle.CastleBuildingLevel;
import com.modules.castle.TopCastle;


public class TownHall extends CastleBuilding {

  public TownHall() {
    buildings_level.add( getFirstLevel() );
    buildings_level.add( getSecondLevel() );

    position_number = 0;
    levels = 2;
  }

  private CastleBuildingLevel getFirstLevel() {
    CastleBuildingLevel level_1 = new CastleBuildingLevel() {

      public void passDay( TopCastle castle ) {
        castle.getOwner().addGold( 500 );
      }

      public void up( TopCastle castle ) {}

      public void passWeek(TopCastle castle) {
        // nothing to do
      }
    };

    level_1.setGoldPrice(2000);
    level_1.setWoodPrice(0);
    level_1.setStonePrice(0);

    level_1.setName("en", "Town Hall");
    level_1.setName("es", "Alcaldia");
    level_1.setBuildTexture("humans-townhall");
    level_1.setDescription("en", "The Town produces 1000 Gold per day.");
    level_1.setDescription("es", "La alcaldía proporciona 1000 de oro al día.");

    return level_1;
  }

  private CastleBuildingLevel getSecondLevel() {
    CastleBuildingLevel level_1 = new CastleBuildingLevel() {

      public void up(TopCastle castle) {}

      public void passDay( TopCastle castle ) {
        System.out.println("Level2");
        castle.getOwner().addGold( 2000 );
      }

      public void passWeek(TopCastle castle) {
        // nothing to do
      }
    };

    level_1.setGoldPrice(6000);
    level_1.setWoodPrice(0);
    level_1.setStonePrice(0);

    level_1.setName("en", "Capitole");
    level_1.setName("es", "Capitolio");
    level_1.setBuildTexture("humans-capitole");
    level_1.setDescription("en", "The Capitole produces 2000 Gold per day.");
    level_1.setDescription("es", "El Capitolio produce 2000 de oro al dia.");

    return level_1;
  }

}
