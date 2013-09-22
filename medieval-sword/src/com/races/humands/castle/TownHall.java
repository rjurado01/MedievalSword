package com.races.humands.castle;

import com.modules.castle.CastleBuilding;
import com.modules.castle.CastleBuildingLevel;
import com.modules.castle.TopCastle;


public class TownHall extends CastleBuilding {

	public TownHall() {
		buildings_level.add( getFirstLevel() );
		buildings_level.add( getSecondLevel() );

		position_number = 0;
		levels = 2;
	}

	private CastleBuildingLevel getFirstLevel() {
		CastleBuildingLevel level_1 = new CastleBuildingLevel() {

			public void passDay( TopCastle castle ) {
				System.out.println("Level1");
				castle.getOwner().addGold( 500 );
			}

			public void up(TopCastle castle) {}

			public void passWeek(TopCastle castle) {
				// TODO Auto-generated method stub
			}
		};

		level_1.setGoldPrice(2000);
		level_1.setWoodPrice(0);
		level_1.setStonePrice(0);

		level_1.setName("Town Hall");
		level_1.setBuildTexture("townhall");
		level_1.setDescription("The Town produces 1000 Gold per day.");

		return level_1;
	}

	private CastleBuildingLevel getSecondLevel() {
		CastleBuildingLevel level_1 = new CastleBuildingLevel() {

			public void up(TopCastle castle) {}

			public void passDay( TopCastle castle ) {
				System.out.println("Level2");
				castle.getOwner().addGold( 2000 );
			}

			public void passWeek(TopCastle castle) {}
		};

		level_1.setGoldPrice(6000);
		level_1.setWoodPrice(0);
		level_1.setStonePrice(0);

		level_1.setName("Capitole");
		level_1.setBuildTexture("townhall");
		level_1.setDescription("Town Hall second level.");

		return level_1;
	}

}
