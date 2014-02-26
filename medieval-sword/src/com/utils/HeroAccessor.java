package com.utils;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.modules.map.MapController;

public class HeroAccessor implements TweenAccessor<MapController> {

	public static final int POSITION_X = 1;
    public static final int POSITION_Y = 2;
    public static final int POSITION_XY = 3;

	public int getValues( MapController target, int tweenType, float[] returnValues ) {
		Actor actor = target.getSelectedHero().getView();

		switch (tweenType) {
	        case POSITION_X: returnValues[0] = actor.x; return 1;
	        case POSITION_Y: returnValues[0] = actor.y; return 1;
	        case POSITION_XY:
	            returnValues[0] = actor.x;
	            returnValues[1] = actor.y;
	            return 2;
	        default: assert false; return -1;
		}
	}

	public void setValues( MapController target, int tweenType, float[] newValues ) {
		Actor actor = target.getSelectedHero().getView();

        switch (tweenType) {
	        case POSITION_X: actor.x = newValues[0]; break;
	        case POSITION_Y: actor.y = newValues[0]; break;
	        case POSITION_XY:
	            actor.x = newValues[0];
	            actor.y = newValues[1];
	            break;
	        default: assert false; break;
	    }

        target.centerCamera( new Vector2( actor.x, actor.y ) );
	}
}
