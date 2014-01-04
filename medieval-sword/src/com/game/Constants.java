package com.game;

public class Constants {

	/* SCREEN SIZE */
	public static final int SIZE_W = 1152;
	public static final int SIZE_H = 768;
	public static final int SCALE = 1;

	/* ORIENTATIONS */
	public static final int N_ORIENTATIONS = 4;
	public static final int MAP_ORIENTATIONS = 4;
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	public static final int TOP = 2;
	public static final int DOWN = 3;

	/* GENERAL */
	public static final String LANGUAGE = "en";
	public static final int UNDEFINED = -1;
	public static final float FONT1_WIDTH = 7.7f;
	public static final int FONT1_HEIGHT = 8;

	/* BATTLE SIDES */
	public static final int LEFT_SIDE 	= 1;
	public static final int RIGHT_SIDE 	= 2;

	/* EVENTS TYPES */
	public static final int NONE 		= -1;
	public static final int SQUARE 		= 0;
	public static final int UNIT 		= 1;
	public static final int SHIELD 		= 2;
	public static final int MAGIC 		= 3;
	public static final int SETTINGS 	= 4;

	/* TEXTURES COLORS */
	public static final int N_COLORS = 3;
	public static final int GREY = 0;
	public static final int BLUE = 1;
	public static final int RED	 = 2;

	/* HUD */
	public static final int HUD_WIDTH = Constants.SIZE_W / 6;
	public static final int HUD_HEIGHT = Constants.SIZE_H;

	/* PLAYERS */
	public static final int NEUTRAL = 0;
	public static final int PLAYER_1 = 1;
	public static final int PLAYER_2 = 2;

	/* UNITS */
	public static final int VILLAGER = 11;
	public static final int ARCHER = 12;
	public static final int CRUSADER = 13;
	public static final int WIZARD = 14;
	public static final int KNIGHT = 15;

	/* GAME */
	public static final int WEEK_DAYS = 7;

}
