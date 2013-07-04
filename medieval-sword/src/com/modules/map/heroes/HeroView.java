package com.modules.map.heroes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.utils.Vector2i;

public class HeroView extends Image {
	
	HeroTop hero;
	
	public HeroView( HeroTop hero, Vector2i size ) {
		this.hero = hero;
		this.width = size.x;
		this.height = size.y;
		
		setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) { 
				clicked();
			}
		});
	}
	
	private void clicked() {
		MapController.addEvent( MapConstants.HERO, hero );
	}
	
	public Vector2 getPosition() {
		return new Vector2(x, y);
	}
	
	public void setPosition( Vector2 pos ) {
		this.x = pos.x;
		this.y = pos.y;
	}
}
