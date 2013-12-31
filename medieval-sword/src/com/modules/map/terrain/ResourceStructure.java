package com.modules.map.terrain;

import com.game.Assets;
import com.game.Player;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.utils.Vector2i;

/**
 * Structure of map that produces resources each turn.
 * This class should be extended by each specific resource structure type.
 */
public abstract class ResourceStructure extends Structure {

	protected Player owner;
	protected int color;
	protected int vision;

	public ResourceStructure( Vector2i square_number ) {
		super( 1, square_number );
		squares_size = new Vector2i( 2, 2 );
	}

	public abstract void turnAction();

	protected void clicked() {
		MapController.addEvent( MapConstants.RESOURCE_STRUCTURE, this );
	}

	/**
	 * Called when player captured it
	 * @param player the player who captured it
	 */
	public void use( Player player ) {
		owner = player;
		flag.setRegion( Assets.getTextureRegion( "flag" + player.color ) );
	}
}
