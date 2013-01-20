package com.me.modules.battle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.mygdxgame.Assets;
import com.me.mygdxgame.Constants;
import com.me.units.Archer;

/**
 * Create new arrow and add it to stage
 */
public class Arrow extends Image {
	
	public Arrow( float x_position, float y_position, int orientation, Stage stage ) {
		this.x = x_position;
		this.y = y_position + Archer.HEIGHT / 2;
		this.stage = stage;

		if( orientation == Constants.XR )
			this.setRegion( Assets.getTextureRegion( "arrow" + Constants.XR ) );
		else
			this.setRegion( Assets.getTextureRegion( "arrow" + Constants.XL ) );
		
		this.width = 20;
		this.height = 3;
		
		this.stage.addActor( this );
	}
	
	public void remove() {
		stage.removeActor( this );
	}
}
