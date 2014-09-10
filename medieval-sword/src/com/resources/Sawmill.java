package com.resources;

import com.game.Player;
import com.modules.map.terrain.ResourceStructure;
import com.utils.Vector2i;

public class Sawmill extends ResourceStructure {

  public Sawmill( Vector2i square_number, Player owner  ) {
    super( square_number );

    this.owner = owner;
    this.texture_name = "sawmill";
    this.size = new Vector2i( 240, 196 );
    this.vision = 5;
    this.square_use_number = new Vector2i( 1, -1 );
    this.position_correction = new Vector2i(24,0);
    this.flag_position = new Vector2i( 163, 155 );

    createActor();

    if( owner != null )
      showFlag( owner.color );
  }

  public void turnAction() {
    if( owner != null )
      owner.wood += 1;
  }
}
