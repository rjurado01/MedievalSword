package com.terrain;

import com.modules.map.MapConstants;
import com.modules.map.terrain.MapObject;
import com.utils.Vector2i;

public class Rock2 extends MapObject {

	public Rock2() {
		type = MapConstants.ROCK_2;
		texture_name = "rock2";
		size = new Vector2i( 96, 68 );
		position_correction = new Vector2i(0,15);
	}
}
