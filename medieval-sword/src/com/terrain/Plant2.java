package com.terrain;

import com.modules.map.MapConstants;
import com.modules.map.terrain.MapObject;
import com.utils.Vector2i;

public class Plant2 extends MapObject {

  public Plant2() {
    type = MapConstants.PLANT_2;
    texture_name = "block-grass";
    size = new Vector2i( 96, 144 );
    position_correction = new Vector2i(0,0);
  }
}
