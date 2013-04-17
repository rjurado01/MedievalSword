package com.modules.map;

import com.mygdxgame.Player;
import com.utils.Vector2i;

public abstract class ResourceStructure extends Structure {
	
	public ResourceStructure( Vector2i square_number ) {
		super( square_number );
	}
	
	public abstract void turnAction( Player player );
}
