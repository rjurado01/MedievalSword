package com.modules.map;

import com.mygdxgame.Constants;

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

	/* MAP SQUARES STATUS */
	public static final int FREE = 0;

	/* HUD */
	public static final int HUD_WIDTH = 80;
	public static final int HUD_HEIGHT = Constants.SIZE_H;

	/* HUD */
	public static final int TERRAIN_WIDTH = Constants.SIZE_W - HUD_WIDTH;
	public static final int TERRAIN_HEIGHT = Constants.SIZE_H;

}
