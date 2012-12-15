package com.me.mygdxgame;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.utils.CallBack;

/**
 * This class let us create animations that can be repeated for a while and call a
 * function when it end.
 */
public class CustomAnimation {
	Animation animation;
	
	float actual_time;
	float duration;
	
	CallBack callback = null;
	
	/**
	 * Class constructor
	 * @param animation
	 * @param duration
	 * @param callback
	 */
	public CustomAnimation( Animation animation, float duration, CallBack callback ) {
		this.animation = animation;
		
		if( duration > 0 )
			this.duration = duration;
		else
			this.duration = animation.animationDuration;
		
		this.actual_time = 0;
		
		this.callback = callback;
	}
	
	/**
	 * Update custom animation time
	 * @param time
	 */
	public void increaseTime(float time) {
		actual_time += time;
	}
	
	/**
	 * Get current frame
	 * @return current frame
	 */
	public TextureRegion getCurrentFrame() {
		return animation.getKeyFrame( actual_time );
	}
	
	/**
	 * Check if custom animation is end
	 * @return true if animation is end and false otherwise
	 */
	public boolean isFinished() {
		if( actual_time > duration ) {
			if( callback != null )
				callback.completed();

			return true;
		}
		else
			return false;
	}
	
	/**
	 * Add callback function to be executed when animation finishes.
	 * @param callback function
	 */
	public void addCallBack( CallBack callback ) {
		this.callback = callback;
	}
}