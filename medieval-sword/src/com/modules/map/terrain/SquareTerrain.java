package com.modules.map.terrain;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Army;
import com.game.Assets;
import com.game.Constants;
import com.game.Player;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.modules.map.heroes.CreaturesGroup;
import com.modules.map.heroes.HeroTop;
import com.modules.map.ui.MiniMap;
import com.utils.Vector2i;

public class SquareTerrain extends Group {
	
	public static int HEIGHT = 40;
	public static int WIDTH = 40;

	static int NO_COLOR = -1;

	/* TYPES */
	static final int GRASS = 0;
	static final int WATHER = 1;
	static final int ROAD = 2;
	
	/* ROAD STATUS */
	static final int FREE = 0;
	static final int HERO_PLAYER_1 = 1;
	static final int CREATURES_GROUP = 2;
	static final int RESOURCE_PILE = 3;
	static final int RESOURCE_STRUCTURE = 4;
	
	boolean visible = true;
	
	int type;
	int status;
	int color;
	
	Vector2i number;
	Image image;
	Image fog;
	
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
		
		image = new Image();
		image.width = this.width;
		image.height = this.height;

		addActor( image );

		addClickEvent();
	}

	public void setRegion( TextureRegion region ) {
		image.setRegion( region );
	}
	
	public Vector2 getPosition() {
		return new Vector2( x, y );
	}

	public Vector2i getNumber()
	{
		return number;
	}

	private void addClickEvent() {
		image.setClickListener( new ClickListener() {
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

	public boolean isMinimapRoad() {
		if( type == Terrain.ROAD && ( status == FREE || status == RESOURCE_PILE ) )
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
	
	public CreaturesGroup getGroup() {
		return group;
	}

	/**
	 * Check if this square has any player hero or player structure
	 */
	public int getMiniMapColor() {
		if( !visible )
			return MiniMap.FOG;
		else if( isMinimapRoad() )
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
		else if( status == RESOURCE_STRUCTURE ) {
			if( color == Constants.RED )
				return MiniMap.RED;
			else if( color == Constants.BLUE )
				return MiniMap.BLUE;
			else
				return MiniMap.WHITE;
		}
		else
			return MiniMap.GRASS;
	}

	public void setFree() {
		if( type == ROAD )
			status = FREE; 
	}

	public void setFog( Stage stage ) {
		visible = false;

		fog = new Image( Assets.getTextureRegion( "greyBackground" ) );
		fog.width = width;
		fog.height = height;
		fog.x = number.x * WIDTH;
		fog.y = number.y * HEIGHT;

		stage.addActor( fog );
	}

	public void exprlore() {
		visible = true;

		if( fog != null )
			fog.remove();
	}

	public void setStructure(Player owner) {
		status = RESOURCE_STRUCTURE;

		if( owner == null )
			color = NO_COLOR;
		else
			color = owner.color;
	}
}
