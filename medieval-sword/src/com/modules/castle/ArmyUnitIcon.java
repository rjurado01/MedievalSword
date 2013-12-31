package com.modules.castle;

import com.game.Unit;
import com.modules.battle.UnitIcon;

/**
 * Group that show unit image and the number of units of this type in an army.
 * It is used in the "HomePage" and "UnitsPage" of Castle interface.
 */
public class ArmyUnitIcon extends UnitIcon {

	Unit unit;

	public ArmyUnitIcon( float x_position, float y_position ) {
		super( x_position, y_position );
	}

	public void addUnit( Unit unit, int color ) {
		this.unit = unit;
		addIcon( unit.getIcon(color) );
	}

	public void removeUnit() {
		clearBox();
		unit = null;
	}

	public boolean empty() {
		return unit == null;
	}
}
