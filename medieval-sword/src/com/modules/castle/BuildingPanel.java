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
 * Show the information about a building of a castle and allows buy it.
 */
public class BuildingPanel extends Group {
	Image background;
	Image picture;
	Label description;

	Button accept_button;
	Button cancel_button;

	TopCastleBuilding building;

	public BuildingPanel( TopCastleBuilding building ) {
		this.building = building;

		width = 200;
		height = 200;
		x = 170;
		y = 50;

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
		background = new Image( Assets.getTextureRegion("menu") );
		background.x = 10;
		background.y = 10;
		background.width = 200;
		background.height = 200;
		addActor( background );
	}

	private void loadPicture( String picture_name ) {
		picture = new Image( Assets.getTextureRegion( picture_name ) );
		picture.x = 30;
		picture.y = 130;
		picture.width = 70;
		picture.height = 60;
		addActor( picture );
	}

	private void loadDescription( String string ) {
		description = new Label( Assets.skin );
		description.setText( string );
		description.x = 30;
		description.y = 60;
		description.width = 80;
		description.height = 80;
		addActor( description );
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

		if( building.canBuildLevel() ) {
			accept_button.setClickListener( new ClickListener() {
				public void click(Actor actor, float x, float y) {
					MapController.addEvent( MapConstants.BUILD_BUILDING, null );
				}
			});
		}

		cancel_button.height = 30;
		cancel_button.width = 60;
		cancel_button.x = 130;
		cancel_button.y = 30;

		cancel_button.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				MapController.addEvent( MapConstants.CLOSE_BUILDING, null );
			}
		});

		addActor( accept_button );
		addActor( cancel_button );
	}

	private void createPrices( int gold, int wood, int stone ) {
		addProperty( Assets.getTextureRegion( "goldIcon" ),
				Integer.toString( gold ),
				new Vector2( 120, 172 ) );

		addProperty( Assets.getTextureRegion( "woodIcon" ),
				Integer.toString( wood ),
				new Vector2( 120, 152 ) );

		addProperty( Assets.getTextureRegion( "stoneIcon" ),
				Integer.toString( stone ),
				new Vector2( 120, 132 ) );
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
}
