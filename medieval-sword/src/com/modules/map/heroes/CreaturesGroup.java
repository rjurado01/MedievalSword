package com.modules.map.heroes;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Army;
import com.game.Constants;
import com.game.Stack;
import com.game.Unit;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.modules.map.terrain.SquareTerrain;

/**
 *	Group of wild creatures that are represented in the map.
 */
public class CreaturesGroup {
	private Army army;
	private SquareTerrain square;
	private Unit unit;
	private Image image;

	int amount = 0;

	public CreaturesGroup( Army army, SquareTerrain square, Unit unit ) {
		this.army = army;
		this.square = square;
		this.unit = unit;

		this.image = unit.getMapImage( Constants.DOWN );
		this.image.x = square.getPosition().x + ( MapConstants.SQUARE_TERRAIN_W - image.width ) / 2;
		this.image.y = square.getPosition().y + MapConstants.SQUARE_Y_POSITION;

		this.image.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) { clicked(); }
		});

		this.square.setCreaturesGroup( this );

		for( Stack stack : army.getStacks() )
			amount += stack.getNumber();

	}

	private void clicked() {
		MapController.addEvent( MapConstants.CREATURES, this );
	}

	public Army getArmy() {
		return army;
	}

	public Image getImage() {
		return image;
	}

	public SquareTerrain getSquare() {
		return square;
	}

	public TextureRegion getIconTextureRegion() {
		return unit.getIcon(0);
	}

	public void destroy( Stage stage ) {
		stage.removeActor( image );
		square.setFree();
	}

	public String getAmount() {
		if( amount < 20 )
			return "1-20";
		else if( amount < 50)
			return "20-50";
		else if( amount < 100)
			return "50 - 100";
		else
			return "+100";
	}

	public String getUnitName( String language ) {
		return unit.getName( language );
	}
}
