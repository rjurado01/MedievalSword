package com.level;

import com.utils.Vector2i;

public class LevelStructure {
	
	public Vector2i square_number;
	public int owner;
	public int type;
	
	public LevelStructure( Vector2i square_number, int owner, int type ) {
		this.square_number = square_number;
		this.owner = owner;
		this.type = type;
	}
}
