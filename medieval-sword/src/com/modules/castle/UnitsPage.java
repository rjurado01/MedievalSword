package com.modules.castle;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.modules.castle.BuyUnitIcon;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.game.Assets;

/**
 * Page of Castle interface
 * The page of units allows the player to buy units
 */
public class UnitsPage extends Group {

	final int N_UNITS = 5;
	final int BX_SIZE = 50; // space between two buildings squares of castle units

	BuyUnitIcon[] castle_units_icons;	// units that player can buy in the castle
	Button[] buy_all_buttons; 	 		// buttons for buy all units of each type

	public UnitsPage( List<TopCastleUnit> castle_units ) {
		castle_units_icons = new BuyUnitIcon[N_UNITS];
		buy_all_buttons = new Button[N_UNITS];

		loadCastleUnits( castle_units );
		loadBuyButtons( castle_units );
	}

	/**
	 * Load castle units icons and add action for show units panel
	 * @param units castle units list
	 */
	private void loadCastleUnits( List<TopCastleUnit> units ) {
		for( int i = 0; i < N_UNITS; i++ ) {
			this.castle_units_icons[i] = new BuyUnitIcon( 50 + BX_SIZE * i, 80 );
			addActor( this.castle_units_icons[i] );
		}

		for( int i = 0; i < units.size(); i++ ) {
			castle_units_icons[i].addUnit( units.get(i) );

			if( units.get(i).available )
				castle_units_icons[i].setUnits( units.get(i).getNumberUnits() );
		}
	}

	/**
	 * Updates number of each type of unit available to buy
	 */
	public void updateCastleUnits() {
		for( BuyUnitIcon unit_icon : castle_units_icons )
			if( unit_icon.empty() == false )
				unit_icon.update();
	}

	/**
	 * Add button for buy all units of each type
	 * @param castle_units list of castle units
	 */
	public void loadBuyButtons( List<TopCastleUnit> castle_units ) {
		for( int i = 0; i < N_UNITS; i++ ) {
			buy_all_buttons[i] = new Button(
					Assets.getTextureRegion("stats"),
					Assets.getTextureRegion("number") );

			buy_all_buttons[i].x = 50 + BX_SIZE * i;
			buy_all_buttons[i].y = 50;
			buy_all_buttons[i].width = 40;
			buy_all_buttons[i].height = 25;

			addActor( buy_all_buttons[i] );
		}

		setBuyButtonsActions( castle_units );
	}

	/**
	 * Add action to each buy all button
	 * @param castle_units list of castle units
	 */
	public void setBuyButtonsActions( List<TopCastleUnit> castle_units ) {
		int count = 0;

		for( final TopCastleUnit castle_unit : castle_units ) {
			buy_all_buttons[count].setClickListener( new ClickListener() {

				public void click(Actor actor, float x, float y) {
					if( castle_unit.number_units > 0 ) {
						castle_unit.buyAll();
						MapController.addEvent( MapConstants.BUY_UNITS, null );
					}
				}
			});

			count++;
		}
	}
}
