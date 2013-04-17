package com.modules.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.utils.Vector2i;

public class SquareTerrain extends Image {
	
	static int HEIGHT = 40;
	static int WIDTH = 40;
	
	boolean visible = false;
	
	int type;
	
	public SquareTerrain( Vector2i position, int type ) {
		this.x = position.x * WIDTH;
		this.y = position.y * HEIGHT;
		this.width = WIDTH;
		this.height = HEIGHT;
		this.type = type;
	}
	
	public Vector2 getPosition() {
		return new Vector2( x, y );
	}
 }
