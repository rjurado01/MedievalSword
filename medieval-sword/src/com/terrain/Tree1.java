package com.terrain;

import com.modules.map.MapConstants;
import com.modules.map.terrain.MapObject;
import com.utils.Vector2i;

public class Tree1 extends MapObject {

	public Tree1() {
		type = MapConstants.TREE_1;
		texture_name = "tree2";
		size = new Vector2i( 54, 80 );
		position_correction = new Vector2i(-5,5);
	}
}
