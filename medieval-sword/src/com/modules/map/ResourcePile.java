package com.modules.map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Player;
import com.utils.Vector2i;

public abstract class ResourcePile {
	protected int amount;
	int type;
	
	public Vector2i square_position_number;
	public String texture_name;
	public Vector2i size;
	public Vector2i position_correction;
	
	Image image;
	
	public ResourcePile() {
		position_correction = new Vector2i(0,0);
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
	
	public Image getActor() {
		return image;
	}
	
	protected void clicked() {
		MapController.addEvent( MapConstants.RESOURCE_PILE, this );
	};
	
	public abstract void use( Player player );
	
}
