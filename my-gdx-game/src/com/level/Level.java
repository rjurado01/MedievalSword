package com.level;

import java.util.ArrayList;


public class Level {
	public int SQUARES_X;
	public int SQUARES_Y;
	
	public int terrain[][];
	
	public int level;
	
	public ArrayList<LevelStructure> structures;
	public ArrayList<LevelPlayer> players;
	public ArrayList<String> units;
	public ArrayList<LevelMapEnemies> map_enemies;
	
	public void initializeTerrain() {
		terrain = new int[ SQUARES_Y ][];
		
		for( int i = 0; i < SQUARES_Y; i++ )
			terrain[i] = new int[ SQUARES_X ];
	}
	
	public void printTerrain() {
		for( int i = 0; i < SQUARES_Y; i++ ) {
			for( int j = 0; j < SQUARES_X; j++)
				System.out.print( terrain[i][j] );
			System.out.println();
		}
	}
}
