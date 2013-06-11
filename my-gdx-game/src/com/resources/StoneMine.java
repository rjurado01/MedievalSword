package com.resources;

import com.modules.map.ResourceStructure;
import com.mygdxgame.Player;
import com.utils.Vector2i;

public class StoneMine extends ResourceStructure{
	
	/* SIZE */
	public static final float HEIGHT = 35;
	public static final float WIDTH = 35;


	public StoneMine( Vector2i square_number, Player owner  ) {
		super( square_number );

		this.owner = owner;
		this.texture_name = "stoneMine";
		this.size = new Vector2i( 100, 100 );
		this.square_use_number = new Vector2i( 1, -1 );
		
		createActor();
	}

	public void turnAction() {
		if( owner != null )
			owner.stone += 1;
	}
}
