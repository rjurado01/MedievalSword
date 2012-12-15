package com.me.mygdxgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.modules.battle.IndicatorUnits;
import com.me.modules.battle.SquareBoard;

/**
 * Represent general unit type.
 * Player can has 'n' number of units of each type.
 */
public abstract class Unit extends Group {

	/* ORIENTATIONS */
	public static final int XR = 0;
	public static final int XL = 1;
	
	/* Properties */
	boolean show_number = true;
	protected int orientation;
	
	SquareBoard square;
	
	protected String name;
	protected int number;		// number of unit of this type
	
	/* Initial attributes */
	protected int initial_life;
	protected int initial_shield;
	protected int initial_range;
	protected int initial_mobility;
	protected int initial_velocity;
	protected int initial_damage;
	
	/* Actual attributes */
	int actual_life;
	int actual_shield;
	int actual_range;
	int actual_mobility;
	int actual_velocity;
	int actual_damage;
	
	/* Textures */
	protected Map<String, TextureRegion> textures;
	
	/* Animations */
	protected Map<String, Animation> animations  = new HashMap<String, Animation>();
	
	/* Actions queue */
	protected ArrayList<CustomAnimation> actions_queue;
	
	Image unit_image;
	IndicatorUnits indicator;
	
	/**
	 * Class constructor
	 * @param square
	 * @param width width of texture in battle
	 * @param height height of texture in battle
	 */
	public Unit( float width, float height, int number ) {
		super();
		
		this.width = width;
		this.height = height;

		this.number = number;
		actions_queue = new ArrayList<CustomAnimation>();
		
		unit_image = new Image();
		unit_image.width = width;
		unit_image.height = height;
		
		indicator = new IndicatorUnits( number );
		indicator.y = -12;
		
		this.addActor( unit_image );
		addActor( indicator );
	}
	
	/**
	 * Load battle animations
	 */
	public abstract void loadAnimations();
	
	/**
	 * Define walk action
	 */
	public abstract void walkAction();

	/**
	 * Define attack action
	 */
	public abstract void attackAction();
	
	/**
	 * Initialize actual values to initial values
	 */
	public void initActualValues() {
		actual_life = initial_life;
		actual_shield = initial_shield;
		actual_range = initial_range;
		actual_mobility = initial_mobility;
		actual_velocity = initial_velocity;
		actual_damage = initial_damage;
	}
	
	/**
	 * Update actions and actual frame
	 * @param time current time
	 */
	public void update( float time ) {
		// Update actions
		if( actions_queue.size() > 0 ) {
			if( actions_queue.get( 0 ).isFinished() )
				actions_queue.remove(0);
			else
				actions_queue.get( 0 ).increaseTime( time );
		
			if( actions_queue.size() > 0 )
				unit_image.setRegion( actions_queue.get(0).getCurrentFrame() );
		}
	}
	
	/**
	 * Load animation to animations map
	 * @param animation_name name of animation
	 * @param nframes number of frames texture ( see TexturePacker)
	 * @param orientation unit orientation
	 * @param loop if animation is loop animation ( true / false )
	 * @param time duration of animation
	 */
	public void loadAnimation( String animation_name, int [] nframes, int orientation, boolean loop, float time ) {
		ArrayList<TextureRegion> frames = new ArrayList<TextureRegion>();
		
		for( int i = 0; i < nframes.length; i++ ) {
			if( orientation == XL )
				frames.add( Assets.getFlipFrame( name, nframes[i] ) );
			else
				frames.add( Assets.getFrame( name, nframes[i] ) );
		}
		
		Animation animation = new Animation( time, frames );
		
		if( loop )
			animation.setPlayMode( Animation.LOOP );
		
		animations.put( animation_name + orientation, animation );
	}
	
	/**
	 * Receive damage from enemy unit
	 * @param damage
	 * @return false if unit dead or true otherwise
	 */
	public boolean receiveDamage(int damage) {
		if( damage > ( actual_life + actual_shield ) ) {
			number -= damage / ( initial_life + actual_shield );

			if( number > 0 )
				actual_life = damage % ( initial_life + actual_shield );
			else
				return false;
		}
		else if ( damage > actual_shield ) {
			actual_life -= ( damage - actual_shield );
		}
		
		indicator.updateTextNumber( number );
		
		return true;
	}
	
	/**
	 * Get specific animation
	 * @param name animation name
	 * @param orientation unit orientation
	 * @return animation
	 */
	public Animation getAnimation( String name, int orientation ) {
		return animations.get( name + orientation );
	}
	
	/**
	 * Set orientation and update battle texture
	 * @param orientation
	 */
	public void setOrientation( int orientation ) {
		this.orientation = orientation;

		if( orientation == XR ) {
			unit_image.setRegion( textures.get( "normal_xr" ) );
			indicator.x = SquareBoard.SIZE_W - indicator.width;
		}
		else {
			unit_image.setRegion( textures.get( "normal_xl" ) );
			indicator.x = 0;
		}
	}
	
	/**
	 * Get animation frame in this time
	 * @param animation
	 * @param time
	 * @return return texture region of animation
	 */
	public TextureRegion getFrameAnimation(String animation, int time) {
		return animations.get(animation).getKeyFrame(time);
	}
	
	/**
	 * Get damage make by all units of this type
	 * @return damage
	 */
	public int getAttackDamage() {
		return number * actual_damage;
	}
	
	public int getActualMovility() {
		return actual_mobility;
	}

	public void setActualMovility(int actual_mobility) {
		this.actual_mobility = actual_mobility;
	}

	public Vector2 getPosition() {
		return new Vector2(x, y);
	}
	
	public void setPosition( Vector2 pos ) {
		this.x = pos.x;
		this.y = pos.y;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getActualRange() {
		return actual_range;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public SquareBoard getSquare() {
		return square;
	}
	
	public void setSquare(SquareBoard sq, int id ) {
		square = sq;
		square.setStatus( id );
	}
	
	public boolean showNumber() {
		return show_number;
	}
	
	public void setShowNumber(boolean x) {
		indicator.visible = x;
	}
}
