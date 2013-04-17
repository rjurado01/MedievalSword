package com.modules.battle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdxgame.Assets;

/**
 * Represent each box of summary that contain information about one Unit.
 */
public class BattleSummaryStack extends Group {
	
	static final int SIZE_W = 40;
	static final int SIZE_H = 60;
	
	final int ICON_W = 40;
	final int ICON_H = 40;
	
	Image background;
	Image icon;
	
	Label number_units_label;
	
	int number = 0;
	
	public BattleSummaryStack( float x_position, float y_position ) {
		this.x = x_position;
		this.y = y_position;
		
		this.width  = SIZE_W;
		this.height = SIZE_H;
		
		createBackgroundImage();
		
		this.addActor( background );
	}
	
	private void createBackgroundImage() {
		background = new Image( Assets.getTextureRegion( "content" ) );		
		background.width  = SIZE_W;
		background.height = SIZE_H;
	}
	
	public void addIcon( TextureRegion texture ) {		
		icon = new Image( texture );
		
		icon.x 		= 0;
		icon.y 		= 20;
		icon.width 	= ICON_W;
		icon.height = ICON_H;
		
		this.addActor( icon );
	}
	
	public void createNumberLabel() {
		if( icon != null ) {
			number_units_label = new Label( Integer.toString( number ), Assets.skin );
			
			number_units_label.x = ( SIZE_W - number_units_label.width ) / 2;
			number_units_label.y = ( SIZE_H - ICON_H - number_units_label.height ) / 2;
			
			this.addActor( number_units_label );
		}
	}
	
	public void addDeaths( int number ) {
		this.number += number;
	}
}
