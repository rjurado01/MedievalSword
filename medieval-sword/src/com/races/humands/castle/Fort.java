package com.races.humands.castle;

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

			public void up( TopCastle castle ) {}

			public void passDay( TopCastle castle ) {}
			public void passWeek(TopCastle castle) {}
		};

		level_1.setGoldPrice(5000);
		level_1.setWoodPrice(0);
		level_1.setStonePrice(10);

		level_1.setName("Fort");
		level_1.setBuildTexture("castle");
		level_1.setDescription("The Fort increases creature\nproduction by 50%.");

		return level_1;
	}

}
