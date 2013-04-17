package com.level;

import com.modules.map.HeroTop;
import com.modules.map.Terrain;
import com.mygdxgame.Player;
import com.races.humands.heroes.HumandHero1;

/**
 * This class allow us convert Level-Objects in Game-Objects
 */
public class Parser {
	
	Terrain terrain;
	
	public Parser( Terrain terrain ) {
		this.terrain = terrain;
	}
	
	public HeroTop getHeroTop( LevelHero level_hero, int color ) {
		HeroTop hero = null;
		
		if( level_hero.type == 1 )
			hero = new HeroTop( new HumandHero1(), color );
		
		if( hero != null ) {
			hero.setSquareTerrain( terrain.getSquareTerrain( level_hero.square_number ) );
			
			if( level_hero.attack > 0 )
				hero.setAttack( level_hero.attack );
			if( level_hero.defense > 0 )
				hero.setDefense( level_hero.defense );
			if( level_hero.mobility > 0 )
				hero.setMobility( level_hero.mobility );
		}
		
		return hero;
	}
	
	public Player getPlayer( LevelPlayer level_player ) {
		Player player = new Player( level_player.color );
		
		player.gold = level_player.gold;
		player.wood = level_player.wood;
		player.stone = level_player.stone;
		
		for( LevelHero level_hero : level_player.heroes )
			player.addHero( getHeroTop( level_hero, level_player.color ) );
		
		return player;
	}
}
