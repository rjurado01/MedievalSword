package com.resources;

import com.game.Player;
import com.modules.map.ResourceStructure;
import com.utils.Vector2i;

/**
 * Archer Unit ( distance unit ) 
 */
public class GoldMine extends ResourceStructure {
	
	/* SIZE */
	public static final float HEIGHT = 35;
	public static final float WIDTH = 35;

	public GoldMine( Vector2i square_number, Player owner ) {
		super( square_number );
		
		this.owner = owner;
		this.texture_name = "goldMine";
		this.size = new Vector2i( 100, 100 );
		this.square_use_number = new Vector2i( 1, -1 );
		//this.position_correction = new Vector2i( -14, 0 );

		createActor();
	}

	public void turnAction() {
		if( owner != null )
			owner.gold += 1;
	}
}
