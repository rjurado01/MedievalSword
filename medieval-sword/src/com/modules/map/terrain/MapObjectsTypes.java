package com.modules.map.terrain;

import java.util.HashMap;
import java.util.Map;

import com.modules.map.MapConstants;
import com.terrain.Mountain1;
import com.terrain.Rock1;
import com.terrain.Rock2;
import com.terrain.Tree1;
import com.terrain.Tree2;
import com.terrain.TreeCut1;
import com.terrain.TreeDry1;
import com.terrain.TreeDry2;

public class MapObjectsTypes {
	Map<Integer, MapObject> objects;
	
	public MapObjectsTypes() {
		objects = new HashMap<Integer, MapObject>();
		
		objects.put( MapConstants.TREE_1, new Tree1() );
		objects.put( MapConstants.TREE_2, new Tree2() );
		objects.put( MapConstants.TREE_DRY_1, new TreeDry1() );
		objects.put( MapConstants.TREE_DRY_2, new TreeDry2() );
		objects.put( MapConstants.TREE_CUT_1, new TreeCut1() );
		objects.put( MapConstants.ROCK_1, new Rock1() );
		objects.put( MapConstants.ROCK_2, new Rock2() );
		objects.put( MapConstants.MOUNTAIN_1, new Mountain1() );
	}
	
	public MapObject getMapObject( int type ) {
		return objects.get( type );
	}
}
