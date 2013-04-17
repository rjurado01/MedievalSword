package com.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This class let us create animations that can be repeated for a while.
 * When animation finish, execute callback function.
 */
public class CustomAnimation {
	
	float actual_time;
	float duration;
	
	Animation animation;
	CallBack callback = null;
	

	/**
	 * If duration == 0, set animation duration (execute only one time)
	 */
	public CustomAnimation( Animation animation, float duration, CallBack callback ) {
		this.animation = animation;		
		this.duration = duration > 0 ? duration : animation.animationDuration;		
		this.actual_time = 0;		
		this.callback = callback;
	}
	
	public void increaseTime(float time) {
		actual_time += time;
	}
	
	public TextureRegion getCurrentFrame() {
		return animation.getKeyFrame( actual_time );
	}
	
	public boolean isFinished() {
		if( actual_time > duration ) {
			executeCallback();
			return true;
		}
		else
			return false;
	}
	
	private void executeCallback() {
		if( callback != null )
			callback.completed();
	}
	
	/**
	 * Add callback function to be executed when animation finishes.
	 */
	public void addCallBack( CallBack callback ) {
		this.callback = callback;
	}
}