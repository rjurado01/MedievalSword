package com.me.modules.battle;

import java.util.Hashtable;

import com.me.mygdxgame.Assets;
import com.me.mygdxgame.Object;
import com.me.utils.Vector2i;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Represents a square on the board. 
 * It is used for calculate way using A* algorithm
 */
public class SquareBoard extends Image {
	
	/** Square size */
	static final public float SIZE_W = 35.0f;
	static final public float SIZE_H = 42.0f;
	
	/** Square STATUS */
	static final int FREE = 0;
	static final int UNIT_P1 = 1;
	static final int UNIT_P2 = 2;
	static final int SELECTED_UNIT = 4;
	
	/** Square TEXTURES */
	static final String T_NORMAL 		= "normalCell";
	static final String T_AVAILABLE 	= "availableCell";
	static final String T_SELECTED_UNIT = "unitCell";
	static final String T_ENEMY 		= "atackCell";
	
	String texture_name = T_NORMAL;
	String texture_status = null;
	
	Vector2i number;
	
	int status = 0;		// represent square status
	
	/** A* variables */
	int F, G, H;
	boolean visited = false;
	SquareBoard father;

	/**
	 * Class constructor
	 * @param pos
	 * @param x
	 * @param y
	 * @param father
	 */
	public SquareBoard(Vector2 pos, Vector2i number ) {
		super();
		
		this.number = number;
		this.x = pos.x;
		this.y = pos.y;
		this.width = SIZE_W;
		this.height = SIZE_H;
		this.color.a = 0.6f;
		
		setRegion( Assets.getTextureRegion( texture_name ) );
		
		setClickListener( new ClickListener() {
			
			@Override
			public void click(Actor actor, float x, float y) {
				clickSquare();
			}
		});
	}
	
	/**
	 * Add to controller information about event for process it
	 */
	public void clickSquare() {
		BattleController.addEvent( BattleController.SQUARE, this );
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
	}
	
	/**
	 * Change status to UNIT ( P1ayer1 or Player2 )
	 * @param type
	 */
	public void setUnit( int unit ) {
		if( unit == UNIT_P1 || unit == UNIT_P2)
			status = unit;
	}
	
	public void setStatus(int st) {
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
		setRegion( Assets.getTextureRegion( T_AVAILABLE ) );
	}
	
	public void setNormalOn() {
		this.texture_status = T_NORMAL;	
		setRegion( Assets.getTextureRegion( T_NORMAL ) );
	}
	
	public void setSelectedUnitOn() {
		this.texture_status = T_SELECTED_UNIT;
		setRegion( Assets.getTextureRegion( T_SELECTED_UNIT ) );
	}
	
	public void setEnemyOn( int player ) {
		if( status == UNIT_P1 && player == UNIT_P2 ) {
			this.texture_status = T_ENEMY;			
			setRegion( Assets.getTextureRegion( T_ENEMY ) );
		}
		else if( status == UNIT_P2 && player == UNIT_P1 ) {
			this.texture_status = T_ENEMY;			
			setRegion( Assets.getTextureRegion( T_ENEMY ) );
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
	
	public Vector2 getPosition() {
		return new Vector2( x, y );
	}
	
	/**
	 * Check if contains an unit
	 * @return true if contains an unit and false otherwhise
	 */
	public boolean hasUnit() {
		if( status == UNIT_P1 || status == UNIT_P2 )
			return true;
		else
			return false;
	}
	
	/**
	 * Check if there is an enemy in this square
	 * @param player id player units
	 * @return true if there is an enemy in this square and false otherwise
	 */
	public boolean hasEnemy( int player ) {
		if( player == UNIT_P1 && status == UNIT_P2 )
			return true;
		else if( player == UNIT_P2 && status == UNIT_P1 )
			return true;
		else
			return false;
	}
	
	/**
	 * Check if this square has enemy that can be attacked by current selected unit
	 * @return true if has enemy on and false otherwise
	 */
	public boolean hasEnemyOn() {
		if( texture_status == T_ENEMY )
			return true;
		else
			return false;
	}
	
	/**
	 * Check if contains selected unit
	 * @return true if contains selected unit and false otherwhise
	 */
	public boolean isSelectedUnit() {
		if( status == SELECTED_UNIT ) 
			return true;
		else
			return false;
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

	/**
	 * Calculate H and F (see A* algorithm)
	 * @param xf x coordinate of the target cell
	 * @param yf y coordinate of the target cell
	 */
	public void calculate(int xf, int yf) {
		H = (Math.abs(xf - number.x) + Math.abs(yf - number.y)) * 10;
		F = H + G;
	}
	
	/**
	 * Reset all A* variables for new search
	 */
	public void reset() {
		G = 0;
		F = 0;
		H = 0;
		
		father = null;
		visited = false;
		
		texture_status = T_NORMAL;
		setRegion( Assets.getTextureRegion( T_NORMAL ) );
	}

	public void setTexture( String texture ) {
		setRegion( Assets.getTextureRegion( texture ) );
	}
}
