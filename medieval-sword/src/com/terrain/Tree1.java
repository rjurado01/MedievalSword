package com.terrain;

import com.modules.map.MapConstants;
import com.modules.map.MapObject;
import com.utils.Vector2i;

public class Tree1 extends MapObject {

	public Tree1() {
		type = MapConstants.TREE_1;
		texture_name = "number";
		size = new Vector2i( 44, 64 );
		position_correction = new Vector2i(0,0);
	}
}
