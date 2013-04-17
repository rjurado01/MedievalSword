package com.modules.battle;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdxgame.Assets;
import com.mygdxgame.Constants;
import com.mygdxgame.Stack;
import com.utils.Vector2i;

/**
 * Represent the board in the battle. 
 * It is composed by NS_Y * NS_X squares. 
 */
public class Board 
{	
	static final int NS_X = 12;
	static final int NS_Y = 6;
	
	static final int CORRECT_Y = 12;
	static final int CORRECT_X = 0;
	
	SquareBoard matrix [][];
	Vector2 position;
	
	List<SquareBoard> squares_list = new ArrayList<SquareBoard>();
	
	Stage stage;	
	Image background;
	BoardPathFinding path_finding;
	
	public Board( Stage stage ) {
		this.stage = stage;
		position = new Vector2( 30, Constants.SIZE_H - SquareBoard.SIZE_H - 15);
		
		createBackgroundImage();		
		stage.addActor( background );
		createMatrixSquares();
		
		path_finding = new BoardPathFinding( matrix );
	}
	
	private void createBackgroundImage() {
		background = new Image( Assets.getTextureRegion( "grass2" ) );
		background.width = Constants.SIZE_W;
		background.height = Constants.SIZE_H - BattlePanel.SIZE_H;
		background.x = 0;
		background.y = BattlePanel.SIZE_H;	
	}
	
	private void createMatrixSquares() {
		matrix = new SquareBoard[NS_Y][];
		for(int i=0;i<NS_Y;i++)
			matrix[i] = new SquareBoard[NS_X];
		
		for(int i=0;i<NS_Y;i++)
			for(int j=0;j<NS_X;j++) 
				createMatrixSquare( j, i );
	}
	
	private void createMatrixSquare( int x, int y ) {
		matrix[y][x] = new SquareBoard( 
				new Vector2( position.x + ( x * SquareBoard.SIZE_W ), position.y - ( y  * SquareBoard.SIZE_H ) ), 
				new Vector2i( x, y ) );
		
		squares_list.add(matrix[y][x]);
		stage.addActor( matrix[y][x] );
	}
	
	public Vector2 getSquarePosition( int x_square_number, int y_square_number ) {
		if( !isValidSquareNumber( x_square_number, y_square_number ) )
			return null;
		
		return new Vector2( x_square_number * SquareBoard.SIZE_W,
				y_square_number * SquareBoard.SIZE_H);
	}
	
	private boolean isValidSquareNumber( int ns_x, int ns_y ) {
		if( ns_x < NS_X && ns_x >= 0 && ns_y < NS_Y && ns_y >= 0)
			return true;
		else
			return false;
	}
	
	public SquareBoard getSquare( int x_square_number, int y_square_number ) {
		if( isValidSquareNumber( x_square_number, y_square_number ) )
			return matrix[y_square_number][x_square_number];
		else
			return null;
	}
	
	public SquareBoard getSquareByCoordinates( float x, float y ) {
		int square_number_x = (int)(x / SquareBoard.SIZE_W);
		int square_number_y = (int)(y / SquareBoard.SIZE_H);
		
		if( square_number_x < NS_X && square_number_y < NS_Y )
			return matrix[square_number_y][square_number_x];
		else
			return null;
	}
	
	public List<SquareBoard> getSquaresList() {
		return squares_list;
	}
	
	public boolean findWay( SquareBoard init, SquareBoard end ) {
		path_finding.findWay( init, end );
		
		if( path_finding.getLastWay().size() > 0 )
			return true;
		else
			return false;
	}
	
	public List<SquareBoard> getLastWay() {
		return path_finding.getLastWay();
	}
	
	/**
	 * Change textures around unit selected which indicate available movements
	 * 
	 * @param number_units square selected unit number
	 * @param radius movement radius of selected unit
	 * @param battle_side units id of player
	 */
	public void selectStack( Stack stack, int battle_side ) {		
		changeTextureStatusOfStacks( stack, battle_side );	
		deleteUnreachableSquares( stack );

		if( stack.getRange() > 0 )
			setAllEnemyOn( battle_side );

		stack.getSquare().setSelectedUnitOn();
	}
	
	private void changeTextureStatusOfStacks( Stack stack, int battle_side ) {
		for( int y = 0; y < NS_Y; y++ )
			for( int x = 0; x < NS_X; x++ ) {
				if( isAchievable( stack.getSquare(), matrix[y][x], stack.getMovility() )) {
					if( matrix[y][x].isFree() )
						matrix[y][x].setAvailableOn();
					else if( matrix[y][x].hasEnemy( battle_side ) )
						matrix[y][x].setEnemyOn( battle_side );
				}
			}
	}
	
	public void deleteUnreachableSquares( Stack stack ) {
		for( int y = 0; y < NS_Y; y++ )
			for( int x = 0; x < NS_X; x++ ) {
				if( getSquare(x, y).isAvailable() &&
						!path_finding.checkWay( stack.getSquare(),
								getSquare(x, y), stack.getMovility() ) )
					matrix[y][x].setNormalOn();
			}
	}
	
	public boolean isAchievable( SquareBoard init, SquareBoard end, int movility ) {
		if( Math.abs( end.getNumber().y - init.getNumber().y ) + 
				Math.abs( end.getNumber().x - init.getNumber().x) <= movility )
			return true;
		else
			return false;
	}
	
	public boolean isValidSquare( Vector2i number ) {
		if( number.y < NS_Y && number.y >= 0 && number.x < NS_X && number.x >= 0 )
			return true;
		else
			return false;
	}
	
	/**
	 * Reset all squares. 
	 * This reset all variables used for find way.
	 * This set normal texture_statuas and normal texture. 
	 */
	public void resetSquares() {
		
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

	public void showAttackPositions( Vector2i enemy, Vector2i me ) {
		for( int y = 0; y < NS_Y; y++ )
			for( int x = 0; x < NS_X; x++ )
				if( matrix[y][x].isAvailable() && Math.abs( y - enemy.y ) + Math.abs( x - enemy.x ) > 1 )
					matrix[y][x].setTexture( SquareBoard.T_NORMAL );
		
		if( Math.abs( enemy.y - me.y ) + Math.abs( enemy.x - me.x ) == 1 )
			matrix[me.y][me.x].setAvailableOn();
	}
	
	/**
	 * Set all enemy squares available for attack
	 * @param player units of player id
	 */
	public void setAllEnemyOn( int player ) {
		for( int y = 0; y < NS_Y; y++ )
			for( int x = 0; x < NS_X; x++ )
				matrix[y][x].setEnemyOn( player );
	}
}
