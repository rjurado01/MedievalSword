package com.modules.battle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Constants;

/**
 * Create new arrow and add it to stage
 */
public class Arrow extends Image {

	public Arrow( float x_position, float y_position, int orientation, Stage stage ) {
		this.x = x_position;
		this.y = y_position;
		this.stage = stage;

		if( orientation == Constants.RIGHT )
			this.setRegion( Assets.getTextureRegion( "arrow" + Constants.RIGHT ) );
		else if ( orientation == Constants.LEFT )
			this.setRegion( Assets.getTextureRegion( "arrow" + Constants.LEFT ) );
		else if ( orientation == Constants.TOP )
			this.setRegion( Assets.getTextureRegion( "arrow" + Constants.TOP ) );
		else
			this.setRegion( Assets.getTextureRegion( "arrow" + Constants.DOWN ) );

		if( orientation == Constants.RIGHT || orientation == Constants.LEFT ) {
			this.width = 20;
			this.height = 3;
		}
		else {
			this.width = 3;
			this.height = 20;
		}

		this.stage.addActor( this );
	}

	public void remove() {
		stage.removeActor( this );
	}
}
