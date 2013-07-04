package com.terrain;

import com.modules.map.MapConstants;
import com.modules.map.terrain.MapObject;
import com.utils.Vector2i;

public class Rock1 extends MapObject {

	public Rock1() {
		type = MapConstants.ROCK_1;
		texture_name = "rock1";
		size = new Vector2i( 45, 40 );
		position_correction = new Vector2i(0,0);
	}
}
