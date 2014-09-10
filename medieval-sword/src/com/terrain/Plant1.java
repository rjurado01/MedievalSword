package com.terrain;

import com.modules.map.MapConstants;
import com.modules.map.terrain.MapObject;
import com.utils.Vector2i;

public class Plant1 extends MapObject {

  public Plant1() {
    type = MapConstants.PLANT_1;
    texture_name = "plant1";
    size = new Vector2i( 96, 96 );
    position_correction = new Vector2i(0,0);
  }
}
