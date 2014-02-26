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

			public void up(TopCastle castle) {
				castle.enableUnit( Constants.ARCHER );
			}

			public void passWeek(TopCastle castle) {
				int number = Math.round( 12 * castle.getProductionPercent() );
				castle.addNumberUnits( Constants.ARCHER, number );
			}

			public void passDay( TopCastle castle ) {}
		};

		level_1.setGoldPrice(2500);
		level_1.setWoodPrice(10);
		level_1.setStonePrice(5);

		level_1.setName( "en", "Archers Tower");
		level_1.setName( "es", "Torre arqueros");
		level_1.setBuildTexture("humans-archery");
		level_1.setDescription( "en", "The Archers Tower allows recruit Archers.");
		level_1.setDescription( "es", "La torre de arqueros permite reclutar \nArqueros.");

		return level_1;
	}
}
