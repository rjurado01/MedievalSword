package com.modules.battle;

import java.util.ArrayList;
import java.util.List;

import com.utils.Vector2i;

/**
 * Execute A* algorithm for find best way between initial square and end square.
 */
public class BoardPathFinding {

	SquareBoard matrix_squares [][];
	SquareBoard father;
	Vector2i end_square_number;

	List<SquareBoard> way_list = new ArrayList<SquareBoard>();
	List<SquareBoard> open_list = new ArrayList<SquareBoard>();
	List<SquareBoard> close_list = new ArrayList<SquareBoard>();


	public BoardPathFinding( SquareBoard matrix_squares [][] ) {
		this.matrix_squares = matrix_squares;
	}

	public void findWay( SquareBoard init_square, SquareBoard end_square ) {
		clearWayLists();

		end_square_number = end_square.getNumber();
		father = init_square;
		father.setVisited(true);

		applyAlgorithm();
	}

	private void applyAlgorithm() {
		while( isValidFather() )  {
			checkAdjacencies();
			selectNextFather();
		}

		createWayList( father );
	}

	private boolean isValidFather() {
		if( father == null )
			return false;
		else if( father.getNumber().x != end_square_number.x )
			return true;
		else if( father.getNumber().y != end_square_number.y )
			return true;
		else
			return false;
	}

	/**
	 * Add new adjacent squares to open list and calculate its values.
	 * Adjacent squares are: top, bottom, right and left squares of father square.
	 */
	private void checkAdjacencies() {
		checkAdyacentSquare( father.getNumber().x, 	 father.getNumber().y + 1 );
		checkAdyacentSquare( father.getNumber().x, 	 father.getNumber().y - 1 );
		checkAdyacentSquare( father.getNumber().x + 1, father.getNumber().y );
		checkAdyacentSquare( father.getNumber().x - 1, father.getNumber().y );
	}

	/**
	 * Check if square is free, calculate its values and add to open_list.
	 *
	 * @param y y square number
	 * @param x x square number
	 */
	private void checkAdyacentSquare( int x, int y ) {
		if( isValidSquare( x, y ) )
		{
			if( !matrix_squares[y][x].isVisited() && matrix_squares[y][x].isFree() )
				visitSquare( x, y );
			else if( isBestSquare( matrix_squares[y][x] ) )
				updateSquareInfo( x, y );
		}
	}

	private boolean isValidSquare( int x, int y ) {
		if( y < BattleConstants.NS_Y && y >= 0 && x < BattleConstants.NS_X && x >= 0 )
			return true;
		else
			return false;
	}

	private boolean isBestSquare( SquareBoard square ) {
		if( square.isVisited() && square.getG() > father.getG() + 10 )
			return true;
		else
			return false;
	}

	private void selectNextFather() {
		father = getFather();

		open_list.remove( father );
		close_list.add( father );
	}

	private void clearWayLists() {
		open_list.clear();
		close_list.clear();
		way_list.clear();
	}

	private void createWayList( SquareBoard father ) {
		if( father != null & father.getFather() != null
				&& father.getFather().getFather() != null )
			createWayList( father.getFather() );

		way_list.add( father );
	}

	private void visitSquare( int x, int y ) {
		updateSquareInfo( x, y );
		matrix_squares[y][x].setVisited(true);
		open_list.add( matrix_squares[y][x] );
	}

	private void updateSquareInfo( int x, int y ) {
		matrix_squares[y][x].setFather(father);
		matrix_squares[y][x].setG(father.getG() + 10);
		matrix_squares[y][x].calculateH( end_square_number );
		matrix_squares[y][x].calculateF();
	}

	/**
	 * Calculate the next best square (father)
	 * @return best new father
	 */
	private SquareBoard getFather() {
		SquareBoard best = null;
		int min = 1000;

		for( SquareBoard aux : open_list )
			if(aux.F < min) {
				best = aux;
				min = aux.F;
			}

		return best;
	}

	/**
	 * Check if destination is attainable
	 *
	 * @param init initial square way
	 * @param end end square way
	 * @param mobility stack mobility
	 *
	 * @return boolean ( true if is valid and false otherwise )
	 */
	public boolean checkWay( SquareBoard init, SquareBoard end, int mobility ) {
		// find way to this square
		findWay( init, end );

		// check if stack can go to this square
		if( getWayList().size() == 0 || getWayList().size() - 1 > mobility ) {
			resetWay();
			return false;
		}
		else {
			resetWay();
			return true;
		}
	}

	/**
	 * Reset values for find way ( A* )
	 */
	private void resetWay() {
		for( int i = 0; i < BattleConstants.NS_Y; i++ )
			for( int j = 0; j < BattleConstants.NS_X; j++ )
				matrix_squares[i][j].resetWayValues();
	}

	private List<SquareBoard> getWayList() {
		return way_list;
	}

	public List<SquareBoard> getWay(SquareBoard init, SquareBoard end) {
		findWay(init, end);
		return way_list;
	}

	public List<SquareBoard> getLastWay() {
		return way_list;
	}
}
