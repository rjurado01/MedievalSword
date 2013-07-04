package com.modules.map.terrain;

import com.game.Assets;
import com.game.Player;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.utils.Vector2i;

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
	
	public void use( Player player ) {
		owner = player;
		image.setRegion( Assets.getTextureRegion( texture_name + player.color ) );
	}
}
