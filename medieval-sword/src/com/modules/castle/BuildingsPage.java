package com.modules.castle;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.utils.Vector2i;

/**
 * The page of buildings allows the player to improve the castle
 */
public class BuildingsPage extends Group {

	final int N_BUILDINGS = 8;

	// Initial position for buildings squares
	final int BUILDINGS_X = 185;
	final int ROW_1_Y = 230;
	final int ROW_2_Y = 60;

	// Buildings squares properties
	static final int BUILDING_W = 140;
	static final int BUILDING_H = 110;
	static final int BUILDING_NAME_H = 32;
	final int SPACE = BUILDING_W + 10;

	BuildingPicture[] buildings;


	public BuildingsPage( List<TopCastleBuilding> buildings ) {
		this.buildings = new BuildingPicture[8];

		for( final TopCastleBuilding castle_building : buildings ) {
			int position_number = castle_building.getPositionNumber();
			Vector2i position = getBuildingPosition( position_number );

			this.buildings[ position_number ] =
					new BuildingPicture( castle_building, position );

			addActor( this.buildings[ position_number ] );
		}
	}

	private Vector2i getBuildingPosition( int position ) {
		if( position < N_BUILDINGS / 2 )
			return new Vector2i( ( (position % 4) * SPACE ) + BUILDINGS_X , ROW_1_Y );
		else
			return new Vector2i( ( (position % 4) * SPACE ) + BUILDINGS_X , ROW_2_Y );
	}

	public void updateBuildings() {
		for( BuildingPicture picture : buildings )
			picture.update();
	}
}
