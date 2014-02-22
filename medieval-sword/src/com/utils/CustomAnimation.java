package com.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.Assets;

/**
 * This class let us create animations that can be repeated for a while.
 * When animation finish, execute callback function.
 */
public class CustomAnimation {

	float actual_time;
	float duration;

	Animation animation;
	CallBack callback = null;
	String sound_name;


	/**
	 * If duration == 0, set animation duration (execute only one time)
	 * @param string
	 */
	public CustomAnimation( Animation animation, float duration, CallBack callback, String sound ) {
		this.animation = animation;
		this.duration = duration > 0 ? duration : animation.animationDuration;
		this.actual_time = 0;
		this.callback = callback;
		this.sound_name = sound;
	}

	public void increaseTime(float time) {
		if( actual_time == 0 && sound_name != null )
			Assets.playSound( sound_name, false );

		actual_time += time;
	}

	public TextureRegion getCurrentFrame() {
		return animation.getKeyFrame( actual_time );
	}

	public boolean isFinished() {
		if( actual_time >= duration ) {
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