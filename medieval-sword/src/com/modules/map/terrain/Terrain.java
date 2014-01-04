package com.modules.map.terrain;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.Assets;
import com.game.Constants;
import com.modules.castle.TopCastle;
import com.modules.map.MapActor;
import com.modules.map.MapConstants;
import com.modules.map.ui.MiniMap;
import com.utils.Vector2i;

/**
 *	This class save information about terrain drawing in the map
 *	and all elements that it contains.
 */
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

	public int width;
	public int height;
	public int x_left_limit;
	public int x_right_limit;
	public int y_bottom_limit;
	public int y_top_limit;

	ArrayList<SquarePath> path_drawn;
	private List<ResourceStructure> resource_structures;
	private List<ResourcePile> resource_piles;
	private List<MapActor> objects;
	private List<TopCastle> castles;
	MiniMap mini_map;

	SquareTerrain terrain[][];
	int explored[][];

	Group path_layer;

	Stage stage;


	public Terrain( Vector2i n_squares ) {
		SQUARES_X = n_squares.x;
		SQUARES_Y = n_squares.y;

		width = SQUARES_X * MapConstants.SQUARE_TERRAIN_W;
		height = SQUARES_Y * MapConstants.SQUARE_TERRAIN_H;
		x_left_limit = Constants.SIZE_W / 2 - Constants.HUD_WIDTH;
		x_right_limit = width - Constants.SIZE_W / 2;
		y_bottom_limit = Constants.SIZE_H / 2;
		y_top_limit = height - Constants.SIZE_H / 2;

		initializeTerrain();

		path_drawn = new ArrayList<SquarePath>();
		path_layer = new Group();
	}

	public void setMiniMap( MiniMap mini_map ) {
		this.mini_map = mini_map;
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
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Vector2 getSquarePosition( Vector2i square_number ) {
		return terrain[square_number.y][square_number.x].getPosition();
	}

	public void addStage( Stage stage ) {
		this.stage = stage;

		for( int i = SQUARES_Y - 1; i >= 0; i--)
			for( int j = 0; j < SQUARES_X; j++)
				stage.addActor( terrain[i][j] );

		stage.addActor( path_layer );
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

			if( i == path.size() -1 ) {
				if( i < mobility )
					square_path = new SquarePath( getSquarePosition( item ), true, true );
				else
					square_path = new SquarePath( getSquarePosition( item ), false, true );
			}
			else if( i < mobility )
				square_path = new SquarePath( getSquarePosition( item ), true, false );
			else
				square_path = new SquarePath( getSquarePosition( item ), false, false );

			path_drawn.add( square_path );
			path_layer.addActor( square_path );

			i++;
		}
	}

	public void removePathDrawn() {
		while( path_drawn.size() > 0 ) {
			path_layer.removeActor( path_drawn.get( 0 ) );
			path_drawn.remove( 0 );
		}
	}

	public void removeFirstPathDrawnElement() {
		if( path_drawn.size() > 0 ) {
			stage.removeActor( path_drawn.get( 0 ) );
			path_drawn.remove( 0 );
		}
	}

	public void passTurn( int day ) {
		if( resource_structures != null )
			for( ResourceStructure structure : resource_structures )
				structure.turnAction();

		for( TopCastle castle : castles )
			castle.passTurn( day );
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
	public void explore( Vector2i square, Vector2i size, int diagonal ) {
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
	public void explore( Vector2i square, int diagonal ) {
		explore( square, new Vector2i(1,1), diagonal );
	}

	/**
	 * Update terrain squares where there is a structure
	 * @param structure
	 */
	public void addStructure( Structure structure ) {
		Vector2i number = structure.square_number;
		Vector2i size = structure.squares_size;

		for( int y = number.y; y < number.y + size.y; y++ )
			for( int x = number.x; x < number.x + size.x; x++ )
				terrain[y][x].setStructure( structure.color );
	}

	/**
	 * Update structure squares with the owner color so that mini_map will be updated
	 * Also, explore map into the vision range of structure
	 * @param structure
	 */
	public void captureStructure( Structure structure ) {
		Vector2i number = structure.square_number;
		Vector2i size = structure.squares_size;

		explore( number, size, structure.vision );

		for( int y = number.y; y < number.y + size.y; y++ )
			for( int x = number.x; x < number.x + size.x; x++ ) {
					terrain[y][x].setStructure( structure.color );
					mini_map.updatePosition( new Vector2i(x,y) );
			}
	}

	public List<MapActor> getObjects() {
		return objects;
	}

	public void setObjects( List<MapActor> objects ) {
		this.objects = objects;
	}

	public List<ResourcePile> getResourcePiles() {
		return resource_piles;
	}

	public void setResourcePiles( List<ResourcePile> resource_piles ) {
		this.resource_piles = resource_piles;
	}

	public List<ResourceStructure> getResourceStructures() {
		return resource_structures;
	}

	public void setResourceStructures(List<ResourceStructure> resource_structures) {
		this.resource_structures = resource_structures;
	}

	public List<TopCastle> getCastles() {
		return castles;
	}

	public void setCastles( List<TopCastle> castles ) {
		this.castles = castles;
	}

	public Vector2i getSize() {
		return new Vector2i( getWidth(), getHeight() );
	}

	public Group getPathLayer() {
		return path_layer;
	}

	public void centerCamera( Vector2 position ) {
		Camera camera = stage.getCamera();
		Vector2 final_position = new Vector2( position );

		if( position.x < x_left_limit || position.x > x_right_limit )
			final_position.x = camera.position.x;

		if( position.y < y_bottom_limit || position.y > y_top_limit )
			final_position.y = camera.position.y;

		if( position != final_position )
			camera.translate(
				final_position.x - camera.position.x,
				final_position.y - camera.position.y,
				0);
	}

	public boolean cameraOutOfLimit( Vector3 position ) {
		if( position.x < x_left_limit || position.x > x_right_limit )
			return true;
		else if( position.y < y_bottom_limit || position.y > y_top_limit )
			return true;
		else
			return false;
	}
}
