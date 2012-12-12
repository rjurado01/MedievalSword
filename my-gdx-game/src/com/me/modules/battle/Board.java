package com.me.modules.battle;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.mygdxgame.Assets;
import com.me.mygdxgame.Unit;
import com.me.utils.Vector2i;

/**
 * Represent the board in the battle. 
 * It is composed by NS_Y * NS_X squares. 
 */
public class Board 
{	
	/** Number of board's squares */
	static final int NS_X = 12;
	static final int NS_Y = 6;
	
	static final int CORRECT_Y = 12;
	static final int CORRECT_X = 0;
	
	SquareBoard matrix [][];	// squares matrix
	
	Vector2 pos;	// board initial position
	
	List<SquareBoard> squares_list = new ArrayList<SquareBoard>();
	
	// Lists for calculate and save the way
	List<SquareBoard> open_list = new ArrayList<SquareBoard>();
	List<SquareBoard> close_list = new ArrayList<SquareBoard>();
	List<SquareBoard> way_list = new ArrayList<SquareBoard>();
	
	Stage stage;
	
	Image background;
	
	
	/** 
	 * Class constructor.
	 */
	public Board( Stage stage ) {
		this.stage = stage;
		
		pos = new Vector2( 30, BattleScreen.SIZE_H - SquareBoard.SIZE_H - 15);
		
		background = new Image( Assets.getTextureRegion( "grass2" ) );
		background.width = BattleScreen.SIZE_W;
		background.height = BattleScreen.SIZE_H - BattlePanel.SIZE_H;
		background.x = 0;
		background.y = BattlePanel.SIZE_H;		
		
		stage.addActor( background );
		
		createSquares();
	}
	
	/**
	 * Return square position from number of square
	 * @param x x square number
	 * @param y y square number
	 * @return square position
	 */
	public Vector2 getPositionFromNumber(int x, int y) {
		if( x < NS_X && x > 0 && y < NS_Y && y > 0)
			return new Vector2(x * SquareBoard.SIZE_W, y * SquareBoard.SIZE_H);
		else
			return null;
	}
	
	/**
	 * Return square which specific number 
	 * @param x x square number
	 * @param y y square number
	 * @return square which this number
	 */
	public SquareBoard getSquareFromNumber(int x, int y) {
		if(x < NS_X && y < NS_Y)
			return matrix[y][x];
		else
			return null;
	}
	
	/**
	 * Return square that has specific position
	 * @param x x square position
	 * @param y y square position
	 * @return square which this position
	 */
	public SquareBoard getSquareFromCoordinates(float x, float y) {
		int sx = (int)(x / SquareBoard.SIZE_W);
		int sy = (int)(y / SquareBoard.SIZE_H);
		
		if(x < NS_X && y < NS_Y)
			return matrix[sy][sx];
		else
			return null;
	}
	
	/**
	 * @return square list
	 */
	public List<SquareBoard> getSquaresList() {
		return squares_list;
	}

	/**
	 * @return way list
	 */
	public List<SquareBoard> getWayList() {
		return way_list;
	}
	
	/**
	 * Create board matrix with squares. Add all squares to squares_list.
	 */
	private void createSquares() {
		matrix = new SquareBoard[NS_Y][];
		for(int i=0;i<NS_Y;i++)
			matrix[i] = new SquareBoard[NS_X];
		
		for(int i=0;i<NS_Y;i++)
			for(int j=0;j<NS_X;j++) 
			{
				matrix[i][j] = new SquareBoard( 
						new Vector2( pos.x + ( j * SquareBoard.SIZE_W ), pos.y - ( i  * SquareBoard.SIZE_H ) ), 
						new Vector2i( j, i ) );
				
				squares_list.add(matrix[i][j]);
				//matrix[i][j].visible = false;
				
				stage.addActor( matrix[i][j] );
			}
	}
	
	/**
	 * Find the way between origin square and finish square
	 * 
	 * @param xo x square origin number
	 * @param yo y square origin number
	 * @param xf x square finish number
	 * @param yf y square finish number
	 */
	public void findWay(int xo, int yo, int xf, int yf) {
		
		// Clean all list
		open_list.clear();
		close_list.clear();
		way_list.clear();
		
		// First father is origin square
		SquareBoard father = matrix[yo][xo];
		father.setVisited(true);
		
		// Apply A* algorithm
		while(father.getNumber().x != xf || father.getNumber().y != yf) 
		{
			// Add new adjacent squares to open list and calculate its values
			check(father.getNumber().y + 1, father.getNumber().x, yf, xf, father);
			check(father.getNumber().y - 1, father.getNumber().x, yf, xf, father);
			check(father.getNumber().y, father.getNumber().x + 1, yf, xf, father);
			check(father.getNumber().y, father.getNumber().x - 1, yf, xf, father);
			
			father = getFather(); 

			open_list.remove(father);
			close_list.add(father);
		}
		
		// Get way squares and add they to way_list
		do {
			way_list.add(father);
		} while( (father = father.getFather()) != null ) ;
	}	
	
	/**
	 * Check if square is free, calculate its values and add to open_list
	 * 
	 * @param y y square number
	 * @param x x square number
	 * @param yf y square finish number
	 * @param xf x square finish number
	 * @param father actual father
	 */
	public void check(int y, int x, int yf, int xf, SquareBoard father) {

		// Check if it is valid board square
		if(y < NS_Y && y >= 0 && x < NS_X && x >= 0 )
		{
			// Check if already it has visited or if it is free
			if( matrix[y][x].isVisited() == false && matrix[y][x].isFree() ) 
			{	
				matrix[y][x].setVisited(true);
				matrix[y][x].setFather(father);
				matrix[y][x].setG(father.getG() + 10);
				matrix[y][x].calculate( xf, yf );
				
				open_list.add( matrix[y][x] );
			}
			// Check if it is best way
			else if( matrix[y][x].isVisited() && matrix[y][x].getG() > father.getG() + 10 )
			{	
				matrix[y][x].setFather(father);
				matrix[y][x].setG(father.getG() + 10);
				matrix[y][x].calculate( xf, yf );
			}
		}
	}	
	
	/**
	 * Calculate the next best square (father)
	 * @return actual square father
	 */
	public SquareBoard getFather() {
		SquareBoard best = null;
		int min = 1000;
		
		for( SquareBoard aux : open_list ) {
			if(aux.F < min) {
				best = aux;
				min = aux.F;
			}
		}
		
		return best;
	}
	
	/**
	 * Change textures around unit selected which indicate available movements
	 * 
	 * @param number square selected unit number
	 * @param radius movement radius of selected unit
	 * @param player units id of player
	 */
	public void selectUnit( Unit unit, int radius, int player ) {
		Vector2i number = unit.getSquare().getNumber();
		
		for( int y = number.y - radius; y < number.y + radius + 1; y++ )
			for( int x = number.x - radius; x < number.x + radius + 1; x++ )
			{
				if( isValidDestination( number, new Vector2i(x, y), radius ) ) 
				{
					if( matrix[y][x].hasEnemy( player ) )
						matrix[y][x].setEnemyOn( player );
					else
						matrix[y][x].setAvailableOn();
				}
			}
		
		for( int y = number.y - radius; y < number.y + radius + 1; y++ )
			for( int x = number.x - radius; x < number.x + radius + 1; x++ )
			{
				if( isValidDestination( number, new Vector2i(x, y), radius ) ) 
				{
					if( !checkDestination( x, y, number, player ) )
						matrix[y][x].setNormalOn();
				}
			}
		
		if( unit.getActualRange() > 0 )
			setAllEnemyOn( player );

		matrix[ number.y ][ number.x ].setSelectedUnitOn();
	}
	
	public boolean checkDestination( int x, int y, Vector2i number, int player_code ) {
		// Check if square has player unit
		if( matrix[y][x].status == player_code )
			return false;
				
		// Check if exist valid way to square from unit
		if( x - 1 >= 0 && matrix[y][x-1].isAvailable() || ( x-1 == number.x && y == number.y)  )
			return true;
		else if( x + 1 < NS_X && matrix[y][x+1].isAvailable() || ( x+1 == number.x && y == number.y)  )
			return true;
		else if( y + 1 < NS_Y && matrix[y+1][x].isAvailable() || ( x == number.x && y+1 == number.y)  )
			return true;
		else if( y - 1 >= 0 && matrix[y-1][x].isAvailable() || ( x == number.x && y-1 == number.y)  ) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Check if end square is a valid destination for unit that is in init square whith radius
	 * @param init square where is unit
	 * @param end square for check if is valid
	 * @param radius radius unit movement 
	 * @return true if end is valid destination and false otherwise
	 */
	public boolean isValidDestination(Vector2i init, Vector2i end, int radius) {
		if( isValidSquare( end ) && ( Math.abs(end.y - init.y) + Math.abs(end.x - init.x) <= radius ) )
			return true;
		else
			return false;
	}
	
	/**
	 * Check if exits any square with this number of row and column
	 * @param number array with row and column of square
	 * @return true if is a valid square and false otherwise
	 */
	public boolean isValidSquare( Vector2i number ) {
		if( number.y < NS_Y && number.y >= 0 && number.x < NS_X && number.x >= 0 )
			return true;
		else
			return false;
	}
	
	/**
	 * Reset all squares variables for find way
	 */
	public void resetSquares( ) {
		
		for(int i=0;i<NS_Y;i++) {
			for(int j=0;j<NS_X;j++) {
				matrix[i][j].reset();
			}
		}
	}
	
	public void printMatrixStatus() {
		for(int i=0;i<NS_Y;i++) {
			for(int j=0;j<NS_X;j++) {
				System.out.print( "[ " + matrix[i][j].status + " ]" );
			}
			System.out.println();
		}
		
		System.out.println();
	}
	
	public void printMatrixTextureStatus() {
		for(int i=0;i<NS_Y;i++) {
			for(int j=0;j<NS_X;j++) {
				System.out.print( "[ " + matrix[i][j].texture_status + " ]" );
			}
			System.out.println();
		}
		
		System.out.println();
	}

	/**
	 * Show the squares from which unit can attack the selected enemy
	 * @param enemy enemy square number
	 * @param unit unit square number
	 * @param movility unit movility
	 */
	/*public void showAtackPositions(Vector2i enemy, Vector2i unit, int movility ) 
	{
		for( int y = unit.y - movility; y < unit.y + movility + 1; y++ )
			for( int x = unit.x - movility; x < unit.x + movility + 1; x++ )
			{
				if( isValidDestination( unit, new Vector2i(x, y), movility ) )
				{
					if( Math.abs( y - enemy.y ) + Math.abs( x - enemy.x ) > 1 )
						matrix[y][x].texture_status = null;
				}
			}
		
		if( Math.abs( unit.y - enemy.y ) + Math.abs( unit.x - enemy.x ) > 1 )
			matrix[unit.y][unit.x].texture_status = SquareBoard.T_AVAILABLE;
	}*/
	
	public void showAttackPositions( Vector2i enemy, Vector2i me ) {
		for( int y = 0; y < NS_Y; y++ )
			for( int x = 0; x < NS_X; x++ ) {
				if( matrix[y][x].isAvailable() && Math.abs( y - enemy.y ) + Math.abs( x - enemy.x ) > 1 )
					matrix[y][x].setTexture( SquareBoard.T_NORMAL );
			}
		
		if( Math.abs( enemy.y - me.y ) + Math.abs( enemy.x - me.x ) == 1 ) {
			matrix[me.y][me.x].setAvailableOn();
		}
	}
	
	public void setAllEnemyOn( int player ) {
		for( int y = 0; y < NS_Y; y++ )
			for( int x = 0; x < NS_X; x++ ) {
				matrix[y][x].setEnemyOn( player );
			}
	}
}
