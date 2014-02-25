package com.modules.castle;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.game.Assets;
import com.modules.battle.UnitIcon;
import com.modules.castle.BuyUnitIcon;
import com.modules.map.MapConstants;
import com.modules.map.MapController;

/**
 * Page of Castle interface
 * This page allows the owner of castle to buy units
 */
public class UnitsPage extends Group {

	final int N_UNITS = 5;
	final int UI_SPACE_W = UnitIcon.SIZE_W + 16; // space between two buildings squares of castle units
	final int PUNITS_X = 200;
	final int ROW2_Y = 80;
	final int ROW1_Y = 200;

	BuyUnitIcon[] castle_units_icons;	// units that player can buy in the castle
	BuyAllButton[] buy_all_buttons; 	// buttons for buy all units of each type

	List<TopCastleUnit> castle_units;

	public UnitsPage( List<TopCastleUnit> castle_units ) {
		castle_units_icons = new BuyUnitIcon[N_UNITS];
		buy_all_buttons = new BuyAllButton[N_UNITS];

		this.castle_units = castle_units;

		loadCastleUnits();
		loadBuyButtons();
	}

	/**
	 * Load castle units icons and add action for show units panel
	 */
	private void loadCastleUnits() {
		for( int i = 0; i < N_UNITS; i++ ) {
			this.castle_units_icons[i] = new BuyUnitIcon( PUNITS_X + UI_SPACE_W * i, ROW1_Y );
			addActor( this.castle_units_icons[i] );
		}

		for( int i = 0; i < castle_units.size(); i++ ) {
			castle_units_icons[i].addUnit( castle_units.get(i) );

			if( castle_units.get(i).available ) {
				castle_units_icons[i].setNumber( castle_units.get(i).getNumberUnits() );
				castle_units_icons[i].showNumberLabel();
			}
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

	public void updateLabelsPrice() {
		for( int i = 0; i < N_UNITS; i++  ) {
			buy_all_buttons[i].updatePrice( castle_units.get(i).getAvailableTotalPrice() );
		}
	}

	/**
	 * Add button for buy all units of each type
	 */
	public void loadBuyButtons() {
		for( int i = 0; i < N_UNITS; i++ ) {
			buy_all_buttons[i] = new BuyAllButton(
				castle_units.get(i).getAvailableTotalPrice() );

			buy_all_buttons[i].x = PUNITS_X + UI_SPACE_W * i;
			buy_all_buttons[i].y = ROW2_Y;

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
			if( castle_unit.number_units > 0 ) {
				buy_all_buttons[count].setClickAction( new ClickListener() {
					public void click(Actor actor, float x, float y) {
						Assets.playSound( "buy", false );
						castle_unit.buyAll();
						MapController.addEvent( MapConstants.BUY_UNITS, null );
					}
				});
			};

			count++;
		}
	}
}
