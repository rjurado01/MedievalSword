package com.modules.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdxgame.Assets;
import com.mygdxgame.Constants;
import com.utils.Vector2i;

public abstract class Hero {
	protected String name;
	
	/* Attributes */
	protected int defense;
	protected int attack;
	protected int power;
	protected int mobility;
	
	protected Vector2i size;

	protected Map<String, TextureRegion> textures;
	protected Map<String, Animation> animations  = new HashMap<String, Animation>();
	

	public Hero() {}
	
	public abstract void loadAnimations();
	
	public abstract void walkAction( HeroTop heroTop, int orientation );
	
	/**
	 * Load static Hereo textures for all orientations
	 */
	public void loadTextures() {
		textures = new HashMap<String, TextureRegion>();
		
		for( int orientation = 0; orientation < Constants.MAP_ORIENTATIONS; orientation++ )
			for( int color = 0; color < Constants.N_COLORS; color++ )
				textures.put( "normal" + orientation + color,
						Assets.getTextureRegion( name + orientation + color ) );
		
		textures.put( "icon", Assets.getTextureRegion( name + Constants.YD + 1 ));
	}
	
	/**
	 * Load animation to animations map (initialize)
	 * 
	 * @param animation_name 	name of animation
	 * @param nframes 			number of frames texture ( see TexturePacker)
	 * @param orientation 		unit orientation
	 * @param loop 				if animation is loop animation ( true / false )
	 * @param time 				duration of animation
	 */
	public void loadAnimation( String animation_name, int [] nframes, int orientation, 
			int color, boolean loop, float time ) 
	{	
		Animation animation = new Animation( time,
				getAnimationFrames( nframes, orientation, color ) );
		
		if( loop ) animation.setPlayMode( Animation.LOOP );
		
		animations.put( animation_name + orientation + color, animation );
	}
	
	/**
	 * Frame name example: unit01 ( name = unit, orientation = 0 and color = 1 )
	 * @return array with animation textures
	 */
	private ArrayList<TextureRegion> getAnimationFrames(int [] nframes,
			int orientation, int color ) 
	{	
		ArrayList<TextureRegion> frames = new ArrayList<TextureRegion>();
		
		for( int i = 0; i < nframes.length; i++ )
			frames.add( Assets.getFrame( name + orientation + color, nframes[i] ) );
		
		return frames;
	}
	
	public Animation getAnimation( String name, int orientation, int color ) {
		return animations.get( name + orientation + color );
	}
	
	public Vector2i getSize() {
		return size;
	}
	
	public TextureRegion getIconTextureRegion() {
		return textures.get( "icon" );
	}
}
