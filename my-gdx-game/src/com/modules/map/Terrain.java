package com.modules.map;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.level.Level;
import com.level.LevelStructure;
import com.level.TerrainParser;
import com.mygdxgame.Assets;
import com.resources.GoldMine;
import com.resources.Sawmill;
import com.resources.StoneMine;
import com.utils.Vector2i;

public class Terrain extends Group {
	
	final int GRASS = 0;
	final int WATER = 1;
	final int ROAD = 2;
	
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
	
	int SQUARES_X = 20;
	int SQUARES_Y = 15;
	
	ArrayList<ResourceStructure> resource_structures;
	
	SquareTerrain terrain[][];
	
	public Terrain( Stage stage, Level level ) {
		this.stage = stage;

		loadTerrain( level );
		loadStructures( level );
	}
	
	private void loadTerrain( Level level ) {
		SQUARES_X = level.SQUARES_X;
		SQUARES_Y = level.SQUARES_Y;

		initializeTerrain();
		createTerrain( level.terrain );
	}
	
	private void initializeTerrain() {
		terrain = new SquareTerrain[ SQUARES_Y ][];
		
		for( int i = 0; i < SQUARES_Y; i++ )
			terrain[i] = new SquareTerrain[ SQUARES_X ];
	}
	
	private void createTerrain( int[][] lvl_terrain ) {
		int inverse = SQUARES_Y - 1; // in libgdx, [0,0] is [SQUARES_Y - 1][0]
		
		for( int i = 0; i < SQUARES_Y; i++)
			for( int j = 0; j < SQUARES_X; j++)
				addSquareTerrain( new Vector2i( j, i ), lvl_terrain[inverse - i][j] );
	}
	
	private void addSquareTerrain( Vector2i square_number, int id ) {
		terrain[square_number.y][square_number.x] =
				new SquareTerrain( square_number, TerrainParser.getSquareType(id) );
		
		terrain[square_number.y][square_number.x].setRegion(
				Assets.getTextureRegion( TerrainParser.getSquareTextureName(id) ) );
		
		stage.addActor( terrain[square_number.y][square_number.x] );
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
}
