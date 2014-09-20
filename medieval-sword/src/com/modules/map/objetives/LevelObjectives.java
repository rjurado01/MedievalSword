package com.modules.map.objetives;

import java.util.List;

import com.game.Player;
import com.modules.castle.TopCastle;
import com.modules.map.heroes.CreaturesGroup;
import com.utils.LocalizedString;

/**
 * This class let us define the objectives of a level
 */
public abstract class LevelObjectives {

  int level;
  int number_objectives = 2;
  LocalizedString descriptions [];
  boolean completed_objetives [];

  public LevelObjectives( int level, int number ) {
    this.level = level;
    number_objectives = number;
    completed_objetives = new boolean [number_objectives];
    descriptions = new LocalizedString [number_objectives];

    for( int i=0; i < number_objectives; i++ ) {
      completed_objetives[i] = false;
      descriptions[i] = new LocalizedString();
    }

    loadDescriptions();
  }

  protected abstract void loadDescriptions();

  public abstract int checkObjetives( Player player, List <TopCastle> castles,
      List<CreaturesGroup> creatures );

  public LocalizedString getDescription( int n ) {
    return descriptions[n];
  }

  public boolean isCompleted( int n ) {
    return completed_objetives[n];
  }

  public int getNumber() {
    return number_objectives;
  }

  public int getLevel() {
    return level;
  }

  public void setCompleted( int n ) {
    completed_objetives[n] = true;
  }
}
