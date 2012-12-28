package com.me.mygdxgame;

import com.badlogic.gdx.utils.Array;
import com.me.modules.battle.BattleController;
import com.me.modules.battle.Board;
import com.me.modules.battle.SquareBoard;
import com.me.units.Archer;
import com.me.units.Villager;

/**
 * This class represent each player and all this features.
 * Contain information about all his resources, units, experience, level...
 */
public class Player {

	int gold;
	
	int level;
	
	private Array<Unit> units;	// player units
	private Unit selected;		// unit selected
	
	boolean battle_side;		// represent 
	
	int units_id = 0;
	
	/** 
	 * Class constructor.
	 */
	public Player(Board board, int units_id ) {
		gold = 0;
		level = 0;
		
		units = new Array<Unit>();
		units.add( new Villager( 1, units_id ) );
		units.add( new Archer( 1, units_id ) );
		
		selected = units.get(0);
		
		this.units_id = units_id;
	}

	/**
	 * Update selected unit to next unit 
	 */
	public void nexUnit() {
		int aux = units.indexOf(selected, true);

		if(aux == units.size - 1)
			selected = units.get(0);
		else
			selected = units.get(aux + 1);
	}
	
	/**
	 * Select last unit
	 */
	public void selectLastUnit() {
		selected = units.get( units.size - 1 );
	}
	
	/**
	 * Get unit of player that is in specific square
	 * @param square
	 * @return unit
	 */
	public Unit getUnitFromSquare( SquareBoard square ) {
		for(int i=0; i<units.size; i++)
			if( units.get(i).getSquare() == square )
				return units.get(i);
		
		return null;
	}
	
	/**
	 * Remove unit from player
	 * @param unit
	 */
	public void deleteUnit(Unit unit) {
		units.removeValue( unit, true );
	}
	
	/**
	 * Update unit
	 * @param time current time
	 */
	public void update( float time ) {
		for( Unit unit : units ) {
			unit.update( time );
		}
	}
	
	/**
	 * Set player battle side ( right / left )
	 * Depending of side, set units textures.
	 * 
	 * @param side battle side
	 */
	public void setBattleSide( boolean side ) {
		this.battle_side = side;
		
		// Update texture of units
		if( battle_side == BattleController.RIGHT ) {
			for( Unit u : units) {
				u.setOrientation( Unit.XL );
			}
		}
		else {
			for( Unit u: units ) {
				u.setOrientation( Unit.XR );
			}
		}
	}
	
	/**
	 * @return player units
	 */
	public Array<Unit> getUnits() {
		return units;
	}
	
	/**
	 * @return player selected unit
	 */
	public Unit getSelectedUnit() {
		return selected;
	}
	
	/**
	 * @return player side in actual battle
	 */
	public boolean getBattleSide() {
		return battle_side;
	}
	
	/**
	 * Set units id for player ( all his units have this id in board )
	 * @param id
	 */
	public void setUnitsId( int id ) {
		this.units_id = id;
	}
	
	/**
	 * @return player units id
	 */
	public int getUnitsId() {
		return units_id;
	}
}
