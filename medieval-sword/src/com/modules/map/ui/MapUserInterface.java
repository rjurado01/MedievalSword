package com.modules.map.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.Player;
import com.modules.map.terrain.Terrain;

public class MapUserInterface {

	HUD hud;
	ArmyInfoPanel army_panel;

	Stage stage;

	public MapUserInterface( Stage stage ) {
		this.stage = stage;
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
}
