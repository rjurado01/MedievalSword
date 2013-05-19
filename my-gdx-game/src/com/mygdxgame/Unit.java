package com.mygdxgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.utils.CallBack;

/**
 * Represent general unit type.
 * Player can has 'n' number of units of each type.
 */
public abstract class Unit {
	
	protected String name;
	
	/* Attributes */
	public int life;
	protected int defense;
	protected int attack;
	protected int range;
	protected int mobility;
	protected int damage;
	
	protected int map_width;
	protected int map_height;

	protected Map<String, TextureRegion> textures;
	protected Map<String, Animation> animations  = new HashMap<String, Animation>();
	

	public Unit() {}
	
	public abstract void loadAnimations();
	
	public abstract void walkAction( Stack stack, int orientation );

	public abstract void attackAction( Stack stack, int orientation, CallBack callback );
	
	/**
	 * Load normal textures of unit ( when unit is stop texture and icon )
	 */
	public void loadTextures() {
		textures = new HashMap<String, TextureRegion>();
		
		loadUnitTextures();
		loadUnitIcons();
	}
	
	/**
	 * Load normal texture ( for all orientations and in all colors ) 
	 */
	private void loadUnitTextures() {
		for( int orientation = 0; orientation < Constants.N_ORIENTATIONS; orientation++ )
			for( int color = 0; color < Constants.N_COLORS; color++ ) 
				textures.put( "normal" + orientation + color,
						Assets.getTextureRegion( name + orientation + color ) );
	}
	
	/**
	 * Load icon ( in all colors ) 
	 */
	private void loadUnitIcons() {
		for( int color = 0; color < Constants.N_COLORS; color++ ) 
			textures.put( "icon" + color, 	Assets.getTextureRegion( name + 0 + color ) );
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
	
	public Image getMapImage( int orientation ) {
		Image image = new Image( textures.get( "normal" + orientation + "0" ) );
		image.width = map_width;
		image.height = map_height;
		return image;
	}

	public TextureRegion getIcon() {
		return textures.get( "normal10" );
	}
	
	public Animation getAnimation( String name, int orientation, int color ) {
		return animations.get( name + orientation + color );
	}
	
	public TextureRegion getFrameAnimation(String animation, int time) {
		return animations.get(animation).getKeyFrame(time);
	}
	
	public TextureRegion getTexture( String name ) {
		return textures.get( name );
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getMobility() {
		return mobility;
	}

	public void setMobility(int mobility) {
		this.mobility = mobility;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
}
