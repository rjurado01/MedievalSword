package com.me.mygdxgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Object {

	protected Vector2 	position;
	protected Rectangle bounds = new Rectangle();
	
	protected TextureRegion actual_texture;

	public TextureRegion getActualTexture() {
		return actual_texture;
	}

	public void setActualTexture(TextureRegion actual_texture) {
		this.actual_texture = actual_texture;
	}

	public Object(Vector2 pos, float width, float height) {
		this.position = pos;
		this.bounds = new Rectangle(pos.x, pos.y, width, height);
	}
	
	public Object( float width, float height ) {
		this.bounds = new Rectangle(0, 0, width, height);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = new Vector2( position );
		
		this.bounds.setX( position.x );
		this.bounds.setY( position.y );
	}
	
	public void setX(float x) {
		this.position.x = x;
	}
	
	public void setY(float y) {
		this.position.y = y;
	}
}
