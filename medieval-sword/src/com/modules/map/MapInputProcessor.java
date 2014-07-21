package com.modules.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.Constants;
import com.modules.map.terrain.Terrain;
import com.utils.Vector2i;

public class MapInputProcessor implements InputProcessor {

	Terrain terrain;
	Stage ui_stage;
	Vector3 last_touch_down = new Vector3();
	Vector2i map_size;

	static private boolean hud_activated;
	static private boolean panel_activated;

	public MapInputProcessor( Terrain terrain, Stage ui_stage ) {
		this.terrain = terrain;
		this.ui_stage = ui_stage;
		hud_activated = true;
		panel_activated = false;
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
		else
			terrain.getStage().touchDown(x, y, pointer, button );

		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		if( hud_activated && isHudClicked(x, y) || panel_activated )
			ui_stage.touchUp(x, y, pointer, button);
		else
			terrain.getStage().touchUp(x, y, pointer, button );

		return false;
	}

	public boolean touchDragged(int x, int y, int pointer) {
		if( hud_activated && panel_activated == false )
			moveCamera( x, y );

		return false;
	}

	private void moveCamera( int touch_x, int touch_y ) {
		Vector3 new_position = getNewCameraPosition( touch_x, touch_y );
		terrain.centerCamera( new Vector2( new_position.x, new_position.y ) );
		last_touch_down.set( touch_x, touch_y, 0);
	}

	private Vector3 getNewCameraPosition( int x, int y ) {
		Vector3 new_position = last_touch_down;
		new_position.sub(x, y, 0);
		new_position.y = -new_position.y;
		new_position.add( terrain.getStage().getCamera().position );

		return new_position;
	}

	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean scrolled(int amount) {
		terrain.getStage().getCamera().viewportHeight += 14 * amount;
		terrain.getStage().getCamera().viewportWidth += 20 * amount;
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
