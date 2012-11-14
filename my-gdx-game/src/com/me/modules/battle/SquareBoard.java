package com.me.modules.battle;

import java.util.Hashtable;
import com.me.mygdxgame.Object;
import com.me.utils.Vector2i;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents a square on the board. 
 * It is used for calculate way using A* algorithm
 */
public class SquareBoard extends Object{
	
	/** Square size */
	static final float SIZE_W = 40.0f;
	static final float SIZE_H = 40.0f;
	
	/** Square STATUS */
	static final int FREE = 0;
	static final int UNIT_P1 = 1;
	static final int UNIT_P2 = 2;
	static final int SELECTED_UNIT = 4;
	
	/** Square TEXTURES */
	static final String T_NORMAL = "normalCell";
	static final String T_AVAILABLE = "availableCell";
	static final String T_SELECTED_UNIT = "unitCell";
	static final String T_ENEMY = "atackCell";
	
	String texture_name = T_NORMAL;
	String texture_status = null;
	
	int status = 0;		// represent square status
	
	int x, y;	// number of square
	
	/** A* variables */
	int F, G, H;
	boolean visited = false;
	SquareBoard father;
	
	protected Hashtable<String, TextureRegion> textures; // square textures

	/**
	 * Class constructor
	 * @param pos
	 * @param x
	 * @param y
	 * @param father
	 */
	public SquareBoard(Vector2 pos, int x, int y, SquareBoard father) {
		super(pos, SIZE_W, SIZE_H);
		
		this.x = x;
		this.y = y;
	}
	
	public String getTextureName() {
		return texture_name;
	}
	
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
	public void setUnit( int type ) {
		if( type == UNIT_P1 || type == UNIT_P2)
			status = type;
	}
	
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
	
	public void setStatus(int st) {
		status = st;
	}
	
	public void setFather(SquareBoard father) {
		this.father = father;
	}
	
	public SquareBoard getFather() {
		return father;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}	
	
	public Vector2i getNumber() {
		return new Vector2i(x, y);
	}
	
	public void setNumber(int x, int y) {
		this.x = x;
		this.y = y;
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
		
		texture_status = null;
	}
	
	/**
	 * Calculate H and F (see A* algorithm)
	 * @param xf x coordinate of the target cell
	 * @param yf y coordinate of the target cell
	 */
	public void calculate(int xf, int yf) {
		H = (Math.abs(xf - x) + Math.abs(yf - y)) * 10;
		F = H + G;
	}
	
	public void setAvailableOn() {
		if( status == FREE )
			this.texture_status = T_AVAILABLE;
	}
	
	/*public void setAvaibleOff() {
		if( status == AVAILABLE )
			this.status = FREE;
	} */
	
	public boolean isAvailable() {
		if( texture_status == T_AVAILABLE ) 
			return true;
		else
			return false;
	}
	
	public boolean isSelectedUnit() {
		if( status == SELECTED_UNIT ) 
			return true;
		else
			return false;
	}
	
	public String getStatusTexture() {
		return texture_status;
	}
}
