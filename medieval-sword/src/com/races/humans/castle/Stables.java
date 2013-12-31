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
				// TODO Auto-generated method stub
			}
		};

		level_1.setGoldPrice(10000);
		level_1.setWoodPrice(10);
		level_1.setStonePrice(5);

		level_1.setName("Stables");
		level_1.setBuildTexture("humans-stables");
		level_1.setDescription("The Stables allow you to recruit\nKnights.");

		return level_1;
	}

}
