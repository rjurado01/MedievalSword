package com.modules.map;

import com.game.Constants;

public class MapConstants {

	/* EVENTS TYPES */
	public static final int NONE 				= -1;
	public static final int STRUCTURE 			=  0;
	public static final int SQUARE      		=  1;
	public static final int HERO        		=  2;
	public static final int CREATURES   		=  3;
	public static final int INFO1       		=  4;
	public static final int INFO2       		=  5;
	public static final int TURN        		=  6;
	public static final int RESOURCE_STRUCTURE 	=  7;
	public static final int RESOURCE_PILE 		=  8;
	public static final int CASTLE 	    		=  9;
	public static final int CLOSE_CASTLE		= 10;
	public static final int SHOW_BUILDING       = 11;
	public static final int CLOSE_BUILDING      = 12;
	public static final int BUILD_BUILDING      = 13;
	public static final int SHOW_UNIT      		= 14;
	public static final int CLOSE_UNIT          = 15;
	public static final int BUY_UNITS			= 16;
	public static final int CLOSE_PANEL         = 17;

	/* MAP SQUARES STATUS */
	public static final int FREE = 0;

	/* HUD */
	public static final int HUD_WIDTH = Constants.SIZE_W / 6;
	public static final int HUD_HEIGHT = Constants.SIZE_H;

	/* HUD */
	public static final int TERRAIN_WIDTH = Constants.SIZE_W - HUD_WIDTH;
	public static final int TERRAIN_HEIGHT = Constants.SIZE_H;

	/* STUCTURES */
	public static final int GOLD_MINE = 0;
	public static final int SAWMILL = 1;
	public static final int STONE_MINE = 2;

	/* MAP OBJECTS */
	public static final int TREE_1 		= 1;
	public static final int TREE_2		= 2;
	public static final int PLANT_1 	= 3;
	public static final int PLANT_2 	= 4;
	public static final int TREE_CUT_1 	= 5;
	public static final int ROCK_1 		= 6;
	public static final int ROCK_2 		= 7;

	/* UNITS POSITION/LEVEL */
	public static final int LEVEL_1 = 0;

	/* OTHERS */
	public static final int SQUARE_X_POSITION = 8;	// x position of object in square
	public static final int SQUARE_Y_POSITION = 20; // y position of object in square
	public static final float HERO_SQUARE_TIME = 0.8f;	// animation time for square

	/* SCREEN MEASURES */
	public static final int SQUARE_TERRAIN_W = Constants.SIZE_W / 12;
	public static final int SQUARE_TERRAIN_H = Constants.SIZE_H / 8;
	public static final int SQUARE_TERRAIN_3D = MapConstants.SQUARE_TERRAIN_H / 2;
}
