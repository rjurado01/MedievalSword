package com.me.modules.battle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.mygdxgame.Assets;
import com.me.mygdxgame.Unit;
import com.me.units.Archer;

/**
 * Create new arrow from archer and add it to stage
 * @author drinor
 */
public class Arrow extends Image {
	
	public Arrow( float x, float y, int orientation, Stage stage ) {
		this.x = x;
		this.y = y + Archer.HEIGHT / 2;
		this.stage = stage;

		if( orientation == Unit.XR )
			this.setRegion( Assets.getTextureRegion( "arrow" ) );
		else
			this.setRegion( Assets.getFlipTextureRegion( "arrow" ) );
		
		this.width = 20;
		this.height = 3;

		stage.addActor( this );
	}
	
	/**
	 * Remove arrow from stage
	 */
	public void remove() {
		stage.removeActor( this );
	}
}
