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

			public void passDay( TopCastle castle ) {
				castle.getOwner().addGold( 1000 );
			}

			public void up(TopCastle castle) {
				castle.enableUnit( Constants.MAGUS );
			}

			public void passWeek(TopCastle castle) {
				// TODO Auto-generated method stub
			}
		};

		level_1.setGoldPrice(12000);
		level_1.setWoodPrice(10);
		level_1.setStonePrice(10);

		level_1.setName("en", "Monastery");
		level_1.setName("es", "Monasterio");
		level_1.setBuildTexture("humans-church");
		level_1.setDescription("en", "The Monastery allows recruit Wizards.");
		level_1.setDescription("es", "El monasterio permite reclutar \nhechiceros.");

		return level_1;
	}

}
