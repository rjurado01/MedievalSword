package com.me.mygdxgame;

import com.badlogic.gdx.utils.Array;
import com.me.modules.battle.Board;
import com.me.modules.battle.SquareBoard;
import com.me.modules.battle.Unit1;

public class Player {
	// resources
	int gold;
	
	int level;
	
	private Array<Unit> units;	// player units
	private Unit selected;		// unit selected
	
	boolean inverse;
	
	int units_id = 0;
	
	/** 
	 * Class constructor.
	 */
	public Player(Board board) {
		gold = 0;
		level = 0;
		
		units = new Array<Unit>();
		units.add( new Unit1( 12 ) );
		units.add( new Unit1( 4 ) );
		
		selected = units.get(0);
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
	
	public Unit getUnitFromSquare(SquareBoard sq) {
		for(int i=0; i<units.size; i++)
			if( units.get(i).getSquare() == sq )
				return units.get(i);
		
		return null;
	}
	
	public void setInverse( boolean inverse ) {
		this.inverse = inverse;
		
		// Update texture of units
		if( inverse == true) {
			for( Unit u : units) {
				u.setOrientation( Unit.XL );
			}
		}
	}
	
	public boolean isInverse() {
		return inverse;
	}
	
	public void setUnitsId( int id ) {
		this.units_id = id;
	}
	
	public int getUnitsId() {
		return units_id;
	}

	public void deleteUnit(Unit unit) {
		units.removeValue( unit, true );
	}
}
