package com.level;

import java.util.ArrayList;
import java.util.List;

/**
 * This class saves the information of Level
 */
public class Level {
  public LevelTerrain terrain;
  public int level;

  public List<LevelResourceStructure> resource_structures;
  public List<LevelResourcePile> resource_piles;
  public List<LevelPlayer> players;
  public List<LevelCreaturesGroup> map_creatures;
  public List<LevelCastle> map_castles;

  public int map_objects[][];
  public int objectives_completed[];

  public Level() {
    resource_piles = new ArrayList<LevelResourcePile>();
  }
}
