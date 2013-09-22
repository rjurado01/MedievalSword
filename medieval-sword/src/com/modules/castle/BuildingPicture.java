package com.modules.castle;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.utils.Vector2i;

/**
 * Show the icon of building in the castle panel (buildings page)
 */
public class BuildingPicture extends Group {

	TopCastleBuilding building;
	Image image;
	Image image_name;
	Label name;

	public BuildingPicture( TopCastleBuilding building, Vector2i position ) {
		this.building = building;

		x = position.x;
		y = position.y;
		width = CastlePanel.BUILDING_WIDTH;
		height = CastlePanel.BUILDING_HEIGHT + CastlePanel.BUILDING_NAME_HEIGHT;

		createImage();
		createName();
		addClickedEvent();
	}

	private void createImage() {
		image = new Image( Assets.getTextureRegion(
				building.getNextBuildingLevel().getBuildTexture() ) );

		image.width = width;
		image.height = CastlePanel.BUILDING_HEIGHT;
		image.y = CastlePanel.BUILDING_NAME_HEIGHT;

		addActor( image );
	}

	private void createName() {
		if( building.availableLevel() == false )
			image_name = new Image( Assets.getFrame( "botBuild", 3 ) );
		else if( building.canBuildLevel() )
			image_name = new Image( Assets.getFrame( "botBuild", 1 ) );
		else
			image_name = new Image( Assets.getFrame( "botBuild", 2 ) );

		image_name.width = width;
		image_name.height = CastlePanel.BUILDING_NAME_HEIGHT;

		name = new Label( building.getNextBuildingLevel().getName(), Assets.skin );
		name.x = 10;
		name.y = 1;

		addActor( image_name );
		addActor( name );
	}

	private void addClickedEvent() {
		if( building.complete() == false && building.level >= 0) {
			image.setClickListener( new ClickListener() {
				public void click(Actor actor, float x, float y) { clicked(); }
			});
		}
	}

	private void clicked() {
		System.out.println("Entro0");
		MapController.addEvent( MapConstants.SHOW_BUILDING, building );
	}

	public void update() {
		updateImage();

		removeActor( image_name );
		removeActor( name );

		createName();
	}

	private void updateImage() {
		image.setRegion( Assets.getTextureRegion(
				building.getNextBuildingLevel().getBuildTexture() ) );
	}
}
