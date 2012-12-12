package com.me.mygdxgame;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.me.modules.battle.BattleController;
import com.me.modules.battle.IndicatorUnits;
import com.me.modules.battle.SquareBoard;

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
	
	/* Textures */
	String actual_texture;
	protected Map<String, TextureRegion> textures;
	
	/* Animations */
	protected Hashtable<String, Animation> animations  = new Hashtable<String, Animation>();
	
	protected ArrayList<CustomAnimation> actions_queue;
	
	float animation_init_time;
	float animation_actual_time;
	float animation_duration;
	
	Image unit_image;
	IndicatorUnits indicator;
	
	/**
	 * Class constructor
	 * @param square
	 * @param width
	 * @param height
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
	
	public abstract void loadAnimations();
	
	public void initActualValues() {
		actual_life = initial_life;
		actual_shield = initial_shield;
		actual_range = initial_range;
		actual_mobility = initial_mobility;
		actual_velocity = initial_velocity;
		actual_damage = initial_damage;
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
	
	public void setOrientation( int orientation ) {
		this.orientation = orientation;

		if( orientation == XR ) {
			actual_texture = "normal_xr";
			indicator.x = SquareBoard.SIZE_W - indicator.width;
		}
		else {
			actual_texture = "normal_xl";
			indicator.x = 0;
		}

		unit_image.setRegion( textures.get( actual_texture ) );
	}
	
	public TextureRegion getFrameAnimation(String animation, int time) {
		return animations.get(animation).getKeyFrame(time);
	}
	
	public int getAttackDamage() {
		return number * actual_damage;
	}
	
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
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public void addAction(String animation_name, float duration, BattleController battleController) {		
		actions_queue.add( new CustomAnimation( animations.get(animation_name), duration) );
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
	
	public void loadAnimation( String animation_name, int [] nframes, boolean flip, boolean loop, float time ) {
		ArrayList<TextureRegion> frames = new ArrayList<TextureRegion>();
		
		for( int i = 0; i < nframes.length; i++ ) {
			if( flip )
				frames.add( Assets.getFlipFrame( name, nframes[i] ) );
			else
				frames.add( Assets.getFrame( name, nframes[i] ) );
		}
		
		Animation animation = new Animation( time, frames );
		
		if( loop )
			animation.setPlayMode( Animation.LOOP );
		
		animations.put( animation_name, animation );
	}
	
	public abstract void walkAction();

	public abstract void attackAction();
}
