package com.me.mygdxgame;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Action {
	Animation animation;
	
	float actual_time;
	float duration;
	
	
	public Action(Animation animation, float duration) {
		this.animation = animation;
		this.duration = duration;
		
		this.actual_time = 0;
	}
	
	
	public void increaseTime(float time) {
		actual_time += time;
	}
	
	
	public TextureRegion getCurrentFrame() {
		return animation.getKeyFrame(actual_time);
	}
	
	
	public boolean isFinished() {
		if( actual_time > duration )
			return true;
		else
			return false;
	}
}