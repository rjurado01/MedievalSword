package com.modules.castle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;
import com.game.Constants;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.modules.map.ui.ResourceIndicator;
import com.modules.map.ui.UiLabel;

/**
 * Shows a panel when user clicks in a unit icon from units page
 * It show information about the unit and allows the player to
 * recruit units of this type.
 */
public class UnitPanel extends Group {
	final int SIZE_W = 394;
	final int SIZE_H = 580;
	final int ROW_1_Y = SIZE_H - 180;
	final int ROW_2_Y = ROW_1_Y - 150;
	final int ROW_3_Y = ROW_2_Y - 100;
	final int COL_1_X = 50;
	final int COL_2_X = 200;
	final int SPACE_INDICATOR_Y = 40;

	Image background;
	Image castle_icon;
	Image picture_back;
	Image picture;
	Label enable_description;

	Image bar_steps [];

	Button accept_button;
	Button cancel_button;
	Image disable_accept_button;

	Button add_unit;
	Button remove_unit;

	ResourceIndicator units_label;
	ResourceIndicator price_label;

	int n_units = 0;

	TopCastleUnit tc_unit;

	public UnitPanel( TopCastleUnit unit ) {
		this.tc_unit = unit;

		width = SIZE_W;
		height = SIZE_H;
		this.x = ( Constants.SIZE_W - width ) / 2 + MapConstants.HUD_WIDTH / 2;
		this.y = ( Constants.SIZE_H - height ) / 2;

		loadBackground();
		loadPicture();
		loadName();
		loadButtons();
		createAttributes();

		if( tc_unit.available )
			createBuyControls();
		else
			createEnableDescription();
	}

	private void loadBackground() {
		background = new Image( Assets.getTextureRegion("backgroundArmy") );
		background.width = width;
		background.height = height;

		castle_icon = new Image( Assets.getTextureRegion("iconCastle") );

		castle_icon.x = COL_2_X + (ResourceIndicator.SIZE_W - CastlePanel.BUTTONS_SIZE) / 2;
		castle_icon.y = ROW_1_Y + 50;
		castle_icon.width = CastlePanel.BUTTONS_SIZE;
		castle_icon.height = CastlePanel.BUTTONS_SIZE;

		addActor( background );
		addActor( castle_icon );
	}

	private void loadPicture() {
		picture_back = new Image( Assets.getTextureRegion("rect") );
		picture_back.width = 100;
		picture_back.height = 132;
		picture_back.x = COL_1_X;
		picture_back.y = ROW_1_Y;

		picture = new Image(  tc_unit.unit.getIcon( tc_unit.castle.owner.color ) );
		picture.width = 70;
		picture.height = 70;
		picture.x = picture_back.x + ( picture_back.width - picture.width ) / 2;
		picture.y = picture_back.y + ( picture_back.height - picture.height ) / 2;

		addActor( picture_back );
		addActor( picture );
	}

	private void loadName() {
		UiLabel name = new UiLabel( COL_2_X, ROW_1_Y );
		name.updateText( tc_unit.unit.getName() );
		addActor( name );
	}

	private void createAttributes() {
		addProperty( Assets.getTextureRegion( "iconLife" ),
				tc_unit.getUnit().getLife(),
				new Vector2( COL_1_X, ROW_2_Y + SPACE_INDICATOR_Y * 2 ) );

		addProperty( Assets.getTextureRegion( "iconPrice" ),
				tc_unit.getUnit().getPrice(),
				new Vector2( COL_2_X, ROW_2_Y + SPACE_INDICATOR_Y * 2 ) );

		addProperty( Assets.getTextureRegion( "iconDamage" ),
				tc_unit.getUnit().getDamage(),
				new Vector2( COL_1_X, ROW_2_Y + SPACE_INDICATOR_Y ) );

		addProperty( Assets.getTextureRegion( "iconDefense" ),
				tc_unit.getUnit().getDefense(),
				new Vector2( COL_2_X, ROW_2_Y + SPACE_INDICATOR_Y ) );

		addProperty( Assets.getTextureRegion( "iconMobility" ),
				tc_unit.getUnit().getMobility(),
				new Vector2( COL_1_X, ROW_2_Y ) );

		addProperty( Assets.getTextureRegion( "iconRange" ),
				tc_unit.getUnit().getRange(),
				new Vector2( COL_2_X, ROW_2_Y ) );
	}

	private void createEnableDescription() {
		String text = tc_unit.getEnableDescription();
		enable_description = new Label( text, Assets.skin );
		enable_description.x = ( SIZE_W - text.length() * Constants.FONT1_WIDTH ) / 2;
		enable_description.y = ROW_2_Y - 75;

		addActor( enable_description );
	}

	private void createBuyControls() {
		add_unit = new Button(
				Assets.getFrame("btnAdd", 1),
				Assets.getFrame("btnAdd", 2) );

		add_unit.width = 64;
		add_unit.height = 64;
		add_unit.x = COL_2_X + 80;
		add_unit.y = ROW_3_Y + 5;

		add_unit.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				if( tc_unit.canBuy( n_units + 1 ) ) {
					n_units += 1;
					updateInfo();
				}
			}
		});

		remove_unit = new Button(
				Assets.getFrame("btnRemove", 1),
				Assets.getFrame("btnRemove", 2) );

		remove_unit.width = 64;
		remove_unit.height = 64;
		remove_unit.x = COL_1_X;
		remove_unit.y = ROW_3_Y + 5;

		remove_unit.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				if( n_units > 0 ) {
					n_units -= 1;
					updateInfo();
				}
			}
		});

		addActor( add_unit );
		addActor( remove_unit );

		int labels_x = ( SIZE_W - ResourceIndicator.SIZE_W ) / 2;

		units_label = new ResourceIndicator( labels_x, ROW_3_Y + 40 );
		units_label.setIcon( Assets.getTextureRegion( "iconUnits" ) );
		units_label.updateText( n_units + " / "  + tc_unit.number_units );

		price_label = new ResourceIndicator( labels_x, ROW_3_Y );
		price_label.setIcon( Assets.getTextureRegion( "iconPrice" ) );
		price_label.updateText( n_units * tc_unit.unit.getPrice() );

		addActor( units_label );
		addActor( price_label );
	}

	private void loadButtons() {
		accept_button = new Button(
				Assets.getFrame("btnBuy", 1),
				Assets.getFrame("btnBuy", 2) );
		cancel_button = new Button(
				Assets.getFrame( "btnExit", 1 ),
				Assets.getFrame( "btnExit", 2 ) );

		accept_button.height = CastlePanel.BUTTONS_SIZE;
		accept_button.width = CastlePanel.BUTTONS_SIZE;
		accept_button.x = 120;
		accept_button.y = 50;

		disable_accept_button = new Image( Assets.getFrame("btnBuy", 3) );
		disable_accept_button.height = CastlePanel.BUTTONS_SIZE;
		disable_accept_button.width = CastlePanel.BUTTONS_SIZE;
		disable_accept_button.x = 120;
		disable_accept_button.y = 50;

		if( tc_unit.available ) {
			accept_button.setClickListener( new ClickListener() {
				public void click(Actor actor, float x, float y) {
					if( n_units > 0 ) {
						tc_unit.buy( n_units );
						MapController.addEvent( MapConstants.BUY_UNITS, null );
					}
				}
			});
		}

		cancel_button.height = CastlePanel.BUTTONS_SIZE;
		cancel_button.width = CastlePanel.BUTTONS_SIZE;
		cancel_button.x = 200;
		cancel_button.y = 50;

		cancel_button.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				MapController.addEvent( MapConstants.CLOSE_UNIT, null );
			}
		});

		addActor( accept_button );
		addActor( cancel_button );
		addActor( disable_accept_button );
	}

	private void addProperty( TextureRegion icon, int value, Vector2 position ) {
		ResourceIndicator indicator = new ResourceIndicator( position );
		indicator.setIcon( icon );
		indicator.updateText( value );

		addActor( indicator );
	}

	private void updateInfo() {
		units_label.updateText( n_units + " / "  + tc_unit.number_units );
		price_label.updateText(  n_units * tc_unit.unit.getPrice() );

		if( n_units > 0 )
			disable_accept_button.visible = false;
		else
			disable_accept_button.visible = true;
	}
}
