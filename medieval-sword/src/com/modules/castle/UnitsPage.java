package com.modules.castle;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.modules.battle.UnitIcon;
import com.modules.castle.BuyUnitIcon;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.game.Assets;
import com.game.Constants;

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
	Button[] buy_all_buttons; 	 		// buttons for buy all units of each type
	Label[] buy_all_prices;             // price of each unit type group

	List<TopCastleUnit> castle_units;

	public UnitsPage( List<TopCastleUnit> castle_units ) {
		castle_units_icons = new BuyUnitIcon[N_UNITS];
		buy_all_buttons = new Button[N_UNITS];
		buy_all_prices = new Label[N_UNITS];

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
			if( i < castle_units.size() && castle_units.get(i) != null ) {
				buy_all_prices[i].setText(
					Integer.toString( castle_units.get(i).getAvailableTotalPrice() ) );
			}
		}
	}

	/**
	 * Add button for buy all units of each type
	 */
	public void loadBuyButtons() {
		for( int i = 0; i < N_UNITS; i++ ) {
			buy_all_buttons[i] = new Button(
					Assets.getFrame("btnBuyUnits", 1),
					Assets.getFrame("btnBuyUnits", 2) );

			buy_all_buttons[i].x = PUNITS_X + UI_SPACE_W * i;
			buy_all_buttons[i].y = ROW2_Y;
			buy_all_buttons[i].width = UnitIcon.SIZE_W;
			buy_all_buttons[i].height = 88;

			addActor( buy_all_buttons[i] );

			// adds label with price of units that player can buy
			if( i < castle_units.size() && castle_units.get(i) != null ) {
				String price = Integer.toString(
					castle_units.get(i).getAvailableTotalPrice() );

				float x_off = price.length() * Constants.FONT1_WIDTH;

				Image icon = new Image( Assets.getTextureRegion( "iconPrice" ) );
				icon.x = buy_all_buttons[i].x + ( UnitIcon.SIZE_W - 45 - x_off ) / 2;
				icon.y = buy_all_buttons[i].y + 1;
				icon.width = 32;
				icon.height = 32;

				buy_all_prices[i] = new Label( price, Assets.font2 );
				buy_all_prices[i].x = icon.x + 40;
				buy_all_prices[i].y = buy_all_buttons[i].y + 6f;

				addActor( icon );
				addActor( buy_all_prices[i] );
			}
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
				buy_all_buttons[count].setClickListener( new ClickListener() {

					public void click(Actor actor, float x, float y) {
						castle_unit.buyAll();
						MapController.addEvent( MapConstants.BUY_UNITS, null );
					}
				});
			};

			count++;
		}
	}
}
