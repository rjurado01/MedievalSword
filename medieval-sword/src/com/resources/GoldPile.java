package com.resources;

import com.game.Player;
import com.modules.map.terrain.ResourcePile;
import com.utils.Vector2i;

public class GoldPile extends ResourcePile {
	
	public GoldPile( Vector2i square_number, int amount ) {
		this.square_position_number = square_number;
		this.amount = amount;
		this.texture_name = "goldPile";
		this.size = new Vector2i( 44, 44 );
		this.position_correction = new Vector2i(-3,0);
		
		createActor();
	}
	
	public void use( Player player ) {
		player.addGold( amount );
	}

}
