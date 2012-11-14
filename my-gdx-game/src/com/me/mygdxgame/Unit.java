package com.me.mygdxgame;

import java.util.ArrayList;
import java.util.Hashtable;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.modules.battle.SquareBoard;

public abstract class Unit extends Object {

	/* ORIENTATIONS */
	static final int XR = 0;
	static final int XL = 1;
	
	/* ANIMATIONS */
	public static final String RUN_XR = "run_xr";
	public static final String RUN_XL = "run_xl";
	
	protected String name;
	protected int number;		// number of unit of this type
	
	/* Battle Attributes */
	int actual_life;
	int actual_shield;
	int actual_range;
	int actual_mobility;
	int actual_velocity;
	int actual_damage;
	
	protected int initial_life;
	protected int initial_shield;
	protected int initial_range;
	protected int initial_mobility;
	protected int initial_velocity;
	protected int initial_damage;
	
	/* Animations */
	protected String actual_texture;
	protected Hashtable<String, TextureRegion> textures;
	protected Hashtable<String, Animation> animations  = new Hashtable<String, Animation>();
	
	ArrayList<Action> actions_queue;
	ArrayList<Vector2> movements;
	
	float animation_init_time;
	float animation_actual_time;
	float animation_duration;
	
	boolean show_number = true;
	int orientation;
	
	SquareBoard square;
	
	/**
	 * Class constructor
	 * @param square
	 * @param width
	 * @param height
	 */
	public Unit( float width, float height ) {
		super( width, height );
		
		number = 1;
		actions_queue = new ArrayList<Action>();
	}
	
	public abstract void loadAnimations();
	
	public void initActualValues() {
		actual_life = initial_life;
		actual_shield = initial_shield;
		actual_range = initial_range;
		actual_mobility = initial_mobility;
		actual_velocity = initial_velocity;
		actual_damage = initial_damage;
		
		actual_texture = "normal_xr";
	}
	
	public int getActualMovility() {
		return actual_mobility;
	}

	public void setActualMovility(int actual_mobility) {
		this.actual_mobility = actual_mobility;
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
	
	public void setOrientation( int orientation ) {
		this.orientation = orientation;
		
		if( orientation == XR )
			actual_texture = "normal_xr";
		else
			actual_texture = "normal_xl";
	}
	
	public int getOrientation() {
		return orientation;
	}
	
	public TextureRegion getFrameAnimation(String animation, int time) {
		return animations.get(animation).getKeyFrame(time);
	}
	
	public TextureRegion getRenderFrame(float time) {
		if(actions_queue.size() > 0) {
			return actions_queue.get(0).getCurrentFrame();
		}
		else
			return textures.get( actual_texture );
	}
	
	
	public int getAttackDamage() {
		return number * actual_damage;
	}
	
	public boolean receiveDamage(int damage) {
		if(damage > actual_life) {
			number -= damage / initial_life;

			if( number > 0 )
				actual_life = damage % initial_life;
			else
				return false;
		}
		else {
			actual_life -= damage;
		}
		
		return true;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public void addAction(String animation_name, float duration) {		
		actions_queue.add( new Action( animations.get(animation_name), duration) );
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
		show_number = x;
	}
	
	public void update( float time ) {
		// Update actions
		if( actions_queue.size() > 0 ) {
			if( actions_queue.get( 0 ).isFinished() )
				actions_queue.remove(0);
			else
				actions_queue.get( 0 ).increaseTime( time );
		}
	}
}
