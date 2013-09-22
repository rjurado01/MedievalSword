package com.modules.castle;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Army;
import com.game.Assets;
import com.modules.map.MapConstants;
import com.modules.map.MapController;

/**
 * Show the castle panel composed by the castle page,
 * the page of units and the page of buildings
 * Also it has buttons for navigate between pages
 * It save the castle status and allow to update it
 */
public class CastlePanel extends Group {

	public static final int BUILDING_WIDTH = 70;
	public static final int BUILDING_HEIGHT = 40;
	public static final int BUILDING_NAME_HEIGHT = 17;

	Image background;

	HomePage home_page;
	BuildingsPage buildings_page;
	UnitsPage units_page;

	Button init_button;
	Button buildings_button;
	Button units_button;
	Button exit_button;

	// save the selected building to build it in the future
	TopCastleBuilding selected_building;

	public CastlePanel( TopCastle castle ) {
		width = 350;
		height = 180;
		x = 100;
		y = 70;

		loadBackground();
		loadButtons();

		home_page = new HomePage( castle.army, null );
		buildings_page = new BuildingsPage( castle.buildings );
		units_page = new UnitsPage( castle.units );

		addActor( home_page );
	}

	private void loadBackground() {
		background = new Image( Assets.getTextureRegion("menu") );

		background.height = height;
		background.width = width;
		background.x = 0;
		background.y = 0;

		background.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {}
		});

		this.addActor( background );
	}

	private void loadButtons() {
		init_button = new Button(
				Assets.getTextureRegion("stats"),
				Assets.getTextureRegion("number") );
		buildings_button = new Button(
				Assets.getTextureRegion("stats"),
				Assets.getTextureRegion("number") );
		units_button = new Button(
				Assets.getTextureRegion("stats"),
				Assets.getTextureRegion("number") );
		exit_button = new Button(
				Assets.getTextureRegion("stats"),
				Assets.getTextureRegion("number") );

		init_button.height = 40;
		init_button.width = 40;
		init_button.x = 20;
		init_button.y = -20;

		init_button.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				removeActor( buildings_page );
				removeActor( units_page );
				addActor( home_page );
			}
		});

		buildings_button.height = 40;
		buildings_button.width = 40;
		buildings_button.x = 70;
		buildings_button.y = -20;

		buildings_button.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				removeActor( home_page );
				removeActor( units_page );
				addActor( buildings_page );
			}
		});

		units_button.height = 40;
		units_button.width = 40;
		units_button.x = 120;
		units_button.y = -20;

		units_button.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				removeActor( home_page );
				removeActor( buildings_page );
				addActor( units_page );
			}
		});

		exit_button.height = 40;
		exit_button.width = 40;
		exit_button.x = 170;
		exit_button.y = -20;

		exit_button.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				MapController.addEvent( MapConstants.CLOSE_CASTLE, null );
				remove();
			}
		});

		addActor( init_button );
		addActor( buildings_button );
		addActor( units_button );
		addActor( exit_button );
	}

	public void hide() {
		visible = false;
	}

	public void show() {
		visible = true;
	}

	public void updateBuildings() {
		buildings_page.updateBuildings();
	}

	public void buildSelectedBuilding() {
		selected_building.build();
	}

	public void selectBuilding(TopCastleBuilding building) {
		selected_building = building;
	}

	public void unselectBuilding() {
		selected_building = null;
	}

	public void updateUnits() {
		units_page.updateCastleUnits();
	}

	public void updateArmy( Army army ) {
		home_page.updateCastleArmy(army);
	}

	public void setHeroArmy( Army army ) {
		home_page.setHeroArmy( army );
	}
}
