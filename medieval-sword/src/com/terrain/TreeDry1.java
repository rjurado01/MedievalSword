package com.terrain;

import com.modules.map.MapConstants;
import com.modules.map.MapObject;
import com.utils.Vector2i;

public class TreeDry1 extends MapObject {

	public TreeDry1() {
		type = MapConstants.TREE_2;
		texture_name = "tree0";
		size = new Vector2i( 50, 80 );
		position_correction = new Vector2i(0,0);
	}
}
