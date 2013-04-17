package com.resources;

import com.modules.map.ResourceStructure;
import com.mygdxgame.Player;
import com.utils.Vector2i;

/**
 * Archer Unit ( distance unit ) 
 */
public class GoldMine extends ResourceStructure {
	
	/* SIZE */
	public static final float HEIGHT = 35;
	public static final float WIDTH = 35;


	public GoldMine( Vector2i square_number, int owner ) {
		super( square_number );
		
		this.owner = owner;
		this.texture = "mineGold";
		this.size = new Vector2i( 53, 40 );
		this.position_correction = new Vector2i( -14, 0 );
	}

	public void turnAction(Player player) {
		player.gold += 1;
	}
}
