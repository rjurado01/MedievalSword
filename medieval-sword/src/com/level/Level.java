package com.level;

import java.util.ArrayList;
import java.util.List;


public class Level {
	public int SQUARES_X;
	public int SQUARES_Y;

	//public int terrain[][];
	public LevelTerrain terrain;

	public int level;

	public List<LevelResourceStructure> resource_structures;
	public List<LevelResourcePile> resource_piles;
	public List<LevelPlayer> players;
	public List<String> units;
	public List<LevelCreaturesGroup> map_creatures;
	public List<LevelCastle> map_castles;
	//public List<LevelMapObject> map_objects;

	public int map_objects[][];

	public Level() {
		resource_piles = new ArrayList<LevelResourcePile>();
	}
}
