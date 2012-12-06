package com.me.modules.battle;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.me.mygdxgame.Assets;

public class IndicatorUnits extends Group {
	Image square;
	Label text;
	
	/**
	 * Class constructor
	 * @param number number of units
	 * @param x x unit position
	 * @param y y unit position
	 */
	public IndicatorUnits( int number, float x, float y, boolean inverse ) {
		text = new Label( Integer.toString( number ), Assets.skin );
		square = new Image( Assets.getTextureRegion( "number" ) );
		
		addActor( square );
		addActor( text );
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Class constructor
	 * @param number number of units
	 */
	public IndicatorUnits( int number ) {
		text = new Label( Integer.toString( number ), Assets.skin );
		square = new Image( Assets.getTextureRegion( "number" ) );
		
		square.width = text.width + 8;
		text.x = ( square.width - text.width ) / 2;
		this.width = square.width;
		
		addActor( square );
		addActor( text );
	}
	
	/**
	 * Change Group position
	 * @param x
	 * @param y
	 */
	public void setPosition( float x, float y ) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Change text to new number
	 * @param number
	 */
	public void updateTextNumber( int number ) {
		System.out.println(square.width);
		text.setText( Integer.toString( number ) );
		text.width = text.getTextBounds().width;
		
		removeActor(text);
		removeActor( square );

		square = new Image( Assets.getTextureRegion( "number" ) );		
		square.width = text.width + 8;
		text.x = ( square.width - text.width ) / 2;

		addActor( square );
		addActor( text );
	}
}
