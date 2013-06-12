package com.modules.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;

public class SquarePath extends Image {
	static int HEIGHT = 16;
	static int WIDTH = 16;
	
	public SquarePath( Vector2 square_position, boolean attainable ) {
		this.x = square_position.x + ( SquareTerrain.WIDTH - WIDTH ) / 2;
		this.y = square_position.y + ( SquareTerrain.HEIGHT - HEIGHT ) / 2;
		this.width = WIDTH;
		this.height = HEIGHT;
		
		if( attainable )
			setRegion( Assets.getTextureRegion( "number" ) );
		else
			setRegion( Assets.getTextureRegion( "atack2Cell" ) );
	}
}
