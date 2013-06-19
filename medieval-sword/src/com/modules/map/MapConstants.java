package com.modules.map;

import com.game.Constants;

public class MapConstants {

	/* EVENTS TYPES */
	public static final int NONE 		= -1;
	public static final int STRUCTURE 	=  0;
	public static final int SQUARE      =  1;
	public static final int HERO        =  2;
	public static final int CREATURES   =  3;
	public static final int INFO1       =  4;
	public static final int INFO2       =  5;
	public static final int TURN        =  6;
	public static final int RESOURCE_STRUCTURE = 7;
	public static final int RESOURCE_PILE = 8;

	/* MAP SQUARES STATUS */
	public static final int FREE = 0;

	/* HUD */
	public static final int HUD_WIDTH = 80;
	public static final int HUD_HEIGHT = Constants.SIZE_H;

	/* HUD */
	public static final int TERRAIN_WIDTH = Constants.SIZE_W - HUD_WIDTH;
	public static final int TERRAIN_HEIGHT = Constants.SIZE_H;

	/* STUCTURES */
	public static final int GOLD_MINE = 0;
	public static final int SAWMILL = 1;
	public static final int STONE_MINE = 2;
}
