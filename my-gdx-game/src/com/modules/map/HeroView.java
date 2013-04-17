package com.modules.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.utils.Vector2i;

public class HeroView extends Image {
	
	public HeroView( Vector2i size ) {
		this.width = size.x;
		this.height = size.y;
	}
	
	public Vector2 getPosition() {
		return new Vector2(x, y);
	}
	
	public void setPosition( Vector2 pos ) {
		this.x = pos.x;
		this.y = pos.y;
	}
}
