package com.modules.map;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.level.Level;
import com.level.LevelStructure;
import com.level.TerrainParser;
import com.mygdxgame.Assets;
import com.resources.GoldMine;
import com.resources.Sawmill;
import com.resources.StoneMine;
import com.utils.Vector2i;

public class Terrain extends Group {
	
	static final int GRASS = 0;
	static final int WATER = 1;
	static final int ROAD = 2;
	
	final int GOLD_MINE = 0;
	final int STONE_MINE = 1;
	final int SAWMILL = 2;
	
	/*
	final int Tree;
	final int Stone;
	final int Mountain;
	final int Mine_Stone;
	final int Mine_Gold;
	final int Sawmill;
	final int Castle; 
	*/
	
	public int SQUARES_X;
	public int SQUARES_Y;
	
	ArrayList<SquarePath> path_selected;
	ArrayList<ResourceStructure> resource_structures;
	
	SquareTerrain terrain[][];
	
	public Terrain( Vector2i n_squares) {
		SQUARES_X = n_squares.x;
		SQUARES_Y = n_squares.y;
		
		initializeTerrain();
		
		path_selected = new ArrayList<SquarePath>();
	}
	
	private void initializeTerrain() {
		terrain = new SquareTerrain[ SQUARES_Y ][];
		
		for( int i = 0; i < SQUARES_Y; i++ )
			terrain[i] = new SquareTerrain[ SQUARES_X ];
	}
	
	public void addSquareTerrain( Vector2i square_number, int type, String texture ) {
		terrain[square_number.y][square_number.x] = 
				new SquareTerrain( square_number, type );
		
		terrain[square_number.y][square_number.x].setRegion(
				Assets.getTextureRegion( texture ) );
	}
	 
	public SquareTerrain getSquareTerrain( Vector2i square_number ) {
		return terrain[square_number.y][square_number.x];
	}
	
	public void loadStructures( Level level ) {
		resource_structures = new ArrayList<ResourceStructure>();
		
		for( LevelStructure level_structure : level.structures )
			addStructure( level_structure );
	}
	
	public void addStructure( LevelStructure ls ) {
		switch( ls.type ) {
			case GOLD_MINE:
				addResourceStructure( new GoldMine( ls.square_number, ls.owner ) );
				break;
			case STONE_MINE:
				addResourceStructure( new StoneMine( ls.square_number, ls.owner ) );
				break;
			case SAWMILL:
				addResourceStructure( new Sawmill( ls.square_number, ls.owner ) );
				break;
		}
	}
	
	private void addResourceStructure( ResourceStructure rs ) {
		if( rs != null ) {
			TopStructure top = new TopStructure( rs );
			stage.addActor( top.getActor() );
			resource_structures.add( rs );
		}
	}
	
	public int getWidth() {
		return SQUARES_X * SquareTerrain.WIDTH;
	}
	
	public int getHeight() {
		return SQUARES_Y * SquareTerrain.HEIGHT;
	}
	
	public Vector2 getSquarePosition( Vector2i square_number ) {
		return terrain[square_number.y][square_number.x].getPosition();
	}
	
	public void addStage( Stage stage ) {
		for( int i = 0; i < SQUARES_Y; i++)
			for( int j = 0; j < SQUARES_X; j++)
				stage.addActor( terrain[i][j] );
	}
	
	/**
	 * Return matrix with roads available (squares)
	 */
	public int[][] getRoadsMatrix( Vector2i destination ) {
		int matrix[][] = new int[ SQUARES_Y ][ SQUARES_X ];
		
		for( int i = 0; i < SQUARES_Y; i++)
			for( int j = 0; j < SQUARES_X; j++)
				if( terrain[i][j].isRoad() )
					matrix[i][j] = PathFinder.FREE;
				else
					matrix[i][j] = PathFinder.BUSY;
		
		matrix[destination.y][destination.x] = PathFinder.FREE;
		
		return matrix;
	}
	
	public void drawPathSelected( List<Vector2i> path, int mobility ) {
		int i = 0;
		
		for( Vector2i item : path ) {
			SquarePath square_path;
			
			if( i < mobility )
				square_path = new SquarePath( getSquarePosition( item ), true );
			else
				square_path = new SquarePath( getSquarePosition( item ), false );
				
			path_selected.add( square_path );
			stage.addActor( square_path );
			i++;
		}
	}
	
	public void removePathSelected() {
		while( path_selected.size() > 0 ) {
			stage.removeActor( path_selected.get( 0 ) );
			path_selected.remove( 0 );
		}
	}
	
	public void removeFirstPathElement() {
		if( path_selected.size() > 0 ) {
			stage.removeActor( path_selected.get( 0 ) );
			path_selected.remove( 0 );
		}
	}
}
