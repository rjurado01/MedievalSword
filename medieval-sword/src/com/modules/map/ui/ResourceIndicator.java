package com.modules.map.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;

/**
 * Box that includes an icon and a label.
 * It is used in the HUD for show the player resources.
 */
public class ResourceIndicator extends Group {
	public static final int SIZE_H = 36;
	public static final int SIZE_W = 144;

	Image background;
	Image icon;
	Label text;

	public ResourceIndicator( int x, int y ) {
		background = new Image(Assets.getTextureRegion( "rect" ) );
		background.height = SIZE_H;
		background.width = SIZE_W;

		text = new Label( "0", Assets.font2 );
		text.x = 70;
		text.y = 5;

		addActor( background );
		addActor( text );

		this.x = x;
		this.y = y;
	}

	public ResourceIndicator( Vector2 position ) {
		background = new Image(Assets.getTextureRegion( "rect" ) );
		background.height = 36;
		background.width = 144;

		text = new Label( "0", Assets.font2 );
		text.x = 70;
		text.y = 5;

		addActor( background );
		addActor( text );

		this.x = position.x;
		this.y = position.y;
	}

	public void setIcon( TextureRegion icon_region ) {
		icon = new Image( icon_region );
		icon.width = 32;
		icon.height = 32;
		icon.x = 20;
		icon.y = 2;

		addActor( icon );
	}

	public void updateText( String new_text ) {
		text.setText( new_text );
	}

	public void updateText( int amount ) {
		text.setText( Integer.toString( amount ) );
	}
}
