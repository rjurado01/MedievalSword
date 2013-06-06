package com.modules.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdxgame.Army;
import com.mygdxgame.Constants;
import com.mygdxgame.Unit;

public class CreaturesGroup {
	private Army army;
	private SquareTerrain square;
	private Unit unit;
	private Image image;
	
	public CreaturesGroup( Army army, SquareTerrain square, Unit unit ) {
		this.army = army;
		this.square = square;
		this.unit = unit;
		
		this.image = unit.getMapImage( Constants.XL );
		this.image.x = square.getPosition().x;
		this.image.y = square.getPosition().y;
		
		this.image.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) { clicked(); }
		});
		
		this.square.setCreaturesGroup( this );
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
		return unit.getIcon();
	}
	
	public void destroy( Stage stage ) {
		stage.removeActor( image );
		square.setFree();
	}
}
