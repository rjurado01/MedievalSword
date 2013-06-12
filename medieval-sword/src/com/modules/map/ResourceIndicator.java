package com.modules.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;

public class ResourceIndicator extends Group {
	
	Image background;
	Image icon;
	Label text;
	
	public ResourceIndicator( int x, int y ) {
		
		background = new Image(Assets.getTextureRegion( "number" ) );
		background.height = 15;
		background.width = 70;
		
		text = new Label( "0", Assets.skin );
		text.x = 25;
		
		addActor( background );
		addActor( text );
		
		this.x = x;
		this.y = y;
	}
	
	public void setIcon( TextureRegion icon_region ) {
		icon = new Image( icon_region );
		icon.width = 10;
		icon.height = 10;
		icon.x = 12;
		icon.y = 2.5f;
		
		addActor( icon );
	}
	
	public void updateAmount( int amount ) {
		text.setText( Integer.toString( amount ) );
	}
}
