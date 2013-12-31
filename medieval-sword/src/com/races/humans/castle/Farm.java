package com.races.humans.castle;

import com.game.Constants;
import com.modules.castle.CastleBuilding;
import com.modules.castle.CastleBuildingLevel;
import com.modules.castle.TopCastle;
import com.modules.map.MapConstants;

public class Farm extends CastleBuilding {

	public Farm() {
		buildings_level.add( getFirstLevel() );

		position_number = 3;
		levels = 1;
	}

	private CastleBuildingLevel getFirstLevel() {
		CastleBuildingLevel level_1 = new CastleBuildingLevel() {

			public void passWeek( TopCastle castle ) {
				castle.addNumberUnits( MapConstants.LEVEL_1, 12 );
			}

			public void up( TopCastle castle ) {
				castle.enableUnit( Constants.VILLAGER );
			}

			public void passDay(TopCastle castle) {
				// TODO Auto-generated method stub
			}
		};

		level_1.setGoldPrice(1000);
		level_1.setWoodPrice(5);
		level_1.setStonePrice(0);

		level_1.setName("Farm");
		level_1.setBuildTexture("humans-farm");
		level_1.setDescription("The Farm allows you to recruit\nVillagers.");

		return level_1;
	}

}
