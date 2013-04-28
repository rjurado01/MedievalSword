package com.modules.map;

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
		
		square.setCreaturesGroup( this );
	}
	
	public Army getArmy() {
		return army;
	}
	
	public Image getImage() {
		return image;
	}
}
