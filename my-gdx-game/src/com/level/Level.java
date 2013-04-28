package com.level;

import java.util.List;


public class Level {
	public int SQUARES_X;
	public int SQUARES_Y;
	
	//public int terrain[][];
	public LevelTerrain terrain;
	
	public int level;
	
	public List<LevelStructure> structures;
	public List<LevelPlayer> players;
	public List<String> units;
	public List<LevelCreaturesGroup> map_creatures;
}
