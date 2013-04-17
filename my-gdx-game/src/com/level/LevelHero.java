package com.level;

import java.util.ArrayList;
import com.utils.Vector2i;

public class LevelHero {
	/* Attributes */
	public int defense;
	public int attack;
	public int mobility;
	
	public Vector2i square_number;
	public int owner;
	public int remaining_movement;
	public int type;
	public boolean cpu;
	
	ArrayList<LevelStack> stacks;
	
	public LevelHero() {
		defense = 0;
		attack = 0;
		mobility = 0;
		
		stacks = new ArrayList<LevelStack>();
	}
	
	public void addStack( LevelStack stack ) {
		stacks.add( stack );
	}
}
