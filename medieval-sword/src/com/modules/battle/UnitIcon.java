package com.modules.battle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;
import com.game.Constants;

/**
 * Represent information about one Unit.
 * It shows a box with unit icon and the number of such units.
 * It is used in the Hero panel, summary and Castle panel for show units.
 */
public class UnitIcon extends Group {

	public static final int SIZE_W = 100;
	public static final int SIZE_H = 140;
	public static final int NUMBER_BOX_H = 20;

	static final int ICON_W = 70;
	static final int ICON_H = 70;

	protected Image background;
	protected Image icon;

	protected Label number_units_label;

	int number = 0;

	public UnitIcon( float x_position, float y_position ) {
		this.x = x_position;
		this.y = y_position;

		this.width  = SIZE_W;
		this.height = SIZE_H;

		createBackgroundImage();
		createNumberLabel();

		this.addActor( background );
	}

	private void createBackgroundImage() {
		background = new Image( Assets.getTextureRegion( "unitSquare" ) );
		background.width  = SIZE_W;
		background.height = SIZE_H;
	}

	public void addIcon( TextureRegion texture ) {
		icon = new Image( texture );
		icon.width 	= ICON_W;
		icon.height = ICON_H;
		icon.x 		= ( SIZE_W - ICON_W ) / 2;
		icon.y 		= NUMBER_BOX_H + ( SIZE_H - ICON_H ) / 2;

		this.addActor( icon );
	}

	public void createNumberLabel() {
		number_units_label = new Label( Integer.toString( number ), Assets.font_black );
		number_units_label.x = ( SIZE_W - number_units_label.width ) / 2;
		number_units_label.y = ( NUMBER_BOX_H - Constants.FONT1_HEIGHT ) / 2;
	}

	public void showNumberLabel() {
		if( getActors().indexOf( number_units_label ) == -1 )
			addActor( number_units_label );
	}

	public void addDeaths( int number ) {
		this.number += number;
	}

	public void setNumber( int number ) {
		this.number = number;
		number_units_label.setText( Integer.toString(number) );
	}

	public void clearBox() {
		if( icon != null )
			removeActor( icon );

		if( number_units_label != null )
			removeActor( number_units_label );
	}

	public void setClickAction( ClickListener action ) {
		background.setClickListener(action);
	}
}
