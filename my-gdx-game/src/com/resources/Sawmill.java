package com.resources;

import com.modules.map.ResourceStructure;
import com.mygdxgame.Player;
import com.utils.Vector2i;

public class Sawmill extends ResourceStructure {
	
	/* SIZE */
	public static final float HEIGHT = 35;
	public static final float WIDTH = 35;


	public Sawmill( Vector2i square_number, int owner  ) {
		super( square_number );
		
		this.owner = owner;
		this.texture = "preferences";
		this.size = new Vector2i( 100, 100 );
	}

	public void turnAction(Player player) {
		player.wood += 1;
	}
}
