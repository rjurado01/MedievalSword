package com.me.modules.battle;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.me.mygdxgame.Assets;

/**
 * Indicator that show number of units in a stack
 */
public class IndicatorUnits extends Group {
	Image box;
	Label text;
	

	public IndicatorUnits( int number ) {
		createTextLabel( number );
		createBoxImage();
		
		this.width = box.width;
		
		addActor( box );
		addActor( text );
	}
	
	private void createTextLabel( int number ) {
		text = new Label( Integer.toString( number ), Assets.skin );
		text.x = 4;
	}
	
	private void createBoxImage() {
		box = new Image( Assets.getTextureRegion( "number" ) );
		box.width = text.width + 8;
	}
	
	public void setPosition( float x, float y ) {
		this.x = x;
		this.y = y;
	}
	

	public void updateTextNumber( int number ) {
		text.setText( Integer.toString( number ) );
		text.width = text.getTextBounds().width;
		
		removeActor(text);
		removeActor( box );

		createBoxImage();
		text.x = ( box.width - text.width ) / 2;

		addActor( box );
		addActor( text );
	}
}
