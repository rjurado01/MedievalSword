package com.me.modules.battle;

import java.util.ArrayList;
import java.util.Hashtable;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.mygdxgame.Assets;
import com.me.mygdxgame.Unit;

public class Unit1 extends Unit{
	
	static final int TOTAL_LIFE = 3;
	static final int TOTAL_SHIELD = 3;
	static final int TOTAL_RANGE = 3;
	static final int TOTAL_VELOCITY = 3;
	static final int TOTAL_DAMAGE = 3;
	static final int TOTAL_MOVILITY = 2;

	public Unit1( int number ) {
		super( 35f, 35f );
		
		initial_life = 		8;
		initial_shield = 	3;
		initial_range = 	0;
		initial_velocity = 	3;
		initial_damage = 	10;
		initial_mobility =  6;
		
		this.number = number;
		this.name = "unit1";
		
		initActualValues();
		
		loadTextures();
		loadAnimations();
	}
	
	public void loadTextures() {
		textures = new Hashtable<String, TextureRegion>();
		
		textures.put( "normal_xr", Assets.getTexture(name) );
		
		TextureRegion aux = new TextureRegion( Assets.getTexture(name));
		aux.flip(true, false);
		
		textures.put( "normal_xl", aux );
	}
	
	public void loadAnimations() {
		// Right run animation
		ArrayList<TextureRegion> runFramesXR = new ArrayList<TextureRegion>();
		
		for( int i = 1; i < 3; i++ )
			runFramesXR.add( Assets.getFrame( "unit1", i ) );
		
		Animation run_xr = new Animation( 0.4f, runFramesXR );
		run_xr.setPlayMode( Animation.LOOP );
		
		animations.put( RUN_XR, run_xr );
		
		// Left run animation
		ArrayList<TextureRegion> runFramesXL = new ArrayList<TextureRegion>();
		
		for( int i = 1; i < 3; i++ ) {
			runFramesXL.add( Assets.getFlipFrame( "unit1", i ) );
		}
		
		Animation run_xl = new Animation( 0.4f, runFramesXL );
		run_xl.setPlayMode( Animation.LOOP );
		
		animations.put( RUN_XL, run_xl );
	}	
}
