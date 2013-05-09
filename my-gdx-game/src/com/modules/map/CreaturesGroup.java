package com.modules.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdxgame.Army;

public class CreaturesGroup {
	private Army army;
	private Image image;
	private SquareTerrain square;
	
	public CreaturesGroup( Army army, SquareTerrain square, Image image ) {
		this.army = army;
		this.square = square;
		this.image = image;
		
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
		return image.getRegion();
	}
}
