package com.modules.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.Constants;
import com.utils.Vector2i;

public class MapInputProcessor implements InputProcessor {

	Stage terrain_stage;
	Stage ui_stage;
	Vector3 last_touch_down = new Vector3();
	Vector2i map_size;

	static private boolean hud_activated = true;
	static private boolean panel_activated = false;

	public MapInputProcessor( Stage terrain_stage, Stage ui_stage, Vector2i map_size ) {
		this.terrain_stage = terrain_stage;
		this.ui_stage = ui_stage;
		this.map_size = map_size;
	}

	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean touchDown(int x, int y, int pointer, int button) {
		last_touch_down.set(x, y, 0);

		if( hud_activated && isHudClicked(x, y) || panel_activated )
			ui_stage.touchDown(x, y, pointer, button);
		else {
			terrain_stage.touchDown(x, y, pointer, button );
		}

		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		if( hud_activated && isHudClicked(x, y) || panel_activated )
			ui_stage.touchUp(x, y, pointer, button);
		else
			terrain_stage.touchUp(x, y, pointer, button );

		return false;
	}

	public boolean touchDragged(int x, int y, int pointer) {
		if( hud_activated && panel_activated == false )
			moveCamera( x, y );

		return false;
	}

	private void moveCamera( int touch_x, int touch_y ) {
		Vector3 new_position = getNewCameraPosition( touch_x, touch_y );

		if( !cameraOutOfLimit( new_position ) )
			terrain_stage.getCamera().translate( new_position.sub( terrain_stage.getCamera().position ) );

		last_touch_down.set( touch_x, touch_y, 0);
	}

	private Vector3 getNewCameraPosition( int x, int y ) {
		Vector3 new_position = last_touch_down;
		new_position.sub(x, y, 0);
		new_position.y = -new_position.y;
		new_position.add( terrain_stage.getCamera().position );

		return new_position;
	}

	private boolean cameraOutOfLimit( Vector3 position ) {
		int x_left_limit = Constants.SIZE_W / 2 - Constants.HUD_WIDTH;
		int x_right_limit = map_size.x - Constants.SIZE_W / 2;
		int y_bottom_limit = Constants.SIZE_H / 2;
		int y_top_limit = map_size.y - Constants.SIZE_H / 2;

		if( position.x < x_left_limit || position.x > x_right_limit )
			return true;
		else if( position.y < y_bottom_limit || position.y > y_top_limit )
			return true;
		else
			return false;
	}

	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean scrolled(int amount) {
		terrain_stage.getCamera().viewportHeight += 14 * amount;
		terrain_stage.getCamera().viewportWidth += 20 * amount;
		return false;
	}

	private boolean isHudClicked( int x, int y ) {
		float hud_width = MapConstants.HUD_WIDTH * Gdx.graphics.getWidth() / Constants.SIZE_W;

		if( x < hud_width )
			return true;
		else
			return false;
	}

	static public void activateHUD() {
		hud_activated = true;
	}

	static public void deactivateHUD() {
		hud_activated = false;
	}

	static public void activatePanel() {
		panel_activated = true;
	}

	static public void deactivatePanel() {
		panel_activated = false;
	}
}
