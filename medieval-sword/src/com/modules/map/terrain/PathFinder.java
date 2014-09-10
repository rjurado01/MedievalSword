package com.modules.map.terrain;


import java.util.ArrayList;
import java.util.List;
import com.utils.Vector2i;

/**
 * This class is used to find the path between two squares in the map (A* algorithm)
 */
public class PathFinder {

  static final int FREE = 0;
  static final int BUSY = 1;

  PathItem matrix [][];
  PathItem father;
  Vector2i origin, destination;
  Vector2i size;

  List<Vector2i> way_list = new ArrayList<Vector2i>();
  List<PathItem> open_list = new ArrayList<PathItem>();
  List<PathItem> close_list = new ArrayList<PathItem>();

  public PathFinder( int terrain [][], int width, int height ) {
    size = new Vector2i( width, height );

    matrix = new PathItem[ height ][];

    for( int i = 0; i < height; i++ ) {
      matrix[i] = new PathItem[ width ];

      for( int j = 0; j < width; j++ )
        if( terrain[i][j] == 0 )
          matrix[i][j] = new PathItem( new Vector2i( j, i ), 0 );
    }
  }

  public List<Vector2i> findWay( Vector2i origin, Vector2i destination ) {
    matrix[origin.y][origin.x] = new PathItem( origin, 1 );

    this.origin = origin;
    this.destination = destination;

    father = matrix[origin.y][origin.x];
    father.visited = true;

    while( isValidFather() )  {
      checkAdjacencies();
      selectNextFather();
    }

    createWayList( father );

    return way_list;
  }

  /**
   * Check if father is null or destination
   */
  private boolean isValidFather() {
    if( father == null )
      return false;
    else if( father.number.x != destination.x )
      return true;
    else if( father.number.y != destination.y )
      return true;
    else
      return false;
  }

  /**
   * Add new adjacent squares to open list and calculate its values.
   * Adjacent squares are: top, bottom, right and left squares of father square.
   */
  private void checkAdjacencies() {
    updateAdyacentItem( father.number.x, father.number.y + 1 );
    updateAdyacentItem( father.number.x, father.number.y - 1 );
    updateAdyacentItem( father.number.x + 1, father.number.y );
    updateAdyacentItem( father.number.x - 1, father.number.y );
  }

  /**
   * Check if item is free, calculate its values and add to open_list.
   *
   * @param y y square number
   * @param x x square number
   */
  private void updateAdyacentItem( int x, int y ) {
    if( isValidItem( x, y ) )
    {
      if( matrix[y][x].visited == false && matrix[y][x].isFree() )
        visitItem( x, y );
      else if( isBestItem( matrix[y][x] ) )
        updateItemInfo( x, y );
    }
  }

  private boolean isValidItem( int x, int y ) {
    if( y < size.y && y >= 0 && x < size.x && x >= 0 && matrix[y][x] != null )
      return true;
    else
      return false;
  }

  private void visitItem( int x, int y ) {
    updateItemInfo( x, y );
    matrix[y][x].visited = true;
    open_list.add( matrix[y][x] );
  }

  private boolean isBestItem( PathItem item ) {
    if( item.visited && item.G > father.G + 10 )
      return true;
    else
      return false;
  }

  private void updateItemInfo( int x, int y ) {
    matrix[y][x].father = this.father;
    matrix[y][x].G = this.father.G + 10;
    matrix[y][x].calculateH( destination );
    matrix[y][x].calculateF();
  }

  private void selectNextFather() {
    int min = 1000;

    if( open_list.size() > 0 ) {
      for( PathItem aux : open_list )
        if( aux.F < min ) {
          father = aux;
          min = aux.F;
        }

      open_list.remove( father );
      close_list.add( father );
    }
    else
      father = null;
  }

  private void createWayList( PathItem father ) {
    if( father != null && father.father != null && father.father.father != null )
      createWayList( father.father );

    if( father != null)
      way_list.add( father.number );
  }

  /*private void printStatuMatrix() {
    for( int i = 0; i < size.y; i++ ) {
    for( int j = 0; j < size.x; j++ )
    System.out.print( matrix[i][j].status );

    System.out.println();
    }
    }*/
}
