package com.modules.castle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;

/**
 * Group that show an unit icon and the number of units of this type
 */
public abstract class UnitIcon extends Group {
	static final int SIZE_W = 40;
	static final int SIZE_H = 60;

	final int ICON_W = 40;
	final int ICON_H = 40;

	Image background;
	Image icon;

	boolean icon_show;
	boolean label_show;

	Label number_units_label;

	int number = 0;

	public UnitIcon( float x_position, float y_position ) {
		this.x = x_position;
		this.y = y_position;

		this.width  = SIZE_W;
		this.height = SIZE_H;

		createBackgroundImage();
		createIcon();
		createNumberLabel();
	}

	private void createBackgroundImage() {
		background = new Image( Assets.getTextureRegion( "content" ) );
		background.width  = SIZE_W;
		background.height = SIZE_H;

		background.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) { clicked(); }
		});

		addActor( background );
	}

	protected abstract void clicked();

	public void createIcon() {
		icon = new Image();
		icon.x 		= 0;
		icon.y 		= 20;
		icon.width 	= ICON_W;
		icon.height = ICON_H;

		addActor( icon );
		icon_show = false;
	}

	public void removeIcon() {
		removeActor( icon );
		icon_show = false;
	}

	public void updateIcon( TextureRegion texture ) {
		icon.setRegion( texture );

		if( icon_show == false ) {
			icon_show = true;
			addActor( icon );
		}
	}

	public void createNumberLabel() {
		number_units_label = new Label( null, Assets.skin );
		number_units_label.x = ( SIZE_W - number_units_label.width ) / 2;
		number_units_label.y = ( SIZE_H - ICON_H - number_units_label.height ) / 2;

		addActor( number_units_label );
		label_show = false;
	}

	public void removeNumberLabel() {
		if( number_units_label != null ) {
			removeActor( number_units_label );
			label_show = false;
		}
	}

	public void setUnits( int number ) {
		this.number = number;
		number_units_label.setText( Integer.toString( this.number ) );
	}

	public void addUnits( int number ) {
		if( number_units_label != null ) {
			this.number += number;
			number_units_label.setText( Integer.toString( this.number ) );
		}
	}

	public void removeUnits( int number ) {
		if( number_units_label != null ) {
			this.number -= number;
			number_units_label.setText( Integer.toString( this.number ) );
		}
	}

	public void updateNumber( int number ) {
		number_units_label.setText( Integer.toString( number ) );

		if( label_show == false ) {
			label_show = true;
			addActor( number_units_label );
		}
	}
}
