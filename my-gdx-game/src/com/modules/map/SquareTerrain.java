package com.modules.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdxgame.Army;
import com.mygdxgame.Constants;
import com.utils.Vector2i;

public class SquareTerrain extends Image {
	
	static int HEIGHT = 40;
	static int WIDTH = 40;
	
	/* ROAD STATUS */
	static final int FREE = 0;
	static final int PLAYER = 1;
	static final int CREATURES_GROUP = 2;
	
	boolean visible = false;
	
	int type;
	int status;
	
	Vector2i number;
	
	Hero hero;
	CreaturesGroup group;
	
	public SquareTerrain( Vector2i number, int type ) {
		this.x = number.x * WIDTH;
		this.y = number.y * HEIGHT;
		this.width = WIDTH;
		this.height = HEIGHT;
		this.type = type;
		this.status = 0;
		this.number = number;
		
		addClickEvent();
	}
	
	public Vector2 getPosition() {
		return new Vector2( x, y );
	}
	
	public Vector2i getNumber()
	{
		return number;
	}
	private void addClickEvent() {
		setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) { clicked(); }	
		});
	}
	
	private void clicked() {
		MapController.addEvent( MapConstants.SQUARE, this );
	}
	
	public boolean isRoadAvailable() {
		if( type == Terrain.ROAD && status == FREE )
			return true;
		else 
			return false;
	}

	public boolean hasCreaturesGroup() {
		if( status == CREATURES_GROUP )
			return true;
		else
			return false;
	}
	
	public void setHero( Hero hero ) {
		this.hero = hero;
		// this.status = HERO;
	}
	
	public void setCreaturesGroup( CreaturesGroup group ) {
		this.group = group;
		this.status = CREATURES_GROUP;
	}
	
	public Army getArmy() {
		if( status == CREATURES_GROUP )
			return group.getArmy();
		else
			return null;
	}
 }
