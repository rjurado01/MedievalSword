package com.me.modules.battle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.me.mygdxgame.Assets;

public class BattleSummaryUnit extends Group {
	
	static final int SIZE_W = 40;
	static final int SIZE_H = 60;
	
	final int ICON_W = 40;
	final int ICON_H = 40;
	
	Image background;
	Image icon;
	
	Label label;	// number of units
	
	int number = 0;
	
	/**
	 * Class constructor
	 * 
	 * @param x x position
	 * @param y y position
	 */
	public BattleSummaryUnit( float x, float y ) {
		this.x = x;
		this.y = y;
		
		this.width  = SIZE_W;
		this.height = SIZE_H;
		
		background = new Image( Assets.getTextureRegion( "content" ) );
		
		background.width  = SIZE_W;
		background.height = SIZE_H;
		
		this.addActor( background );
	}
	
	/**
	 * Set icon from unit
	 * 
	 * @param texture
	 */
	public void setIcon( TextureRegion texture ) {
		
		icon = new Image( texture );
		
		icon.x 		= 0;
		icon.y 		= 20;
		icon.width 	= ICON_W;
		icon.height = ICON_H;
		
		this.addActor( icon );
	}
	
	/**
	 * Create and add to stage label with number of dead units
	 */
	public void addNumberLabel() {
		if( icon != null ) {
			label = new Label( Integer.toString( number ), Assets.skin );
			
			label.x = ( SIZE_W - label.width ) / 2;
			label.y = ( SIZE_H - ICON_H - label.height ) / 2;
			
			this.addActor( label );
		}
	}
	
	/**
	 * Increase the number of dead units
	 * 
	 * @param number
	 */
	public void addDead( int number ) {
		this.number = number;
	}
}
