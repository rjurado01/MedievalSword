package com.modules.castle;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;
import com.game.Constants;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.utils.Vector2i;

/**
 * Show the icon of building in the castle panel (buildings page)
 */
public class BuildingPicture extends Group {

	TopCastleBuilding building;
	Image background;
	Image image;
	Image name_image;
	Label name_label;

	public BuildingPicture( TopCastleBuilding building, Vector2i position ) {
		this.building = building;

		x = position.x;
		y = position.y;
		width = BuildingsPage.BUILDING_W;
		height = BuildingsPage.BUILDING_H + BuildingsPage.BUILDING_NAME_H;

		createImage();
		createName();
		addClickedEvent();
	}

	private void createImage() {
		background = new Image( Assets.getTextureRegion("rect2") );
		background.width = width;
		background.height = BuildingsPage.BUILDING_H;
		background.y = BuildingsPage.BUILDING_NAME_H;

		image = new Image( Assets.getTextureRegion(
				building.getNextBuildingLevel().getBuildTexture() ) );

		image.width = width;
		image.height = BuildingsPage.BUILDING_H;
		image.y = BuildingsPage.BUILDING_NAME_H;

		addActor( background );
		addActor( image );
	}

	private void createName() {
		if( building.availableLevel() == false )
			name_image = new Image( Assets.getFrame( "botBuild", 1 ) );
		else if ( building.complete() )
			name_image = new Image( Assets.getFrame( "botBuild", 4 ) );
		else if( building.canBuildLevel() )
			name_image = new Image( Assets.getFrame( "botBuild", 2 ) );
		else
			name_image = new Image( Assets.getFrame( "botBuild", 3 ) );

		name_image.width = width;
		name_image.height = BuildingsPage.BUILDING_NAME_H;

		String name = building.getNextBuildingLevel().getName( Constants.LANGUAGE );
		name_label = new Label( name, Assets.skin );
		name_label.x = ( width - name.length() * Constants.FONT1_WIDTH ) / 2;
		name_label.y = 3;

		addActor( name_image );
		addActor( name_label );
	}

	private void addClickedEvent() {
		if( building.complete() == false && building.level >= 0) {
			image.setClickListener( new ClickListener() {
				public void click(Actor actor, float x, float y) { clicked(); }
			});
		}
	}

	private void clicked() {
		Assets.playSound( "button", false );
		MapController.addEvent( MapConstants.SHOW_BUILDING, building );
	}

	public void update() {
		updateImage();
		removeActor( name_image );
		removeActor( name_label );
		createName();
	}

	private void updateImage() {
		image.setRegion( Assets.getTextureRegion(
				building.getNextBuildingLevel().getBuildTexture() ) );
	}
}
