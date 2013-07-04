package com.modules.map;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.modules.map.terrain.MapObject;
import com.modules.map.terrain.SquareTerrain;
import com.utils.Vector2i;

public class MapActor {
	MapObject object;
	Image image;
	
	Vector2i square_position_number;
	
	public MapActor( Vector2i square_number, MapObject object ) {
		this.square_position_number = square_number;
		this.object = object;
		
		createActor();
	}
	
	protected void createActor() {
		image = new Image( Assets.getTextureRegion( object.texture_name ) );
		
		image.x = SquareTerrain.WIDTH * square_position_number.x 
					+ object.position_correction.x;
		image.y = SquareTerrain.HEIGHT * square_position_number.y
					+ object.position_correction.y;
		
		image.width = object.size.x;
		image.height = object.size.y;
	}
	
	public Image getActor() {
		return image;
	}
}
