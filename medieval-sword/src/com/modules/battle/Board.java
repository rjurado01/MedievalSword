package com.modules.battle;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Stack;
import com.utils.Vector2i;

/**
 * Represent the board in the battle.
 * It is composed by NS_Y * NS_X squares.
 */
public class Board
{
  final String back_texture = "block-stone";

  SquareBoard matrix [][];
  Vector2 position;

  List<SquareBoard> squares_list = new ArrayList<SquareBoard>();

  Stage stage;
  Image background;
  BoardPathFinding path_finding;

  public Board( Stage stage ) {
    this.stage = stage;

    position = new Vector2(
        BattleConstants.MARGIN_W,
        ( BattleConstants.NS_Y - 2 ) *
        (BattleConstants.SQUARE_SIZE_H + BattleConstants.SQUARE_SIZE_3D ) );

    createBackgroundTop();
    createBackgroundSides();
    createMatrixSquares();
    createBackgroundBottom();

    path_finding = new BoardPathFinding( matrix );
  }

  private void createBackgroundTop() {
    for( int i=0; i < BattleConstants.NS_X + 2; i++ ) {
      Image sq_top = new Image( Assets.getTextureRegion( back_texture ) );
      sq_top.width = BattleConstants.SQUARE_SIZE_W;
      sq_top.height = BattleConstants.SQUARE_SIZE_H + BattleConstants.SQUARE_SIZE_3D;

      sq_top.x = position.x - sq_top.width + sq_top.width * i;
      sq_top.y = position.y + BattleConstants.SQUARE_SIZE_H;

      stage.addActor( sq_top );
    }

  }

  private void createBackgroundBottom() {
    for( int i=0; i < BattleConstants.NS_X + 2; i++ ) {
      Image sq_bottom = new Image( Assets.getTextureRegion( back_texture ) );
      sq_bottom.width = BattleConstants.SQUARE_SIZE_W;
      sq_bottom.height = BattleConstants.SQUARE_SIZE_H + BattleConstants.SQUARE_SIZE_3D;

      sq_bottom.x = position.x - sq_bottom.width + sq_bottom.width * i;
      sq_bottom.y = position.y - BattleConstants.SQUARE_SIZE_H * BattleConstants.NS_Y;

      stage.addActor( sq_bottom );
    }

  }

  private void createBackgroundSides() {
    for( int i=0; i < BattleConstants.NS_Y; i++ ) {
      Image sq_side1 = new Image( Assets.getTextureRegion( back_texture ) );
      sq_side1.width = BattleConstants.SQUARE_SIZE_W;
      sq_side1.height = BattleConstants.SQUARE_SIZE_H + BattleConstants.SQUARE_SIZE_3D;

      Image sq_side2 = new Image( Assets.getTextureRegion( back_texture ) );
      sq_side2.width = BattleConstants.SQUARE_SIZE_W;
      sq_side2.height = BattleConstants.SQUARE_SIZE_H + BattleConstants.SQUARE_SIZE_3D;

      sq_side1.x = position.x - BattleConstants.SQUARE_SIZE_W;
      sq_side1.y = position.y - BattleConstants.SQUARE_SIZE_H * i;

      sq_side2.x = position.x + BattleConstants.SQUARE_SIZE_W * BattleConstants.NS_X;
      sq_side2.y = position.y - BattleConstants.SQUARE_SIZE_H * i;

      stage.addActor( sq_side1 );
      stage.addActor( sq_side2 );
    }

  }

  private void createMatrixSquares() {
    matrix = new SquareBoard[BattleConstants.NS_Y][];
    for(int i=0;i<BattleConstants.NS_Y;i++)
      matrix[i] = new SquareBoard[BattleConstants.NS_X];

    for(int i=0;i<BattleConstants.NS_Y;i++)
      for(int j=0;j<BattleConstants.NS_X;j++)
        createMatrixSquare( j, i );
  }

  private void createMatrixSquare( int x, int y ) {
    matrix[y][x] = new SquareBoard(
        position,
        new Vector2i( x, y ) );

    squares_list.add(matrix[y][x]);
    stage.addActor( matrix[y][x] );
  }

  public Vector2 getSquarePosition( int x_square_number, int y_square_number ) {
    if( !isValidSquareNumber( x_square_number, y_square_number ) )
      return null;

    return new Vector2( x_square_number * BattleConstants.SQUARE_SIZE_W,
        y_square_number * BattleConstants.SQUARE_SIZE_H);
  }

  private boolean isValidSquareNumber( int ns_x, int ns_y ) {
    if( ns_x < BattleConstants.NS_X && ns_x >= 0 &&
        ns_y < BattleConstants.NS_Y && ns_y >= 0)
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
    int square_number_x = (int)(x / BattleConstants.SQUARE_SIZE_W);
    int square_number_y = (int)(y / BattleConstants.SQUARE_SIZE_H);

    if( square_number_x < BattleConstants.NS_X &&
        square_number_y < BattleConstants.NS_Y )
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
    for( int y = 0; y < BattleConstants.NS_Y; y++ )
      for( int x = 0; x < BattleConstants.NS_X; x++ ) {
        if( isAchievable( stack.getSquare(), matrix[y][x], stack.getMovility() )) {
          if( matrix[y][x].isFree() )
            matrix[y][x].setAvailableOn();
          else if( matrix[y][x].hasEnemy( battle_side ) )
            matrix[y][x].setEnemyOn( battle_side );
        }
      }
  }

  public void deleteUnreachableSquares( Stack stack ) {
    for( int y = 0; y < BattleConstants.NS_Y; y++ )
      for( int x = 0; x < BattleConstants.NS_X; x++ ) {
        if( getSquare(x, y).isAvailable() && !path_finding.checkWay(
              stack.getSquare(), getSquare(x, y), stack.getMovility() ) )
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
    if( number.y < BattleConstants.NS_Y &&
        number.y >= 0 && number.x < BattleConstants.NS_X && number.x >= 0 )
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
    for(int i=0;i<BattleConstants.NS_Y;i++) {
      for(int j=0;j<BattleConstants.NS_X;j++) {
        matrix[i][j].reset();
      }
    }
  }

  public void printMatrixStatus() {
    for(int i=0;i<BattleConstants.NS_Y;i++) {
      for(int j=0;j<BattleConstants.NS_X;j++) {
        System.out.print( "[ " + matrix[i][j].status + " ]" );
      }
      System.out.println();
    }

    System.out.println();
  }

  public void printMatrixTextureStatus() {
    for(int i=0;i<BattleConstants.NS_Y;i++) {
      for(int j=0;j<BattleConstants.NS_X;j++) {
        System.out.print( "[ " + matrix[i][j].texture_status + " ]" );
      }
      System.out.println();
    }

    System.out.println();
  }

  public void showAttackPositions( Vector2i enemy, Vector2i me ) {
    for( int y = 0; y < BattleConstants.NS_Y; y++ )
      for( int x = 0; x < BattleConstants.NS_X; x++ )
        if( matrix[y][x].isAvailable() && Math.abs( y - enemy.y ) + Math.abs( x - enemy.x ) > 1 )
          matrix[y][x].setTexture( null );

    if( Math.abs( enemy.y - me.y ) + Math.abs( enemy.x - me.x ) == 1 )
      matrix[me.y][me.x].setAvailableOn();
  }

  /**
   * Set all enemy squares available for attack
   * @param player units of player id
   */
  public void setAllEnemyOn( int player ) {
    for( int y = 0; y < BattleConstants.NS_Y; y++ )
      for( int x = 0; x < BattleConstants.NS_X; x++ )
        matrix[y][x].setEnemyOn( player );
  }

  public void resetAvailableSquares() {
    for( int y = 0; y < BattleConstants.NS_Y; y++ )
      for( int x = 0; x < BattleConstants.NS_X; x++ )
        if( matrix[y][x].isAvailable() )
          matrix[y][x].setAvailableOn();

  }

  public SquareBoard getNextSquare( int battle_side, SquareBoard unit_square ) {
    List<SquareBoard> enemies_squares = new ArrayList<SquareBoard>();
    SquareBoard new_square = null;
    float min = 20;

    // get all enemies squares
    for( int y = 0; y < BattleConstants.NS_Y; y++ )
      for( int x = 0; x < BattleConstants.NS_X; x++ )
        if( matrix[y][x].hasEnemy( battle_side ) )
          enemies_squares.add( matrix[y][x] );

    for( int y = 0; y < BattleConstants.NS_Y; y++ )
      for( int x = 0; x < BattleConstants.NS_X; x++ )
        if( matrix[y][x].isAvailable() ) {
          if( new_square == null )
            new_square = matrix[y][x];
          else {
            for( SquareBoard enemy_square : enemies_squares ) {
              float value = Math.abs( enemy_square.getNumber().x - matrix[y][x].getNumber().x ) +
                Math.abs( enemy_square.getNumber().y - matrix[y][x].getNumber().y );

              if( value < min ) {
                min = value;
                new_square = matrix[y][x];
              }
            }
          }
        }

    return new_square;
  }

  public SquareBoard nearEnemySquare( int battle_side, SquareBoard unit_square ) {
    SquareBoard new_square = null;
    float min = 20;

    for( int y = 0; y < BattleConstants.NS_Y; y++ )
      for( int x = 0; x < BattleConstants.NS_X; x++ )
        if( matrix[y][x].hasEnemyOn() ) {
          float value = Math.abs( unit_square.getNumber().x - matrix[y][x].getNumber().x ) +
            Math.abs( unit_square.getNumber().y - matrix[y][x].getNumber().y );

          if( value < min ) {
            min = value;
            new_square = matrix[y][x];
          }
        }

    return new_square;
  }

  public SquareBoard nearEnemySquareAvailable( int battle_side, SquareBoard unit_square ) {
    SquareBoard new_square = null;
    float min = 20;

    for( int y = 0; y < BattleConstants.NS_Y; y++ )
      for( int x = 0; x < BattleConstants.NS_X; x++ )
        if( matrix[y][x].isAvailable() && matrix[y][x].hasEnemy( battle_side ) ) {
          float value = Math.abs( unit_square.getNumber().x - matrix[y][x].getNumber().x ) +
            Math.abs( unit_square.getNumber().y - matrix[y][x].getNumber().y );

          if( value < min ) {
            min = value;
            new_square = matrix[y][x];
          }
        }

    return new_square;
  }

  public SquareBoard nearEnemySquareAttack( int battle_side, SquareBoard unit_square ) {
    SquareBoard new_square = null;
    float min = 20;

    for( int y = 0; y < BattleConstants.NS_Y; y++ )
      for( int x = 0; x < BattleConstants.NS_X; x++ )
        if( matrix[y][x].isAvailableForAttack() ) {
          float value = Math.abs( unit_square.getNumber().x - matrix[y][x].getNumber().x ) +
            Math.abs( unit_square.getNumber().y - matrix[y][x].getNumber().y );

          if( value < min ) {
            min = value;
            new_square = matrix[y][x];
          }
        }

    return new_square;
  }
}
