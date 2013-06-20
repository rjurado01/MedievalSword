package com.modules.map;

import com.utils.Vector2i;

public class MapObject {
	
	public int type;
	public String texture_name;
	public Vector2i size;
	public Vector2i position_correction;
	
	public MapObject() {
		this.position_correction = new Vector2i(0, 0);
	}
}
