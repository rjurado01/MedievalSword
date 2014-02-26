package com.modules.map.heroes;

import java.util.List;

import com.modules.map.MapController;
import com.modules.map.terrain.PathFinder;
import com.modules.map.terrain.SquareTerrain;
import com.modules.map.terrain.Terrain;
import com.utils.Vector2i;

/**
 * Find path in the map from a Hero to destiny
 */
public class HeroPath {

	Terrain terrain;
	MapController controller;

	List<Vector2i> path;
	int mobility = 0;
	boolean path_marked;

	Vector2i last_available;

	public HeroPath( Terrain terrain ) {
		this.terrain = terrain;
		this.path_marked = false;
	}

	public void addPath( List<Vector2i> path, int mobility ) {
		this.path = path;
		this.mobility = mobility;

		if( path != null ) {
			this.path_marked = true;
			terrain.drawPathSelected( path, mobility );
		}
	}

	public void removeMarked() {
		path_marked = false;
	}

	public void removePath() {
		path_marked = false;
		terrain.removePathDrawn();
	}

	public Vector2i getFirstElement() {
		return path.get( 0 );
	}

	public void removeFirstElement() {
		path.remove( 0 );

		if( path.size() == 0 )
			path_marked = false;
	}

	public void removeLastElement() {
		path.remove( path.size() - 1 );
	}

	/**
	 * Returns if there is valid path marked
	 */
	public boolean isPathMarked() {
		return path_marked;
	}

	/**
	 * Check if marked is the last square of the path list
	 * @param marked number of marked square
	 * @return true if is the last and false otherwise
	 */
	public boolean isLastMarked( Vector2i marked ) {
		if( path == null || path.size() == 0 )
			return false;

		Vector2i end = path.get( path.size() - 1 );

		if( marked.x == end.x &&  marked.y == end.y )
			return true;
		else
			return false;
	}

	/**
	 *
	 * @param marked
	 * @return
	 */
	public boolean isValidDestination( Vector2i marked ) {
		if( isLastMarked(marked) && mobility >= path.size() )
			return true;
		else
			return false;
	}

	/**
	 * Find path from origin to destination and check if is valid path.
	 * If path isn't valid, cut it for get a valid path.
	 *
	 * @param origin number of squareTerrain origin
	 * @param destination number of squareTerrain destination
	 */
	public void findPath( Vector2i origin, Vector2i destination, int mobility ) {
		this.mobility = mobility;
		removePath();

		PathFinder path_finder = new PathFinder(
			terrain.getRoadsMatrix( destination ), terrain.SQUARES_X, terrain.SQUARES_Y );

		path = path_finder.findWay( origin, destination );

		// If there is an enemy in the path, cut it
		Vector2i taken_square_number = getFirstEnemySquareNumber();

		if( taken_square_number != null )
			path = path.subList( 0, path.indexOf( taken_square_number ) + 1 );

		// Draw path
		terrain.drawPathSelected( path, mobility );

		// Set last item available
		if( path.size() >= mobility + 1 )
			last_available = path.get( mobility );
		else
			last_available = null;

		path_marked = true;
	}

	/**
	 * Iterate path array and return first element that is not a road available
	 */
	private Vector2i getFirstEnemySquareNumber() {
		for( Vector2i item : path )
			if( terrain.getSquareTerrain( item ).isRoadAvailable() == false )
				return item;

		return null;
	}

	/**
	 * @return if last element of the path is a available
	 */
	public boolean isLastAvailable() {
		return terrain.getSquareTerrain( path.get( path.size() - 1) ).isRoadAvailable();
	}

	/**
	 * @return last SquareTerrain from the path
	 */
	public SquareTerrain getLastSquareTerrain() {
		return terrain.getSquareTerrain( path.get( path.size() - 1) );
	}

	/**
	 * @return path list
	 */
	public List<Vector2i> getPathList() {
		return path;
	}

	public List<Vector2i> getAvailableList() {
		if( path.size() >= mobility )
			return path.subList( 0, mobility );
		else
			return path;
	}

	public boolean isLastPahtItem( Vector2i item ) {
		if( last_available != null && path.size() > 0 && path.get( 0 ) == last_available )
			return true;
		else
			return false;
	}

	public int getPathSize() {
		return path.size();
	}
}
