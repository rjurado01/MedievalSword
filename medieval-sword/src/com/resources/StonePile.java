package com.resources;

import com.game.Player;
import com.modules.map.terrain.ResourcePile;
import com.utils.Vector2i;

public class StonePile extends ResourcePile {

	public StonePile( Vector2i square_number, int amount ) {
		this.square_position_number = square_number;
		this.amount = amount;
		this.texture_name = "stonePile";
		this.size = new Vector2i( 40, 40 );
		
		createActor();
	}
	
	public void use( Player player ) {
		player.addStone( amount );
	}
	
}
