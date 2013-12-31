package com.modules.castle;

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

/**
 * Shows information about a building of a castle and allows buy it.
 */
public class BuildingPanel extends Group {
	final int ROW_1_Y = 240;
	final int PRICES_X = 220;

	Image background;
	Image picture_background;
	Image picture;
	Label description;

	Button accept_button;
	Button cancel_button;

	TopCastleBuilding building;

	public BuildingPanel( TopCastleBuilding building ) {
		this.building = building;

		width = 400;
		height = 400;
		x = ( Constants.SIZE_W - width ) / 2 + MapConstants.HUD_WIDTH / 2;
		y = ( Constants.SIZE_H - height ) / 2;

		loadBackground();
		loadPicture( building.getNextBuildingLevel().getBuildTexture() );
		loadDescription( building.getNextBuildingLevel().getDescription() );
		loadButtons();
		createPrices(
			building.getNextBuildingLevel().getGoldPrice(),
			building.getNextBuildingLevel().getWoodPrice(),
			building.getNextBuildingLevel().getStonePrice()
		);
	}

	private void loadBackground() {
		background = new Image( Assets.getTextureRegion("backgroundArmy") );
		background.x = 10;
		background.y = 10;
		background.width = width;
		background.height = height;
		addActor( background );
	}

	private void loadPicture( String picture_name ) {
		picture_background = new Image( Assets.getTextureRegion( "rect" ) );
		picture_background.width = BuildingsPage.BUILDING_W;
		picture_background.height = BuildingsPage.BUILDING_H + 10;
		picture_background.x = 60;
		picture_background.y = ROW_1_Y;

		picture = new Image( Assets.getTextureRegion( picture_name ) );
		picture.x = picture_background.x;
		picture.y = picture_background.y + 5;
		picture.width = BuildingsPage.BUILDING_W;
		picture.height = BuildingsPage.BUILDING_H;

		addActor( picture_background );
		addActor( picture );
	}

	private void loadDescription( String string ) {
		description = new Label( Assets.skin );
		description.setText( string );
		description.x = 60;
		description.y = 150;
		description.width = 80;
		description.height = 80;
		addActor( description );
	}

	private void loadButtons() {
		accept_button = new Button(
				Assets.getFrame( "btnBuy", 1 ),
				Assets.getFrame( "btnBuy", 2 ) );
		cancel_button = new Button(
				Assets.getFrame( "btnExit", 1 ),
				Assets.getFrame( "btnExit", 2 ) );

		accept_button.height = CastlePanel.BUTTONS_SIZE;
		accept_button.width = CastlePanel.BUTTONS_SIZE;
		accept_button.x = width / 2 - accept_button.width;
		accept_button.y = 50;

		if( building.canBuildLevel() ) {
			accept_button.setClickListener( new ClickListener() {
				public void click(Actor actor, float x, float y) {
					MapController.addEvent( MapConstants.BUILD_BUILDING, null );
				}
			});
		}

		cancel_button.height = CastlePanel.BUTTONS_SIZE;
		cancel_button.width = CastlePanel.BUTTONS_SIZE;
		cancel_button.x = width / 2 + 10;
		cancel_button.y = 50;

		cancel_button.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				MapController.addEvent( MapConstants.CLOSE_BUILDING, null );
			}
		});

		addActor( accept_button );
		addActor( cancel_button );
	}

	private void createPrices( int gold, int wood, int stone ) {
		ResourceIndicator gold_label = new ResourceIndicator( PRICES_X, ROW_1_Y + 82 );
		gold_label.setIcon( Assets.getTextureRegion( "iconPrice" ) );
		gold_label.updateText( gold );

		ResourceIndicator wood_label = new ResourceIndicator( PRICES_X, ROW_1_Y + 41 );
		wood_label.setIcon( Assets.getTextureRegion( "iconWood" ) );
		wood_label.updateText( wood );

		ResourceIndicator stone_label = new ResourceIndicator( PRICES_X, ROW_1_Y );
		stone_label.setIcon( Assets.getTextureRegion( "iconStone" ) );
		stone_label.updateText( stone );

		addActor( gold_label );
		addActor( wood_label );
		addActor( stone_label );
	}
}
