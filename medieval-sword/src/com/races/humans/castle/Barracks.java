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
				// TODO Auto-generated method stub
			}
		};

		level_1.setGoldPrice(5000);
		level_1.setWoodPrice(5);
		level_1.setStonePrice(5);

		level_1.setName("Barracks");
		level_1.setBuildTexture("humans-barracks");
		level_1.setDescription("The Barracks allow you to recruit\nCrusaders.");

		return level_1;
	}

}
