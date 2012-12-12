package com.me.mygdxgame;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.utils.CallBack;

public class CustomAnimation {
	Animation animation;
	
	float actual_time;
	float duration;
	
	CallBack callback = null;
	
	public CustomAnimation(Animation animation, float duration) {
		this.animation = animation;
		
		if( duration > 0 )
			this.duration = duration;
		else
			this.duration = animation.animationDuration;
		
		this.actual_time = 0;
	}
	
	
	public void increaseTime(float time) {
		actual_time += time;
	}
	
	
	public TextureRegion getCurrentFrame() {
		return animation.getKeyFrame( actual_time );
	}
	
	
	public boolean isFinished() {
		if( actual_time > duration ) {
			if( callback != null )
				callback.completed();

			return true;
		}
		else
			return false;
	}
	
	public void addCallBack( CallBack callback ) {
		this.callback = callback;
	}
}