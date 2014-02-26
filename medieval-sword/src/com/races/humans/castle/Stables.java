package com.races.humans.castle;

import com.game.Constants;
import com.modules.castle.CastleBuilding;
import com.modules.castle.CastleBuildingLevel;
import com.modules.castle.TopCastle;

public class Stables extends CastleBuilding {

	public Stables() {
		buildings_level.add( getFirstLevel() );

		position_number = 6;
		levels = 1;
	}

	private CastleBuildingLevel getFirstLevel() {
		CastleBuildingLevel level_1 = new CastleBuildingLevel() {

			public void passDay( TopCastle castle ) {}

			public void up( TopCastle castle ) {
				castle.enableUnit( Constants.KNIGHT );
			}

			public void passWeek(TopCastle castle) {
				int number = 2 * Math.round( castle.getProductionPercent() );
				castle.addNumberUnits( Constants.KNIGHT, number );
			}
		};

		level_1.setGoldPrice(12000);
		level_1.setWoodPrice(20);
		level_1.setStonePrice(10);

		level_1.setName("en", "Stables");
		level_1.setName("es", "Establos");
		level_1.setBuildTexture("humans-stables");
		level_1.setDescription("en", "The Stables allows to recruit Knights.");
		level_1.setDescription("es", "Los establos permiten reclutar caballeros.");

		return level_1;
	}

}
