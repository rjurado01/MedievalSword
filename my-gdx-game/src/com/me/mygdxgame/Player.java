package com.me.mygdxgame;

import com.badlogic.gdx.utils.Array;

/**
 * This class represent each player and all this features.
 * Contain information about all his resources, units, experience, level...
 */
public class Player {

	int gold;
	int wood;
	
	private Array<Hero> heroes;
	private Hero hero_selected;
	
	public Player() {
		gold = 0;
		wood = 0;		
		heroes = new Array<Hero>();
	}
	
	public void addHero( Hero hero ) {
		heroes.add( hero );
		hero_selected = heroes.get( heroes.size - 1 );
	}
	
	public void removeHero( Hero hero ) {
		heroes.removeValue( hero, true );
	}
	
	public Hero getHeroSelected() {
		return hero_selected;
	}
}
