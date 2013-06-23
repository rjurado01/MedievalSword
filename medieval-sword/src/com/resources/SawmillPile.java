package com.resources;

import com.game.Player;
import com.modules.map.ResourcePile;
import com.utils.Vector2i;

public class SawmillPile extends ResourcePile {
	
	public SawmillPile( Vector2i square_number, int amount ) {
		this.square_position_number = square_number;
		this.amount = amount;
		this.texture_name = "woodPile";
		this.size = new Vector2i( 30, 30 );
		this.position_correction = new Vector2i(5,5);
		
		createActor();
	}
	
	public void use( Player player ) {
		player.addWood( amount );
	}
	
}
