package com.level;

import com.utils.Vector2i;

/**
 * This class is used to save/load map object information in JSON format
 */
public class LevelMapObject {

  public Vector2i square_number;
  public int type;

  public LevelMapObject( Vector2i square_number, int type ) {
    this.square_number = square_number;
    this.type = type;
  }
}
