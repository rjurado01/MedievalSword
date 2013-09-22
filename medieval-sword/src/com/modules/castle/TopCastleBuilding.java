package com.modules.castle;

import com.game.Constants;

/**
 * It is a building from a castle and save its status.
 */
public class TopCastleBuilding {

	int level;

	CastleBuilding building;
	TopCastle castle;

	public TopCastleBuilding( TopCastle castle, CastleBuilding building ) {
		this.castle = castle;
		this.building = building;
		level = 0;
	}

	public void setLevel( int level ) {
		this.level = level;
	}

	public void up() {
		System.out.println("Construllo si puedo");
	}

	public CastleBuildingLevel getBuildingLevel() {
		return building.getBuildingLevel(level - 1);
	}

	public CastleBuildingLevel getNextBuildingLevel() {
		if( complete() )
			return building.getBuildingLevel(level - 1);	// last level
		else
			return building.getBuildingLevel(level);
	}

	public int getPositionNumber() {
		return building.getPositionNumber();
	}

	public boolean complete() {
		return building.levels == level;
	}

	public boolean canBuildLevel() {
		CastleBuildingLevel building_level = getNextBuildingLevel();

		if( building_level.getGoldPrice() <= castle.getOwner().gold &&
			building_level.getWoodPrice() <= castle.getOwner().wood &&
			building_level.getStonePrice() <= castle.getOwner().stone )
			return true;
		else
			return false;
	}

	public boolean availableLevel() {
		return level >= 0 && !complete() && !castle.isBuilt();
	}

	public void build() {
		CastleBuildingLevel building_level = getNextBuildingLevel();

		castle.getOwner().addGold( - building_level.getGoldPrice() );
		castle.getOwner().addWood( - building_level.getWoodPrice() );
		castle.getOwner().addStone( - building_level.getStonePrice() );

		castle.setBuilt();
		building.up( castle, level );
		level++;
	}

	public void initialize( int level ) {
		this.level = level;

		for( int i = 0; i < level; i++ )
			building.up( castle, i );
	}

	public void passDay( int day ) {
		if( level > 0 ) {
			getBuildingLevel().passDay( castle );

			if( day == Constants.WEEK_DAYS - 1 )
				getBuildingLevel().passWeek( castle );
		}
	}
}
