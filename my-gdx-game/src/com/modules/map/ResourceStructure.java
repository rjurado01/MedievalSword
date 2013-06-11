package com.modules.map;

import com.mygdxgame.Assets;
import com.mygdxgame.Player;
import com.utils.Vector2i;

public abstract class ResourceStructure extends Structure {
	
	protected Player owner;
	protected int color;
	
	public ResourceStructure( Vector2i square_number ) {
		super( 1, square_number );
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
