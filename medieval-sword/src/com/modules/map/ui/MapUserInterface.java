package com.modules.map.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Constants;
import com.game.Player;
import com.modules.map.objetives.LevelObjectives;
import com.modules.map.terrain.Terrain;

/**
 * Manage the UI stage and all elements that are drown in it.
 */
public class MapUserInterface {

	HUD hud;
	Stage stage;
	Image alpha;

	OptionsPanel options_panel;
	ObjectivesPanel objectives_panel;

	public MapUserInterface( Stage stage, LevelObjectives objectives ) {
		this.stage = stage;

		alpha = new Image( Assets.getTextureRegion( "disabledBackground" ) );
		alpha.width = Constants.SIZE_W;
		alpha.height = Constants.SIZE_H;
		alpha.x = 0;
		alpha.y = 0;

		// stop propagation of clicks
		alpha.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {}
		});

		options_panel = new OptionsPanel();
		objectives_panel = new ObjectivesPanel( objectives );
	}

	public void createHUD( Player player, Terrain terrain ) {
		hud = new HUD( terrain );
		hud.updateGold( player.gold );
		hud.updateWood( player.wood );
		hud.updateStone( player.stone );

		stage.addActor( hud );
	}

	public HUD getHUD() {
		return hud;
	}

	public Stage getStage() {
		return stage;
	}

	public void updateHudResources( Player player ) {
		hud.updateGold( player.gold );
		hud.updateWood( player.wood );
		hud.updateStone( player.stone );
	}

	public void disableAll() {
		stage.addActor(alpha);
	}

	public void enableAll() {
		stage.removeActor(alpha);
	}

	public void showOptionsPanel() {
		disableAll();
		stage.addActor( options_panel );
	}

	public void showObjectivesPanel() {
		disableAll();
		stage.addActor( objectives_panel );
	}

	public void completeObjective( int n ) {
		objectives_panel.completeObjective(n);
	}

	public void showEndPanel( boolean win ) {
		objectives_panel.setEndGame(win);
		showObjectivesPanel();
	}

	public void completeAllObjectives() {
		objectives_panel.completeAllObjectives();
	}
}
