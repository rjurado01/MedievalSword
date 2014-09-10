package com.level;

import java.util.ArrayList;
import java.util.List;

import com.game.Army;
import com.game.Constants;
import com.game.Player;
import com.game.Stack;
import com.modules.castle.BuildingsPage;
import com.modules.castle.TopCastle;
import com.modules.map.MapActor;
import com.modules.map.heroes.CreaturesGroup;
import com.modules.map.heroes.HeroTop;
import com.modules.map.objetives.LevelObjectives;
import com.modules.map.terrain.ResourcePile;
import com.modules.map.terrain.ResourceStructure;
import com.modules.map.terrain.Terrain;
import com.utils.Vector2i;

public class SaveParser {

  Level level;

  public SaveParser(Terrain terrain, List<Player> players, List<CreaturesGroup> creatures,
      LevelObjectives objectives) {
    level = new Level();
    level.level = objectives.getLevel();
    saveTerrain(terrain);
    saveMapObjects(terrain);
    saveResourceStructures(terrain);
    saveResourcePiles(terrain);
    savePlayers(players);
    saveMapCreatures(creatures);
    saveCastles(terrain);
    saveObjectives(objectives);
  }

  private void saveTerrain(Terrain terrain) {
    LevelTerrain lvl_terrain = new LevelTerrain();

    lvl_terrain.SQUARES_X = terrain.SQUARES_X;
    lvl_terrain.SQUARES_Y = terrain.SQUARES_Y;

    lvl_terrain.terrain = new int[ terrain.SQUARES_Y ][];
    lvl_terrain.explored = new int[ terrain.SQUARES_Y ][];

    for( int i = 0; i < terrain.SQUARES_Y; i++ ) {
      int row = terrain.SQUARES_Y - i - 1;
      lvl_terrain.terrain[row] = new int[ terrain.SQUARES_X ];
      lvl_terrain.explored[row] = new int[ terrain.SQUARES_X ];

      for( int j = 0; j < terrain.SQUARES_X; j++ ) {
        lvl_terrain.terrain[row][j] =
          terrain.getSquareTerrain( new Vector2i(j,i) ).getType();
        
        lvl_terrain.explored[row][j] = terrain.isExplored(j,i);
      }
    }

    level.terrain = lvl_terrain;        
  }
  
  private void saveMapObjects(Terrain terrain) {
    level.map_objects = new int[ terrain.SQUARES_Y ][];

    for( int i = 0; i < terrain.SQUARES_Y; i++ ) {
      level.map_objects[i] = new int[ terrain.SQUARES_X ];

      for( int j = 0; j < terrain.SQUARES_X; j++ )
        level.map_objects[i][j] = 0;
    }

    for( MapActor item : terrain.getObjects() ) {
      Vector2i position = item.getPositionNumber();
      level.map_objects[terrain.SQUARES_Y -position.y - 1][position.x] = item.getObject().type;
    }
  }

  private void saveResourceStructures(Terrain terrain) {
    level.resource_structures = new ArrayList<LevelResourceStructure>();

    for( ResourceStructure structure : terrain.getResourceStructures() ) {    
      level.resource_structures.add( new LevelResourceStructure(
          structure.square_number, structure.getOwnerId(), structure.type) );
    }
  }

  private void saveResourcePiles(Terrain terrain) {
    level.resource_piles = new ArrayList<LevelResourcePile>();

    for( ResourcePile pile : terrain.getResourcePiles() ) {
      level.resource_piles.add( new LevelResourcePile(
          pile.square_position_number, pile.type, pile.amount) );
    }
  }

  private void savePlayers(List<Player> players) {
    level.players = new ArrayList<LevelPlayer>();

    for( Player player : players ) {
      LevelPlayer level_player = new LevelPlayer();
      level_player.gold = player.gold;
      level_player.wood = player.wood;
      level_player.stone = player.stone;
      level_player.id = player.id;
      level_player.color = player.color;
      level_player.human = player.human;
      level_player.heroes = getPlayerLevelHeroes(player);
      
      level.players.add(level_player);
    }
  }

  private ArrayList<LevelHero> getPlayerLevelHeroes(Player player) {
    ArrayList<LevelHero> level_heroes = new ArrayList<LevelHero>();

    for( HeroTop hero : player.getHeroes() ) {
      LevelHero level_hero = new LevelHero();
      level_hero.defense = hero.getDefense();
      level_hero.attack = hero.getAttack();
      level_hero.mobility = hero.getMobility();
      level_hero.square_number = hero.getSquareTerrain().getNumber();
      level_hero.type = hero.getType();
      level_hero.remaining_movement = hero.getActualMobility();
      level_hero.owner = player.id;
      level_hero.cpu = !player.human;
      level_hero.stacks = getArmyStacks( hero.getArmy() );
      
      level_heroes.add( level_hero );
    }

    return level_heroes;
  }

  private ArrayList<LevelStack> getArmyStacks( Army army ) {
    ArrayList<LevelStack> level_stacks = new ArrayList<LevelStack>();

    for( Stack stack : army.getStacks() ) {
      LevelStack level_stack = new LevelStack();
      level_stack.type = stack.getUnit().getId();
      level_stack.number = stack.getNumber();
      level_stacks.add( level_stack );
    }
    
    return level_stacks;
  }

  private void saveMapCreatures(List<CreaturesGroup> creatures) {
    level.map_creatures = new ArrayList<LevelCreaturesGroup>();

    for( CreaturesGroup group : creatures ) {
      LevelCreaturesGroup level_group = new LevelCreaturesGroup();
      level_group.square_number = group.getSquare().getNumber();
      level_group.type = group.getUnitId();
      level_group.stacks = getGroupStacks( group );
      level.map_creatures.add(level_group);
    }
  }

  private List<LevelStack> getGroupStacks( CreaturesGroup group ) {
    List<LevelStack> level_stacks = new ArrayList<LevelStack>();

    for( Stack stack : group.getArmy().getStacks() ) {
      LevelStack level_stack = new LevelStack();
      level_stack.type = stack.getUnit().getId();
      level_stack.number = stack.getNumber();
      level_stacks.add( level_stack );
    }
    
    return level_stacks;
  }

  private void saveCastles(Terrain terrain) {
    level.map_castles = new ArrayList<LevelCastle>();

    for( TopCastle castle : terrain.getCastles() ) {
      LevelCastle level_castle = new LevelCastle(
          castle.getStructure().square_number, castle.getOwnerId(), 1);

      level_castle.stacks = getCastleStacks(castle.getArmy().getStacks());
      
      level_castle.number_units = new int[Constants.N_UNITS];
      level_castle.buildings_levels = new int[BuildingsPage.N_BUILDINGS];
      
      for( int i = 0; i  < Constants.N_UNITS; i++ ) {
        level_castle.number_units[i] = castle.getNumberUnits(i);
      }

      for( int i = 0; i  < BuildingsPage.N_BUILDINGS; i++ ) {
        level_castle.buildings_levels[i] = castle.getBuildingLevel(i);
      }
      
      level.map_castles.add( level_castle );
    }
  }

  private ArrayList<LevelStack> getCastleStacks( ArrayList<Stack> stacks ) {
    ArrayList<LevelStack> level_stacks = new ArrayList<LevelStack>();

    for( Stack stack : stacks ) {
      LevelStack level_stack = new LevelStack();
      level_stack.type = stack.getUnit().getId();
      level_stack.number = stack.getNumber();
      level_stacks.add( level_stack );
    }
    
    return level_stacks;
  }

  private void saveObjectives(LevelObjectives objectives) {
    level.objectives_completed = new int[objectives.getNumber()];
    
    for( int i = 0; i < level.objectives_completed.length; i++ ) {
      if( objectives.isCompleted(i) )
        level.objectives_completed[i] = 1;
      else
        level.objectives_completed[i] = 0;
    }
  }

  public Level getLevel() {
    return level;
  }
}
