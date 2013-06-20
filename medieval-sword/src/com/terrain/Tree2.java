package com.terrain;

import com.modules.map.MapConstants;
import com.modules.map.MapObject;
import com.utils.Vector2i;

public class Tree2 extends MapObject {

	public Tree2() {
		type = MapConstants.TREE_2;
		texture_name = "tree2";
		size = new Vector2i( 44, 64 );
		position_correction = new Vector2i(0,0);
	}
}
