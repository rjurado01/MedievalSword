package com.level;

import java.util.ArrayList;

public class LevelPlayer {

	public int gold;
	public int wood;
	public int stone;

	public int id;
	public int color; 
	public ArrayList<LevelHero> heroes;
	public boolean humand;

	public LevelPlayer() {
		gold = 0;
		wood = 0;
		stone = 0;
		heroes = new ArrayList<LevelHero>();
		humand = true;
	}

	public void addHero( LevelHero hero ) {
		heroes.add( hero );
	}
}
