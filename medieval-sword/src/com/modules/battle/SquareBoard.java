package com.modules.battle;

import com.utils.Vector2i;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Constants;
import com.game.Stack;

/**
 * Represents a square on the board.
 * It is used for calculate way using A* algorithm.
 */
public class SquareBoard extends Group {

	/** Square STATUS */
	static final int FREE = 0;
	public static final int STACK_P1 = 1;
	public static final int STACK_P2 = 2;

	/** Square TEXTURES */
	static final String T_GROUND        = "block-grass";
	static final String T_AVAILABLE     = "squareAvailable";
	static final String T_SELECTED_UNIT = "squareSelected";
	static final String T_ENEMY         = "squareAttack";

	String texture_status = null;

	Image ground_img;
	Image status_img;

	Vector2i number;

	int status = 0;		// represent square status

	/** A* variables */
	int F, G, H;
	boolean visited = false;
	SquareBoard father;

	Stack stack;


	public SquareBoard( Vector2 position, Vector2i number ) {
		super();

		this.number = number;
		this.x = position.x + ( number.x * BattleConstants.SQUARE_SIZE_W );
		this.y = position.y - ( number.y * BattleConstants.SQUARE_SIZE_H );

		this.width = BattleConstants.SQUARE_SIZE_W;
		this.height = BattleConstants.SQUARE_SIZE_H + BattleConstants.SQUARE_SIZE_3D;

		createGroundImg();
		createStatusImg();
	}

	private void createGroundImg() {
		ground_img = new Image( Assets.getTextureRegion( T_GROUND ) );
		ground_img.width = width;
		ground_img.height = height;

		ground_img.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) { clickSquare(); }
		});

		addActor( ground_img );
	}

	private void createStatusImg() {
		status_img = new Image();
		status_img.width = BattleConstants.SQUARE_SIZE_W;
		status_img.height = BattleConstants.SQUARE_SIZE_H;
		status_img.y = BattleConstants.SQUARE_SIZE_3D;

		addActor( status_img );
	}

	/**
	 * Add SQUARE event to controller.
	 * We need to pass this object.
	 */
	public void clickSquare() {
		BattleController.addEvent( BattleConstants.SQUARE, this );
	}

	public boolean isFree() {
		if( status == FREE )
			return true;
		else
			return false;
	}

	public void setFree() {
		status = FREE;
		texture_status = null;
		stack = null;
	}

	public void setStack( Stack stack ) {
		this.stack = stack;
		status = getStackId( stack.getBattleSide() );
	}

	public void setStatus( int st ) {
		status = st;
	}

	public Vector2i getNumber() {
		return number;
	}

	public void setNumber( Vector2i number ) {
		this.number = number;
	}

	public void setAvailableOn() {
		this.texture_status = T_AVAILABLE;
		status_img.setRegion( Assets.getTextureRegion( T_AVAILABLE ) );
	}

	public void setNormalOn() {
		this.texture_status = T_GROUND;
		status_img.setRegion( null );
	}

	public void setSelectedUnitOn() {
		this.texture_status = T_SELECTED_UNIT;
		status_img.setRegion( Assets.getTextureRegion( T_SELECTED_UNIT ) );
	}

	public void setEnemyOn( int player ) {
		if( status == STACK_P1 && player == STACK_P2 ) {
			texture_status = T_ENEMY;
			status_img.setRegion( Assets.getTextureRegion( T_ENEMY ) );
		}
		else if( status == STACK_P2 && player == STACK_P1 ) {
			texture_status = T_ENEMY;
			status_img.setRegion( Assets.getTextureRegion( T_ENEMY ) );
		}
	}

	public String getStatusTexture() {
		return texture_status;
	}

	public boolean isAvailable() {
		if( texture_status == T_AVAILABLE )
			return true;
		else
			return false;
	}

	public boolean isAvailableForAttack() {
		if( texture_status == T_AVAILABLE && hasTexture() )
			return true;
		else
			return false;
	}

	public Vector2 getPosition() {
		return new Vector2( x, y );
	}

	/**
	 * Check if this square has a stack
	 */
	public boolean hasStack() {
		if( status == STACK_P1 || status == STACK_P2 )
			return true;
		else
			return false;
	}

	/**
	 * Check if there is an enemy in this square
	 * @param stack_id id player units
	 */
	public boolean hasEnemy( int battle_side ) {
		int stack_id = getStackId( battle_side );

		if( stack_id == STACK_P1 && status == STACK_P2 )
			return true;
		else if( stack_id == STACK_P2 && status == STACK_P1 )
			return true;
		else
			return false;
	}

	/**
	 * Check if there is an ally in this square
	 * @param stack_id id player units
	 */
	public boolean hasAlly( int battle_side ) {
		int stack_id = getStackId( battle_side );

		if( stack_id == STACK_P1 && status == STACK_P1 )
			return true;
		else if( stack_id == STACK_P2 && status == STACK_P2 )
			return true;
		else
			return false;
	}

	/**
	 * Check if this square has enemy that can be attacked by current selected unit
	 */
	public boolean hasEnemyOn() {
		if( texture_status == T_ENEMY )
			return true;
		else
			return false;
	}

	public Stack getStack() {
		return stack;
	}

	public Vector2 getStackPosition() {
		return getPosition().add(
			new Vector2( BattleConstants.CORRECT_X, BattleConstants.CORRECT_Y ) );
	}

	public void setTexture( String texture ) {
		status_img.setRegion( Assets.getTextureRegion( texture ) );
	}

	public int getStackId( int battle_side ) {
		if( battle_side == Constants.LEFT_SIDE )
			return STACK_P1;
		else
			return STACK_P2;
	}

	public boolean hasTexture() {
		return status_img.getRegion() != null;
	}

	/******** A* Functions ************/

	public int getF() {
		return F;
	}

	public void setG(int g) {
		G = g;
	}

	public int getG() {
		return G;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public void setFather(SquareBoard father) {
		this.father = father;
	}

	public SquareBoard getFather() {
		return father;
	}

	public void calculateH( Vector2i number_square ) {
		H = ( Math.abs( number_square.x - number.x ) +
				Math.abs( number_square.y - number.y ) ) * 10;
	}

	public void calculateF() {
		F = H + G;
	}

	public void reset() {
		resetWayValues();

		texture_status = T_GROUND;
		status_img.setRegion( Assets.getTextureRegion( null ) );
	}

	/**
	 * Reset all A* variables for new search
	 */
	public void resetWayValues() {
		G = 0;
		F = 0;
		H = 0;

		father = null;
		visited = false;
	}
}
