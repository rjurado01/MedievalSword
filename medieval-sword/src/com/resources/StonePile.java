package com.resources;

import com.game.Assets;
import com.game.Player;
import com.modules.map.MapConstants;
import com.modules.map.terrain.ResourcePile;
import com.utils.Vector2i;

public class StonePile extends ResourcePile {

  public StonePile( Vector2i square_number, int amount ) {
    this.square_position_number = square_number;
    this.amount = amount;
    this.texture_name = "pile-stone";
    this.size = new Vector2i( 94, 65 );
    this.position_correction = new Vector2i(2,10);
    this.type = MapConstants.STONE_MINE;

    createActor();
  }

  public void use( Player player ) {
    player.addStone( amount );
    Assets.playSound("stone_pile", false);
  }
}
