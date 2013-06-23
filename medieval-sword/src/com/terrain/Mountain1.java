package com.terrain;

import com.modules.map.MapConstants;
import com.modules.map.MapObject;
import com.utils.Vector2i;

public class Mountain1 extends MapObject {

	public Mountain1() {
		type = MapConstants.MOUNTAIN_1;
		texture_name = "mountain";
		size = new Vector2i( 80, 84 );
		position_correction = new Vector2i(0,-10);
	}
}
