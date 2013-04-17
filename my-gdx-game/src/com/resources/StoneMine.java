package com.resources;

import com.modules.map.ResourceStructure;
import com.mygdxgame.Player;
import com.utils.Vector2i;

public class StoneMine extends ResourceStructure{
	
	/* SIZE */
	public static final float HEIGHT = 35;
	public static final float WIDTH = 35;


	public StoneMine( Vector2i square_number, int owner  ) {
		super( square_number );

		this.owner = owner;
		this.texture = "settings";
		this.size = new Vector2i( 70, 70 );
	}

	public void turnAction(Player player) {
		player.stone += 1;
	}
}
