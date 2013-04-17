package com.modules.map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdxgame.Assets;

public class TopStructure {
	
	Image image;
	Structure structure;
	
	public TopStructure( Structure structure ) {
		this.structure = structure;
		createActor();
	}
	
	private void createActor() {
		image = new Image( Assets.getTextureRegion( structure.texture ) );
		
		image.x = SquareTerrain.WIDTH * structure.square_number.x 
					+ structure.position_correction.x;
		image.y = SquareTerrain.HEIGHT * structure.square_number.y
					+ structure.position_correction.y;
		
		image.width = structure.size.x;
		image.height = structure.size.y;
		
		image.setClickListener( new ClickListener() {
	
			public void click(Actor actor, float x, float y) {
				MapController.addEvent( MapConstants.STRUCTURE, structure );
			}
		});
	}
	
	public Image getActor() {
		return image;
	}
}
