package com.modules.castle;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Army;
import com.game.Assets;
import com.game.Constants;
import com.modules.map.MapConstants;
import com.modules.map.MapController;

/**
 * Show the castle panel composed by the castle page,
 * the page of units and the page of buildings
 * Also it has buttons for navigate between pages
 * It save the castle status and allow to update it
 */
public class CastlePanel extends Group {

	final int SIZE_W = 840;
	final int SIZE_H = 430;

	static final int BUTTONS_SIZE = 74;
	final int BUTTONS_SPACE = BUTTONS_SIZE + 20;
	final int BUTTONS_X = 35;
	final int BUTTONS_1_Y = 36;

	Image background;

	HomePage home_page;
	BuildingsPage buildings_page;
	UnitsPage units_page;

	Button init_button;
	Button buildings_button;
	Button units_button;
	Button exit_button;

	Image init_button_selected;
	Image buildings_button_selected;
	Image units_button_selected;

	// save the selected building to build it in the future
	TopCastleBuilding selected_building;

	public CastlePanel( TopCastle castle, Army hero_army ) {
		width = SIZE_W;
		height = SIZE_H;
		this.x = ( Constants.SIZE_W - width ) / 2 + MapConstants.HUD_WIDTH / 2;
		this.y = ( Constants.SIZE_H - height ) / 2;

		loadBackground();
		loadButtons();

		home_page = new HomePage( castle.army, hero_army, castle.owner.color );
		buildings_page = new BuildingsPage( castle.buildings );
		units_page = new UnitsPage( castle.units );

		addActor( home_page );
	}

	private void loadBackground() {
		background = new Image( Assets.getTextureRegion("castleBackground") );

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
				Assets.getFrame("btnCastle", 1),
				Assets.getFrame("btnCastle", 2) );
		buildings_button = new Button(
				Assets.getFrame("btnBuild", 1),
				Assets.getFrame("btnBuild", 2) );
		units_button = new Button(
				Assets.getFrame("btnUnits", 1),
				Assets.getFrame("btnUnits", 2) );
		exit_button = new Button(
				Assets.getFrame("btnExit", 1),
				Assets.getFrame("btnExit", 2) );

		init_button_selected = new Image( Assets.getFrame("btnCastle", 3) );
		buildings_button_selected = new Image( Assets.getFrame("btnBuild", 3) );
		units_button_selected = new Image( Assets.getFrame("btnUnits", 3) );

		init_button.height = BUTTONS_SIZE;
		init_button.width = BUTTONS_SIZE;
		init_button.x = BUTTONS_X;
		init_button.y = BUTTONS_1_Y + BUTTONS_SPACE * 3;

		init_button_selected.height = init_button.height;
		init_button_selected.width = init_button.width;
		init_button_selected.x = init_button.x;
		init_button_selected.y = init_button.y;

		init_button.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				Assets.playSound( "button", false );
				removeActor( buildings_page );
				removeActor( units_page );
				addActor( home_page );

				init_button.visible = false;
				buildings_button.visible = true;
				units_button.visible = true;
			}
		});

		buildings_button.height = BUTTONS_SIZE;
		buildings_button.width = BUTTONS_SIZE;
		buildings_button.x = BUTTONS_X;
		buildings_button.y = BUTTONS_1_Y + BUTTONS_SPACE * 2;

		buildings_button_selected.height = buildings_button.height;
		buildings_button_selected.width = buildings_button.width;
		buildings_button_selected.x = buildings_button.x;
		buildings_button_selected.y = buildings_button.y;

		buildings_button.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				Assets.playSound( "button", false );
				removeActor( home_page );
				removeActor( units_page );
				addActor( buildings_page );

				init_button.visible = true;
				buildings_button.visible = false;
				units_button.visible = true;
			}
		});

		units_button.height = BUTTONS_SIZE;
		units_button.width = BUTTONS_SIZE;
		units_button.x = BUTTONS_X;
		units_button.y = BUTTONS_1_Y + BUTTONS_SPACE;

		units_button_selected.height = units_button.height;
		units_button_selected.width = units_button.width;
		units_button_selected.x = units_button.x;
		units_button_selected.y = units_button.y;

		units_button.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				Assets.playSound( "button", false );
				removeActor( home_page );
				removeActor( buildings_page );
				addActor( units_page );

				init_button.visible = true;
				buildings_button.visible = true;
				units_button.visible = false;
			}
		});

		exit_button.height = BUTTONS_SIZE;
		exit_button.width = BUTTONS_SIZE;
		exit_button.x = BUTTONS_X;
		exit_button.y = BUTTONS_1_Y;

		exit_button.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				Assets.playSound( "button", false );
				MapController.addEvent( MapConstants.CLOSE_PANEL, null );
				remove();
			}
		});

		init_button.visible = false;

		addActor( init_button_selected );
		addActor( init_button );
		addActor( buildings_button_selected );
		addActor( buildings_button );
		addActor( units_button_selected );
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
		units_page.updateLabelsPrice();
	}

	public void updateArmy( Army army ) {
		home_page.updateCastleArmy(army);
	}
}
