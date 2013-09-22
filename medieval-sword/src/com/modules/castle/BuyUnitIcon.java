package com.modules.castle;

import com.modules.map.MapConstants;
import com.modules.map.MapController;

/**
 * Represents each unit of the castle.
 * It allows player to recruit units of this type.
 */
public class BuyUnitIcon extends UnitIcon {

	TopCastleUnit tc_unit;

	public BuyUnitIcon( float x_position, float y_position ) {
		super( x_position, y_position );
	}

	protected void clicked() {
		if( tc_unit != null )
			MapController.addEvent( MapConstants.SHOW_UNIT, tc_unit );
	}

	public void addUnit( TopCastleUnit unit ) {
		this.tc_unit = unit;
		updateIcon( tc_unit.unit.getIcon() );
	}

	public boolean empty() {
		return tc_unit == null;
	}

	public void update() {
		updateNumber( tc_unit.number_units );
	}
}
