package com.resources;

import com.game.Player;
import com.modules.map.terrain.ResourceStructure;
import com.utils.Vector2i;

public class StoneMine extends ResourceStructure{

  public StoneMine( Vector2i square_number, Player owner  ) {
    super( square_number );

    this.owner = owner;
    this.texture_name = "stone-mine";
    this.size = new Vector2i( 264, 212 );
    this.vision = 5;
    this.square_use_number = new Vector2i( 1, -1 );
    this.position_correction = new Vector2i( 0, 0 );
    this.flag_position = new Vector2i( 120, 190 );

    createActor();

    if( owner != null )
      showFlag( owner.color );
  }

  public void turnAction() {
    if( owner != null )
      owner.stone += 1;
  }
}
