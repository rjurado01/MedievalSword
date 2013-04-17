package com.mygdxgame;

import com.badlogic.gdx.utils.Array;
import com.modules.map.HeroTop;

/**
 * This class represent each player and all this features.
 * Contain information about all his resources, units, experience, level...
 */
public class Player {

	public int gold;
	public int wood;
	public int stone;
	
	public int color;
	private Array<HeroTop> heroes;
	private HeroTop hero_selected;
	
	public Player( int color ) {
		gold = 0;
		wood = 0;		
		heroes = new Array<HeroTop>();
		this.color = color;
	}
	
	public void addHero( HeroTop hero ) {
		heroes.add( hero );
		hero_selected = heroes.get( heroes.size - 1 );
	}
	
	public void removeHero( HeroTop hero ) {
		heroes.removeValue( hero, true );
	}
	
	public HeroTop getHeroSelected() {
		return hero_selected;
	}
	
	public Array<HeroTop> getHeroes() {
		return heroes;
	}
}
