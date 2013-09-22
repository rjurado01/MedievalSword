package com.modules.castle;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.utils.Vector2i;

/**
 * The page of buildings allows the player to improve the castle
 */
public class BuildingsPage extends Group {

	// Initial position for buildings squares
	final int BX_INIT = 20;
	final int BY_INIT = 105;

	// Space between two buildings squares
	final int BX_SIZE = 80;
	final int BY_SIZE = 70;

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
		return new Vector2i(
				( ( position % 4 ) * BX_SIZE ) + BX_INIT ,
				( BY_INIT - ( position / 4 ) * BY_SIZE ) );
	}

	public void updateBuildings() {
		for( BuildingPicture picture : buildings )
			picture.update();
	}
}
