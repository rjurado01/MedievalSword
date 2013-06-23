package com.terrain;

import com.modules.map.MapConstants;
import com.modules.map.MapObject;
import com.utils.Vector2i;

public class TreeDry2 extends MapObject {

	public TreeDry2() {
		type = MapConstants.TREE_2;
		texture_name = "tree1";
		size = new Vector2i( 50, 80 );
		position_correction = new Vector2i(0,0);
	}
}
