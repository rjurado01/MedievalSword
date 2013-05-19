package com.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.modules.map.CreaturesGroup;
import com.modules.map.HeroTop;
import com.modules.map.SquareTerrain;
import com.modules.map.Terrain;
import com.mygdxgame.Army;
import com.mygdxgame.Player;
import com.mygdxgame.Stack;
import com.mygdxgame.Unit;
import com.mygdxgame.Constants;
import com.races.humands.heroes.HumandHero1;
import com.utils.Vector2i;

/**
 * This class allow us convert Level-Objects in Game-Objects
 */
public class Parser {
	
	public Parser() {
	}
	
	public HeroTop getHeroTop( Player player, Terrain terrain, Map<Integer, Unit> units, LevelHero level_hero, int color ) {
		HeroTop hero = null;
		
		if( level_hero.type == 1 )
			hero = new HeroTop( player, new HumandHero1(), color );
		
		if( hero != null ) {
			hero.setSquareTerrain( terrain.getSquareTerrain( level_hero.square_number ) );
			terrain.getSquareTerrain( level_hero.square_number ).setTopHero( hero );
			
			if( level_hero.attack > 0 )
				hero.setAttack( level_hero.attack );
			if( level_hero.defense > 0 )
				hero.setDefense( level_hero.defense );
			if( level_hero.mobility > 0 )
				hero.setMobility( level_hero.mobility );
			
			if( level_hero.stacks.size() > 0 ) {
				for( LevelStack level_stack : level_hero.stacks )
					hero.addStack( new Stack( units.get( level_stack.type ), level_stack.number, color ) );
			}
		}
		
		return hero;
	}
	
	public Player getPlayer( Terrain terrain, Map<Integer, Unit> units, LevelPlayer level_player ) {
		Player player = new Player( level_player.color );
		
		player.gold = level_player.gold;
		player.wood = level_player.wood;
		player.stone = level_player.stone;
		
		for( LevelHero level_hero : level_player.heroes )
			player.addHero( getHeroTop( player, terrain, units, level_hero, level_player.color ) );
		
		return player;
	}
	
	public Terrain getTerrain( LevelTerrain level_terrain ) {
		Terrain terrain = new Terrain(
				new Vector2i( level_terrain.SQUARES_X, level_terrain.SQUARES_Y ));
		
		int inverse = level_terrain.SQUARES_Y - 1; // in libgdx, [0,0] is [SQUARES_Y - 1][0]
		
		for( int i = 0; i < level_terrain.SQUARES_Y; i++)
			for( int j = 0; j < level_terrain.SQUARES_X; j++)
				terrain.addSquareTerrain( 
						new Vector2i( j, i ),
						level_terrain.terrain[inverse - i][j], 
						getSquareTextureName( level_terrain.terrain[inverse - i][j] ) );
		
		return terrain;
	}
	
	public String getSquareTextureName( int id ) {
		switch ( id ) {
			case 1: return "mapWater2";
			case 2: return "mapRoad";
			default: return "mapGrass";
		}
	}
	
	public List<CreaturesGroup> getCreaturesGroups( Terrain terrain, Map<Integer, Unit> units, Level level ) {
		List<CreaturesGroup> groups = new ArrayList<CreaturesGroup>();
		
		if( level.map_creatures != null )
			for( LevelCreaturesGroup level_group : level.map_creatures )
				groups.add( getCreatureGroup( terrain, units, level_group ) );
		
		return groups;
	}
	
	private CreaturesGroup getCreatureGroup( Terrain terrain, Map<Integer, Unit> units, LevelCreaturesGroup level_group ) {
		Army army = new Army();
		SquareTerrain square = terrain.getSquareTerrain( level_group.square_number );
		Unit unit = units.get( level_group.type );
		
		for( LevelStack level_stack : level_group.stacks )
			army.addStack( new Stack( units.get( level_stack.type ), level_stack.number, 0 ) );
			
		return new CreaturesGroup( army, square, unit  );
	}
}
