package com.races.humans.castle;

import com.game.Constants;
import com.modules.castle.CastleBuilding;
import com.modules.castle.CastleBuildingLevel;
import com.modules.castle.TopCastle;

public class Archery extends CastleBuilding {

	public Archery() {
		buildings_level.add( getFirstLevel() );
		position_number = 5;
		levels = 1;
	}

	private CastleBuildingLevel getFirstLevel() {
		CastleBuildingLevel level_1 = new CastleBuildingLevel() {
			public void passDay( TopCastle castle ) {}

			public void up(TopCastle castle) {
				castle.enableUnit( Constants.ARCHER );
			}

			public void passWeek(TopCastle castle) {
				// TODO Auto-generated method stub
			}
		};

		level_1.setGoldPrice(2500);
		level_1.setWoodPrice(10);
		level_1.setStonePrice(0);

		level_1.setName("Archery");
		level_1.setBuildTexture("humans-archery");
		level_1.setDescription("The Archery allows you to recruit\nArchers.");

		return level_1;
	}
}
