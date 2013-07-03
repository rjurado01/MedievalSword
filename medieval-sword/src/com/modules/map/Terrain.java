package com.modules.map;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.Assets;
import com.utils.Vector2i;

public class Terrain {

	static final int GRASS = 0;
	static final int WATER = 1;
	static final int ROAD = 2;

	final int GOLD_MINE = 0;
	final int STONE_MINE = 1;
	final int SAWMILL = 2;

	static public final int EXPLORED = 1;
	static public final int DARK = 0;

	public int SQUARES_X;
	public int SQUARES_Y;

	ArrayList<SquarePath> path_drawn;
	List<ResourceStructure> resource_structures;
	List<ResourcePile> resource_piles;
	List<MapActor> objects;

	SquareTerrain terrain[][];
	int explored[][];

	Stage stage;


	public Terrain( Vector2i n_squares ) {
		SQUARES_X = n_squares.x;
		SQUARES_Y = n_squares.y;

		initializeTerrain();

		path_drawn = new ArrayList<SquarePath>();
	}

	private void initializeTerrain() {
		terrain = new SquareTerrain[ SQUARES_Y ][];
		explored = new int[ SQUARES_Y ][];

		for( int i = 0; i < SQUARES_Y; i++ ) {
			terrain[i] = new SquareTerrain[ SQUARES_X ];
			explored[i] = new int[ SQUARES_X ];

			for( int j = 0; j < SQUARES_X; j++ )
				explored[i][j] = DARK;
		}
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
		this.stage = stage;

		for( int i = 0; i < SQUARES_Y; i++)
			for( int j = 0; j < SQUARES_X; j++)
				stage.addActor( terrain[i][j] );
	}

	public Stage getStage() {
		return stage;
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

			if( terrain[item.y][item.x].isRoadAvailable() ) {
				if( i < mobility )
					square_path = new SquarePath( getSquarePosition( item ), true );
				else
					square_path = new SquarePath( getSquarePosition( item ), false );

				path_drawn.add( square_path );
				stage.addActor( square_path );
			}

			i++;
		}
	}

	public void removePathDrawn() {
		while( path_drawn.size() > 0 ) {
			stage.removeActor( path_drawn.get( 0 ) );
			path_drawn.remove( 0 );
		}
	}

	public void removeFirstPathDrawnElement() {
		if( path_drawn.size() > 0 ) {
			stage.removeActor( path_drawn.get( 0 ) );
			path_drawn.remove( 0 );
		}
	}
	
	public void passTurn() {
		for( ResourceStructure structure : resource_structures )
			structure.turnAction();
	}

	public void exploreSquare( int x, int y ) {
		explored[y][x] = EXPLORED;
	}

	/**
	 * Draw fog for terrain
	 */
	public void drawFog() {
		for( int i = 0; i < SQUARES_Y; i++)
			for( int j = 0; j < SQUARES_X; j++)
				if( explored[i][j] == DARK )
					terrain[i][j].setFog( stage );
	}

	/**
	 * Remove fog from a terrain area
	 * @param square center of area
	 * @param size right-top increase diagonal
	 * (use for elements who occupy more than one square)
	 * @param diagonal diagonal number of squares (diagonal) of area
	 * @param mini_map
	 */
	public void explore( Vector2i square, Vector2i size, int diagonal, MiniMap mini_map ) {
		int radius = diagonal / 2;
		int y, x;

		for( int i = 0; i < diagonal + size.y - 1; i++ )
			for( int j = 0; j < diagonal + size.x - 1; j++ ) {
				y = square.y + i - radius;
				x = square.x + j - radius;

				if( y >= 0 && x >= 0 && y < SQUARES_Y && x < SQUARES_X &&
						explored[y][x] == DARK ) {
					explored[y][x] = EXPLORED;
					terrain[y][x].exprlore();

					mini_map.updatePosition( new Vector2i( x, y ) );
				}
			}
	}

	/**
	 * Remove fog from a terrain area
	 * @param square center of area
	 * @param diagonal number of squares (diagonal) of area
	 * @param mini_map
	 */
	public void explore( Vector2i square, int diagonal, MiniMap mini_map ) {
		explore( square, new Vector2i(1,1), diagonal, mini_map );
	}
}
