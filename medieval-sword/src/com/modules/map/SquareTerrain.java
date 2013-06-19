package com.modules.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Army;
import com.game.Constants;
import com.utils.Vector2i;

public class SquareTerrain extends Image {
	
	static int HEIGHT = 40;
	static int WIDTH = 40;
	
	/* TYPES */
	static final int GRASS = 0;
	static final int WATHER = 1;
	static final int ROAD = 2;
	
	/* ROAD STATUS */
	static final int FREE = 0;
	static final int HERO_PLAYER_1 = 1;
	static final int CREATURES_GROUP = 2;
	static final int RESOURCE_PILE = 3;
	
	boolean visible = false;
	
	int type;
	int status;
	
	Vector2i number;
	
	HeroTop hero;
	CreaturesGroup group;
	MiniMap mini_map;
	
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
	
	public boolean isRoad() {
		if( type == Terrain.ROAD )
			return true;
		else 
			return false;
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

	public void setTopHero( HeroTop hero ) {
		this.hero = hero;
		this.status = HERO_PLAYER_1;
	}

	public void setResourcePileStatus() {
		this.status = RESOURCE_PILE;
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
	
	public int getType() {
		return type;
	}
	
	/**
	 * Check if this square has any player hero or player structure
	 */
	public int getMiniMapColor() {
		if( isRoadAvailable() )
			return MiniMap.ROAD;
		else if( type == SquareTerrain.WATHER )
			return MiniMap.WHATER;
		else if( hero != null ) {
			if( hero.getColor() == Constants.BLUE  )
				return MiniMap.BLUE;
			else
				return MiniMap.RED;
		}
		else if( hasCreaturesGroup() ) {
			return MiniMap.GREY; }
		else
			return MiniMap.GRASS;
	}

	public void setFree() {
		if( type == ROAD )
			status = FREE; 
	}
 }
