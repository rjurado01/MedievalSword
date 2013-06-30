package com.resources;

import com.game.Player;
import com.modules.map.ResourceStructure;
import com.utils.Vector2i;

public class Sawmill extends ResourceStructure {
	
	/* SIZE */
	public static final float HEIGHT = 70;
	public static final float WIDTH = 70;


	public Sawmill( Vector2i square_number, Player owner  ) {
		super( square_number );
		
		this.owner = owner;
		this.texture_name = "sawmill";
		this.size = new Vector2i( 100, 100 );
		this.vision = 5;
		this.square_use_number = new Vector2i( 1, -1 );
		
		createActor();
	}

	public void turnAction() {
		if( owner != null )
			owner.wood += 1;
	}
}
