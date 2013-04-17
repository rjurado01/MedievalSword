package com.level;

import java.util.ArrayList;

public class LevelPlayer {
	public int gold;
	public int wood;
	public int stone;
	
	public int color; 
	public ArrayList<LevelHero> heroes;
	
	public LevelPlayer() {
		gold = 0;
		wood = 0;
		stone = 0;
		heroes = new ArrayList<LevelHero>();
	}
	
	public void addHero( LevelHero hero ) {
		heroes.add( hero );
	}
}
