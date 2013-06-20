package com.modules.map;

import java.util.HashMap;
import java.util.Map;

import com.terrain.Tree1;
import com.terrain.Tree2;

public class MapObjectsTypes {
	Map<Integer, MapObject> objects;
	
	MapObjectsTypes() {
		objects = new HashMap<Integer, MapObject>();
		
		objects.put( MapConstants.TREE_1, new Tree1() );
		objects.put( MapConstants.TREE_2, new Tree2() );
	}
	
	public MapObject getMapObject( int type ) {
		return objects.get( type );
	}
}
