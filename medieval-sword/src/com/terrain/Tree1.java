package com.terrain;

import com.modules.map.MapConstants;
import com.modules.map.terrain.MapObject;
import com.utils.Vector2i;

public class Tree1 extends MapObject {

  public Tree1() {
    type = MapConstants.TREE_1;
    texture_name = "tree1";
    size = new Vector2i( 130, 190 );
    position_correction = new Vector2i(-15,15);
  }
}
