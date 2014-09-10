package com.races.humans.castle;

import com.modules.castle.CastleBuilding;
import com.modules.castle.CastleBuildingLevel;
import com.modules.castle.TopCastle;

public class Fort extends CastleBuilding {

  public Fort() {
    buildings_level.add( getFirstLevel() );

    position_number = 2;
    levels = 1;
  }

  private CastleBuildingLevel getFirstLevel() {
    CastleBuildingLevel level_1 = new CastleBuildingLevel() {

      public void up( TopCastle castle ) {
        castle.setProductionPercent( 1.5f );
      }

      public void passDay( TopCastle castle ) {}

      public void passWeek( TopCastle castle ) {}
    };

    level_1.setGoldPrice(5000);
    level_1.setWoodPrice(0);
    level_1.setStonePrice(10);

    level_1.setName("en", "Fort");
    level_1.setName("es", "Fuerte");
    level_1.setBuildTexture("humans-fort");
    level_1.setDescription("en", "The Fort increases creature \nproduction by 50%.");
    level_1.setDescription("es", "El fuerte incrementa la produción de \ncriaturas en un 50%.");

    return level_1;
  }
}
