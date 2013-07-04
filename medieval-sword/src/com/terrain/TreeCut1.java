package com.terrain;

import com.modules.map.MapConstants;
import com.modules.map.terrain.MapObject;
import com.utils.Vector2i;

public class TreeCut1 extends MapObject {

	public TreeCut1() {
		type = MapConstants.TREE_2;
		texture_name = "cutTree";
		size = new Vector2i( 30, 20 );
		position_correction = new Vector2i(10,5);
	}
}
