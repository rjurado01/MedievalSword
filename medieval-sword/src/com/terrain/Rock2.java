package com.terrain;

import com.modules.map.MapConstants;
import com.modules.map.MapObject;
import com.utils.Vector2i;

public class Rock2 extends MapObject {

	public Rock2() {
		type = MapConstants.ROCK_2;
		texture_name = "rock2";
		size = new Vector2i( 40, 60 );
		position_correction = new Vector2i(0,0);
	}
}
