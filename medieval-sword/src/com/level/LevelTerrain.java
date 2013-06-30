package com.level;

import java.util.ArrayList;

public class LevelTerrain {
	public int SQUARES_X;
	public int SQUARES_Y;
	
	public int terrain[][];
	public int explored[][];
	
	public ArrayList<LevelResourceStructure> structures;
	
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
