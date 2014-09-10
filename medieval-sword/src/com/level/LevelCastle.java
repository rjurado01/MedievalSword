package com.level;

import java.util.ArrayList;

import com.utils.Vector2i;

/**
 * This class is used to save/load castle information in JSON format
 */
public class LevelCastle {

  public Vector2i square_number;
  public int owner;
  public int type;

  ArrayList<LevelStack> stacks;

  int number_units[];
  int buildings_levels[];

  public LevelCastle( Vector2i square_number, int owner, int type ) {
    this.square_number = square_number;
    this.owner = owner;
    this.type = type;

    stacks = new ArrayList<LevelStack>();
    number_units = new int[5];
    buildings_levels = new int[8];

    for( int i = 0; i < 5; i++ )
      number_units[i] = 0;
  }

  public void addStack( LevelStack stack ) {
    stacks.add( stack );
  }

  public void setNumberUnitsType( int position, int number ) {
    number_units[position] = number;
  }
}
