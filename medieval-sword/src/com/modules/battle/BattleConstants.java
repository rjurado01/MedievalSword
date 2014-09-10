package com.modules.battle;

import com.game.Constants;

public class BattleConstants {

  static final int NS_X = 12;
  static final int NS_Y = 6;

  static final float MARGIN_W = 36;
  static final float MARGIN_H = 84;

  public static final int UNIT_W = 80;
  public static final int UNIT_H = 80;

  static final float SQUARE_SIZE_W = ( Constants.SIZE_W - MARGIN_W * 2 ) / NS_X;
  static final float SQUARE_SIZE_H = ( Constants.SIZE_H - MARGIN_H * 2) / ( NS_Y + 0.5f );
  static final float SQUARE_SIZE_3D = SQUARE_SIZE_H / 2;

  static final float CORRECT_Y = SQUARE_SIZE_3D + SQUARE_SIZE_H / 4;
  static final float CORRECT_X = 0;

  public static final float BUTTONS_SIZE = 74;

  /* EVENTS TYPES */
  public static final int NONE 		= -1;
  public static final int SQUARE 		= 0;
  public static final int UNIT 		= 1;
  public static final int SHIELD 		= 2;
  public static final int MAGIC 		= 3;
  public static final int SETTINGS 	= 4;
  public static final int EXIT        = 5;
}
