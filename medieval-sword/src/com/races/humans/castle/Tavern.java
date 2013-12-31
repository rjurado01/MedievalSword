package com.races.humans.castle;

import com.modules.castle.CastleBuilding;
import com.modules.castle.CastleBuildingLevel;
import com.modules.castle.TopCastle;

public class Tavern extends CastleBuilding {

	public Tavern() {
		buildings_level.add( getFirstLevel() );

		position_number = 1;
		levels = 1;
	}

	private CastleBuildingLevel getFirstLevel() {
		CastleBuildingLevel level_1 = new CastleBuildingLevel() {

			public void passDay( TopCastle castle ) {}

			public void up( TopCastle castle ) {}

			public void passWeek( TopCastle castle ) {
				// TODO Auto-generated method stub
			}
		};

		level_1.setGoldPrice(1000);
		level_1.setWoodPrice(0);
		level_1.setStonePrice(0);

		level_1.setName("en", "Tavern");
		level_1.setName("es", "Taberna");
		level_1.setBuildTexture("humans-tavern");
		level_1.setDescription("en", "The Tavern allows to recruit heroes.");
		level_1.setDescription("es", "La taberna permite reclutar nuevos \nheroes.");

		return level_1;
	}

}
