package com.modules.map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.utils.Vector2i;

public abstract class Structure {
	public Vector2i square_position_number;
	public int type;
	
	// position from square_position_number for use structure
	protected Vector2i square_use_number;
	
	public String texture_name;
	public Vector2i size;
	public Vector2i squares_size;
	public Vector2i position_correction;
	
	Image image;
	
	public Structure( int type, Vector2i square ) {
		this.type = type;
		this.square_position_number = square;
		this.position_correction = new Vector2i(0, 0);
	}
	
	protected void createActor() {
		image = new Image( Assets.getTextureRegion( texture_name ) );
		
		image.x = SquareTerrain.WIDTH * square_position_number.x 
					+ position_correction.x;
		image.y = SquareTerrain.HEIGHT * square_position_number.y
					+ position_correction.y;
		
		image.width = size.x;
		image.height = size.y;
		
		image.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) { clicked(); };
		});
	}
	
	protected abstract void clicked();
	
	public Image getActor() {
		return image;
	}
	
	public Vector2i getUseSquareNumber() {
		Vector2i square_number = null;
		
		if( square_position_number != null && square_use_number != null )
			square_number = new Vector2i(
					square_position_number.x + square_use_number.x,
					square_position_number.y + square_use_number.y );
		
		return square_number;
	}
}
