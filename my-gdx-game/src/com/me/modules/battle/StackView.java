package com.me.modules.battle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Represent view of stack.
 * It include stack position, size and textures.
 */
public class StackView extends Group {
	
	boolean show_number = true;
	
	Image unit_image;
	IndicatorUnits indicator;	
	
	public StackView( float width, float height, int number ) {
		this.width = width;
		this.height = height;
		
		unit_image = new Image();
		unit_image.width = width;
		unit_image.height = height;
		
		indicator = new IndicatorUnits( number );
		indicator.y = -12;
		
		addActor( unit_image );
		addActor( indicator );
	}
	
	public void showIndicator( boolean x ) {
		indicator.visible = x;
	}
	
	public Vector2 getPosition() {
		return new Vector2(x, y);
	}
	
	public void setPosition( Vector2 pos ) {
		this.x = pos.x;
		this.y = pos.y;
	}

	public void updateTextNumber( int number ) {
		indicator.updateTextNumber( number );
	}

	public void setFrame( TextureRegion frame ) {
		unit_image.setRegion( frame );
	}
}
