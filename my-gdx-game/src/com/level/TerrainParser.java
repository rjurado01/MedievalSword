package com.level;

public class TerrainParser {
	
	public static int getSquareType( int id ) {
		return id;
	}
	
	public static String getSquareTextureName( int id ) {
		switch ( id ) {
			case 1: return "mapWater2";
			case 2: return "mapRoad";
			default: return "mapGrass";
		}
	}
}
