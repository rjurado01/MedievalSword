package com.modules.map;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdxgame.Constants;

public class MapInputProcessor implements InputProcessor {
	
	Stage stage;
	Vector3 last_touch_down = new Vector3();
	Terrain terrain;
	
	public MapInputProcessor( Terrain terrain ) {
		this.stage = terrain.getStage();
		this.terrain = terrain;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		last_touch_down.set(x, y, 0);
		stage.touchDown(x, y, pointer, button );
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		stage.touchUp(x, y, pointer, button );
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		moveCamera( x, y );		
		return false;
	}
	
	private void moveCamera( int touch_x, int touch_y ) {
		Vector3 new_position = getNewCameraPosition( touch_x, touch_y );

		if( !cameraOutOfLimit( new_position ) )
			stage.getCamera().translate( new_position.sub( stage.getCamera().position ) );
		
		last_touch_down.set( touch_x, touch_y, 0);
	}
	
	private Vector3 getNewCameraPosition( int x, int y ) {
		Vector3 new_position = last_touch_down;
		new_position.sub(x, y, 0);
		new_position.y = -new_position.y;
		new_position.add( stage.getCamera().position );
		
		return new_position;
	}
	
	private boolean cameraOutOfLimit( Vector3 position ) {
		int x_left_limit = Constants.SIZE_W / 2 - Constants.HUD_WIDTH;
		int x_right_limit = terrain.getWidth() - Constants.SIZE_W / 2;
		int y_bottom_limit = Constants.SIZE_H / 2;
		int y_top_limit = terrain.getHeight() - Constants.SIZE_H / 2;
		
		if( position.x < x_left_limit || position.x > x_right_limit )
			return true;
		else if( position.y < y_bottom_limit || position.y > y_top_limit )
			return true;
		else
			return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
