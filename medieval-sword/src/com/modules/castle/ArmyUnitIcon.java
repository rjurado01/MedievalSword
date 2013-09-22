package com.modules.castle;

import com.game.Unit;

/**
 * Group that show unit image and the number of units of this type in an army.
 * It is used in the "HomePage" and "UnitsPage" of Castle interface.
 */
public class ArmyUnitIcon extends UnitIcon {

	Unit unit;

	public ArmyUnitIcon( float x_position, float y_position ) {
		super( x_position, y_position );
	}

	protected void clicked() {
		//if( unit != null )
		//	MapController.addEvent( MapConstants.SHOW_UNIT, unit );
	}

	public void addUnit( Unit unit ) {
		this.unit = unit;
		updateIcon( unit.getIcon() );
	}

	public void removeUnit() {
		removeIcon();
		removeNumberLabel();

		unit = null;
	}

	public boolean empty() {
		return unit == null;
	}
}
