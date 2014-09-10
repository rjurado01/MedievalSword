package com.level;

import java.util.ArrayList;
import java.util.List;

import com.game.Army;
import com.game.Assets;
import com.game.Player;
import com.game.Stack;
import com.game.Unit;
import com.modules.castle.Castle;
import com.modules.castle.TopCastle;
import com.modules.map.MapActor;
import com.modules.map.MapConstants;
import com.modules.map.heroes.CreaturesGroup;
import com.modules.map.heroes.HeroTop;
import com.modules.map.terrain.MapObjectsTypes;
import com.modules.map.terrain.ResourcePile;
import com.modules.map.terrain.ResourceStructure;
import com.modules.map.terrain.SquareTerrain;
import com.modules.map.terrain.Terrain;
import com.races.humans.castle.HumansCastle;
import com.races.humans.heroes.HumandHero1;
import com.resources.GoldMine;
import com.resources.GoldPile;
import com.resources.Sawmill;
import com.resources.SawmillPile;
import com.resources.StoneMine;
import com.resources.StonePile;
import com.utils.Vector2i;

/**
 * This class allow us convert Level-Objects in Game-Objects
 */
public class LoadParser {

  public LoadParser() {
  }

  public HeroTop getHeroTop( Player player, Terrain terrain,
      LevelHero level_hero, int color ) {

    HeroTop hero = null;

    if( level_hero.type == 1 )
      hero = new HeroTop( player, new HumandHero1(), color );

    if( hero != null ) {
      hero.setSquareTerrain( terrain.getSquareTerrain( level_hero.square_number ) );
      hero.setActualMobility( level_hero.remaining_movement );
      terrain.getSquareTerrain( level_hero.square_number ).setTopHero( hero );

      if( level_hero.attack > 0 )
        hero.setAttack( level_hero.attack );
      if( level_hero.defense > 0 )
        hero.setDefense( level_hero.defense );
      if( level_hero.mobility > 0 )
        hero.setMobility( level_hero.mobility );

      if( level_hero.stacks.size() > 0 ) {
        for( LevelStack level_stack : level_hero.stacks )
          hero.addStack(
              new Stack( Assets.getUnit( level_stack.type ),
                level_stack.number, color ) );
      }
    }

    return hero;
  }

  public Player getPlayer( Terrain terrain, LevelPlayer level_player ) {

    Player player = new Player( level_player.color );

    player.id = level_player.id;
    player.gold = level_player.gold;
    player.wood = level_player.wood;
    player.stone = level_player.stone;
    player.human = level_player.human;

    for( LevelHero level_hero : level_player.heroes )
      player.addHero( getHeroTop(
            player, terrain, level_hero, level_player.color ) );

    return player;
  }

  public Terrain getTerrain( LevelTerrain level_terrain ) {
    Terrain terrain = new Terrain(
        new Vector2i( level_terrain.SQUARES_X, level_terrain.SQUARES_Y ));

    int inverse = level_terrain.SQUARES_Y - 1; // in libgdx, [0,0] is [SQUARES_Y - 1][0]

    for( int i = 0; i < level_terrain.SQUARES_Y; i++)
      for( int j = 0; j < level_terrain.SQUARES_X; j++) {
        terrain.addSquareTerrain(
            new Vector2i( j, i ),
            level_terrain.terrain[inverse - i][j],
            getSquareTextureName( level_terrain.terrain[inverse - i][j] ) );

        if( level_terrain.explored[inverse - i][j] == Terrain.EXPLORED )
          terrain.exploreSquare( j, i );
      }

    return terrain;
  }

  public String getSquareTextureName( int id ) {
    switch ( id ) {
      case 1: return "block-water";
      case 2: return "block-stone";
      default: return "block-grass";
    }
  }

  public List<CreaturesGroup> getCreaturesGroups( Terrain terrain, Level level ) {
    List<CreaturesGroup> groups = new ArrayList<CreaturesGroup>();

    if( level.map_creatures != null )
      for( LevelCreaturesGroup level_group : level.map_creatures )
        groups.add( getCreatureGroup( terrain, level_group ) );

    return groups;
  }

  private CreaturesGroup getCreatureGroup(
      Terrain terrain, LevelCreaturesGroup level_group ) {

    Army army = new Army();
    SquareTerrain square = terrain.getSquareTerrain( level_group.square_number );
    Unit unit = Assets.getUnit( level_group.type );

    for( LevelStack level_stack : level_group.stacks )
      army.addStack( new Stack(
            Assets.getUnit( level_stack.type ), level_stack.number, 0 ) );

    return new CreaturesGroup( army, square, unit  );
      }

  public List<ResourceStructure> getResourceStructures( List<Player> players, Level level ) {
    List<ResourceStructure> structures = new ArrayList<ResourceStructure>();

    for( LevelResourceStructure level_structure : level.resource_structures )
      structures.add( getResourceStructure( players, level_structure ) );

    return structures;
  }

  private ResourceStructure getResourceStructure(
    List<Player> players, LevelResourceStructure level_structure ) {

    ResourceStructure structure = null;

    switch( level_structure.type ) {
      case MapConstants.GOLD_MINE:
        structure = new GoldMine(
            level_structure.square_number,
            getPlayerFromId( players, level_structure.owner ) );
        break;
      case MapConstants.SAWMILL:
        structure = new Sawmill(
            level_structure.square_number,
            getPlayerFromId( players, level_structure.owner ) );
        break;
      case MapConstants.STONE_MINE:
        structure = new StoneMine(
            level_structure.square_number,
            getPlayerFromId( players, level_structure.owner ));
        break;
    }
    
    return structure;
  }

  private Player getPlayerFromId( List<Player> players, int id ) {
    for( Player player : players ) {
      if( player.id == id )
        return player;
    }

    return null;
  }

  public List<ResourcePile> getResourcePiles( Level level ) {
    List<ResourcePile> piles = new ArrayList<ResourcePile>();

    for( LevelResourcePile level_pile : level.resource_piles )
      piles.add( getResourcePile( level_pile ) );

    return piles;
  }

  private ResourcePile getResourcePile( LevelResourcePile level_pile ) {

    ResourcePile pile = null;

    switch( level_pile.type ) {
      case MapConstants.GOLD_MINE:
        pile = new GoldPile( level_pile.square_number, level_pile.amount );
        break;
      case MapConstants.SAWMILL:
        pile = new SawmillPile( level_pile.square_number, level_pile.amount );
        break;
      case MapConstants.STONE_MINE:
        pile = new StonePile( level_pile.square_number, level_pile.amount );
        break;
    }

    return pile;
  }

  public List<MapActor> getMapObjects( Level level, MapObjectsTypes object_types ) {
    List<MapActor> objects = new ArrayList<MapActor>();

    int inverse = level.terrain.SQUARES_Y - 1; // in libgdx, [0,0] is [SQUARES_Y - 1][0]

    for( int i = 0; i < level.terrain.SQUARES_Y; i++)
      for( int j = 0; j < level.terrain.SQUARES_X; j++) {
        if( level.map_objects[i][j] > 0 ) {
          objects.add( new MapActor( new Vector2i(j,inverse - i),
                object_types.getMapObject( level.map_objects[i][j] ) ) );
        }
      }

    return objects;
  }

  public List<TopCastle> getMapCastles( List<Player> players, Level level ) {
    List<TopCastle> castles = new ArrayList<TopCastle>();

    for( LevelCastle lvl_castle : level.map_castles ) {
      TopCastle castle = new TopCastle(
          getNewCastle( lvl_castle.type ),
          lvl_castle.square_number,
          getPlayerFromId( players, lvl_castle.owner ) );

      for( LevelStack lvl_stack : lvl_castle.stacks )
        castle.addUnitToArmy(
            Assets.getUnit(lvl_stack.type), lvl_stack.number );

      for( int i = 0; i < lvl_castle.number_units.length; i++  )
        castle.setNumberUnits( i, lvl_castle.number_units[i] );

      for( int i = 0; i < 8; i++ )
        castle.initializeBuilding( i, lvl_castle.buildings_levels[i] );

      castles.add( castle );
    }

    return castles;
  }

  private Castle getNewCastle( int type ) {
    return new HumansCastle();
  }

}
