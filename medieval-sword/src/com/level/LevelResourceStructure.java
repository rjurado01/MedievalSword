package com.level;

import com.utils.Vector2i;

public class LevelResourceStructure {
	
	public Vector2i square_number;
	public int owner;
	public int type;
	
	public LevelResourceStructure( Vector2i square_number, int owner, int type ) {
		this.square_number = square_number;
		this.owner = owner;
		this.type = type;
	}
}
