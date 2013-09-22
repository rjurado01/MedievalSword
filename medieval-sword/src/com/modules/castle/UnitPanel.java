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
import com.modules.map.MapConstants;
import com.modules.map.MapController;

/**
 * Shows a panel when user clicks in a unit icon from units page
 * It show information about the unit and allows the player to
 * recruit units of this type.
 */
public class UnitPanel extends Group {
	Image background;
	Image picture_background;
	Image picture;
	Label enable_description;

	Image bar_steps [];

	Button accept_button;
	Button cancel_button;

	Button add_unit;
	Button remove_unit;
	Label info_label;

	int n_units = 0;

	TopCastleUnit tc_unit;

	public UnitPanel( TopCastleUnit unit ) {
		this.tc_unit = unit;

		width = 180;
		height = 260;
		x = 170;
		y = 30;

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
		background = new Image( Assets.getTextureRegion("menu") );
		background.x = 10;
		background.y = 10;
		background.width = width;
		background.height = height;

		addActor( background );
	}

	private void loadPicture() {
		picture_background = new Image( Assets.getTextureRegion("number") );
		picture_background.x = 30;
		picture_background.y = 190;
		picture_background.width = 60;
		picture_background.height = 60;

		picture = new Image(  tc_unit.unit.getIcon() );
		picture.x = 40;
		picture.y = 200;
		picture.width = 40;
		picture.height = 40;

		addActor( picture_background );
		addActor( picture );
	}

	private void loadName() {
		Image background = new Image( Assets.getTextureRegion("number") );
		background.width = 75;
		background.height = 15;
		background.x = 100;
		background.y = 190;

		Label name = new Label( tc_unit.unit.getName(), Assets.skin );
		name.x = 120;
		name.y = 190;

		addActor( background );
		addActor( name );
	}

	private void loadButtons() {
		accept_button = new Button(
				Assets.getTextureRegion("stats"),
				Assets.getTextureRegion("number") );
		cancel_button = new Button(
				Assets.getFrame( "exit", 1 ),
				Assets.getFrame( "exit", 2 ) );

		accept_button.height = 30;
		accept_button.width = 60;
		accept_button.x = 30;
		accept_button.y = 30;

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

		cancel_button.height = 30;
		cancel_button.width = 60;
		cancel_button.x = 110;
		cancel_button.y = 30;

		cancel_button.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				MapController.addEvent( MapConstants.CLOSE_UNIT, null );
			}
		});

		addActor( accept_button );
		addActor( cancel_button );
	}

	private void createAttributes() {
		int x1 = 30;
		int x2 = 100;
		int y = 160;

		addProperty( Assets.getTextureRegion( "goldIcon" ),
				Integer.toString( 100 ),
				new Vector2( x1, y ) );

		addProperty( Assets.getTextureRegion( "woodIcon" ),
				Integer.toString( 100 ),
				new Vector2( x2, y ) );

		addProperty( Assets.getTextureRegion( "goldIcon" ),
				Integer.toString( 100 ),
				new Vector2( x1, y - 20 ) );

		addProperty( Assets.getTextureRegion( "stoneIcon" ),
				Integer.toString( 1000 ),
				new Vector2( x2, y - 20 ) );

		addProperty( Assets.getTextureRegion( "woodIcon" ),
				Integer.toString( 100 ),
				new Vector2( x1, y - 40 ) );

		addProperty( Assets.getTextureRegion( "stoneIcon" ),
				Integer.toString( 100 ),
				new Vector2( x2, y - 40 ) );
	}

	private void createEnableDescription() {
		enable_description = new Label( Assets.skin );
		enable_description.setText( tc_unit.getEnableDescription() );
		enable_description.x = 40;
		enable_description.y = 90;

		addActor( enable_description );
	}

	private void createBuyControls() {
		add_unit = new Button( Assets.getTextureRegion("number"),
				Assets.getTextureRegion("unitCell") );
		add_unit.width = 30;
		add_unit.height = 30;
		add_unit.x = 140;
		add_unit.y = 75;

		add_unit.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				if( tc_unit.canBuy( n_units + 1 ) ) {
					n_units += 1;
					updateInfo();
				}
			}
		});

		remove_unit = new Button( Assets.getTextureRegion("number"),
				Assets.getTextureRegion("unitCell") );
		remove_unit.width = 30;
		remove_unit.height = 30;
		remove_unit.x = 30;
		remove_unit.y = 75;

		remove_unit.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				if( n_units > 0 ) {
					n_units -= 1;
					updateInfo();
				}
			}
		});

		info_label = new Label( Integer.toString( n_units) + " / " +
				Integer.toString( n_units * tc_unit.unit.getPrice() ), Assets.skin );
		info_label.x = 80;
		info_label.y = 80;

		addActor( add_unit );
		addActor( remove_unit );
		addActor( info_label );
	}

	private void addProperty( TextureRegion icon, String value, Vector2 position ) {
		Image background = new Image( Assets.getTextureRegion("number") );
		background.width = 70;
		background.height = 15;
		background.x = position.x;
		background.y = position.y;

		Image image = new Image( icon );
		image.width = 10;
		image.height = 10;
		image.x = position.x + 15;
		image.y = position.y + 2.5f;

		Label info;
		info = new Label( value, Assets.skin );
		info.x = position.x + 30;
		info.y = position.y;

		addActor( background );
		addActor( image );
		addActor( info );
	}

	private void updateInfo() {
		info_label.setText( Integer.toString( n_units) + " / " +
				Integer.toString( n_units * tc_unit.unit.getPrice() ) );
	}
}
