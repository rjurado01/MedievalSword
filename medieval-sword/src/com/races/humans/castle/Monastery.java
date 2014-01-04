package com.races.humans.castle;

import com.game.Constants;
import com.modules.castle.CastleBuilding;
import com.modules.castle.CastleBuildingLevel;
import com.modules.castle.TopCastle;

public class Monastery extends CastleBuilding {

	public Monastery() {
		buildings_level.add( getFirstLevel() );

		position_number = 7;
		levels = 1;
	}

	private CastleBuildingLevel getFirstLevel() {
		CastleBuildingLevel level_1 = new CastleBuildingLevel() {

			public void up(TopCastle castle) {
				castle.enableUnit( Constants.WIZARD );
			}

			public void passDay( TopCastle castle ) {}

			public void passWeek( TopCastle castle ) {
				int number = 4 * Math.round( castle.getProductionPercent() );
				castle.addNumberUnits( Constants.WIZARD, number );
			}
		};

		level_1.setGoldPrice(8000);
		level_1.setWoodPrice(6);
		level_1.setStonePrice(15);

		level_1.setName("en", "Monastery");
		level_1.setName("es", "Monasterio");
		level_1.setBuildTexture("humans-church");
		level_1.setDescription("en", "The Monastery allows recruit Wizards.");
		level_1.setDescription("es", "El monasterio permite reclutar \nhechiceros.");

		return level_1;
	}

}
