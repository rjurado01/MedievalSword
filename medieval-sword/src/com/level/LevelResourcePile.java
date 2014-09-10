package com.level;

import com.utils.Vector2i;

/**
 * This class is used to save/load resource pile information in JSON format
 */
public class LevelResourcePile {

  public Vector2i square_number;
  public int type;
  public int amount;

  public LevelResourcePile( Vector2i square_number, int type, int amount ) {
    this.square_number = square_number;
    this.amount = amount;
    this.type = type;
  }

}
