package com.modules.map;

import com.utils.Vector2i;

public class Structure {
	public Vector2i square_number;
	public int owner;
	public String texture;
	public Vector2i size;
	public Vector2i position_correction;
	
	public Structure( Vector2i square ) {
		this.square_number = square;
		this.position_correction = new Vector2i(0, 0);
	}
}
