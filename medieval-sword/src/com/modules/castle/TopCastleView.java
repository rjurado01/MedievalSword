package com.modules.castle;

import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.modules.map.terrain.Structure;
import com.utils.Vector2i;

/**
 * View of castle (contains position, size and texture)
 */
public class TopCastleView extends Structure {

	TopCastle top_castle;

	public TopCastleView( TopCastle top_castle, Vector2i square_number ) {
		super(2, square_number );

		this.top_castle = top_castle;
		this.squares_size = new Vector2i( 5, 3 );
		this.texture_name = top_castle.castle.texture_name;
		this.size = top_castle.castle.size;
		this.vision = top_castle.castle.vision;
		this.position_correction = top_castle.castle.position_correction;
		this.flag_position = top_castle.castle.flag_position;
		this.square_use_number = top_castle.castle.square_use_number;

		createActor();
	}

	protected void clicked() {
		MapController.addEvent( MapConstants.CASTLE, top_castle );
	}

}
