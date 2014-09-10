package com.level;

import java.util.ArrayList;

/**
 * This class is used to save/load player information in JSON format
 */
public class LevelPlayer {

  public int gold;
  public int wood;
  public int stone;

  public int id;
  public int color;
  public ArrayList<LevelHero> heroes;
  public boolean human;

  public LevelPlayer() {
    gold = 0;
    wood = 0;
    stone = 0;
    heroes = new ArrayList<LevelHero>();
    human = false;
  }

  public void addHero( LevelHero hero ) {
    heroes.add( hero );
  }
}
