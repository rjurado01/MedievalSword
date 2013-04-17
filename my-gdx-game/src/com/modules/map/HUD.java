package com.modules.map;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdxgame.Assets;
import com.mygdxgame.Constants;

public class HUD extends Group {
	
	Image background;
	Image map;
	Button pass_turn;
	
	public HUD( Stage stage ) {
		this.stage = stage;		
		this.x = 0;
		this.y = 0;
		this.width = Constants.HUD_WIDTH;
		this.height = Constants.HUD_HEIGHT;
		
		loadBackground();
	}
	
	private void loadBackground() {
		background = new Image(Assets.getTextureRegion( "number" ) );
		background.height = height;
		background.width = width;
		
		stage.addActor( background );
	}
	
}
