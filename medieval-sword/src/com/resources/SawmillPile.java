package com.resources;

import com.game.Assets;
import com.game.Player;
import com.modules.map.MapConstants;
import com.modules.map.terrain.ResourcePile;
import com.utils.Vector2i;

public class SawmillPile extends ResourcePile {

  public SawmillPile( Vector2i square_number, int amount ) {
    this.square_position_number = square_number;
    this.amount = amount;
    this.texture_name = "pile-wood";
    this.size = new Vector2i( 72, 68 );
    this.position_correction = new Vector2i(12,15);
    this.type = MapConstants.SAWMILL;

    createActor();
  }

  public void use( Player player ) {
    player.addWood( amount );
    Assets.playSound("wood_pile", false);
  }
}
