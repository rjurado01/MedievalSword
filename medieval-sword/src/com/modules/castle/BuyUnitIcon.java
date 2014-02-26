package com.modules.castle;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.game.Assets;
import com.modules.battle.UnitIcon;
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

		background.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) { clicked(); }
		});
	}

	protected void clicked() {
		if( tc_unit != null ) {
			Assets.playSound( "button", false );
			MapController.addEvent( MapConstants.SHOW_UNIT, tc_unit );
		}
	}

	public void addUnit( TopCastleUnit unit ) {
		this.tc_unit = unit;
		addIcon( tc_unit.unit.getIcon( tc_unit.castle.owner.color ) );
	}

	public boolean empty() {
		return tc_unit == null;
	}

	public void update() {
		setNumber( tc_unit.number_units );
	}
}
