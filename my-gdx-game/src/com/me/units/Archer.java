package com.me.units;

import java.util.Hashtable;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.modules.battle.Arrow;
import com.me.modules.battle.BattleController;
import com.me.modules.battle.SquareBoard;
import com.me.mygdxgame.Assets;
import com.me.mygdxgame.CustomAnimation;
import com.me.mygdxgame.Unit;
import com.me.utils.CallBack;

/**
 * Archer unit ( distance unit ) 
 */
public class Archer extends Unit {
	
	/* ANIMATIONS */
	public static final String PREPARE = "prepare";
	public static final String SHOOT = "shoot";
	public static final String WALK = "walk";
	
	/* SIZE */
	public static final float HEIGHT = 35;
	public static final float WIDTH = 35;
	
	Arrow arrow = null;

	/**
	 * Class constructor
	 * @param number number of units of this type
	 * @param player player code
	 */
	public Archer( int number, int player ) {
		super( WIDTH, HEIGHT, number );

		initial_life = 		6;
		initial_shield = 	3;
		initial_range = 	3;
		initial_velocity = 	2;
		initial_damage = 	14;
		initial_mobility =  4;
		
		if( player == SquareBoard.UNIT_P1 )
			this.name = "P1Archer";
		else
			this.name = "P2Archer";
		
		initActualValues();
		
		loadTextures();
		loadAnimations();
	}
	
	/**
	 * Load Archer textures
	 */
	public void loadTextures() {
		textures = new Hashtable<String, TextureRegion>();
		
		textures.put( "icon", 		Assets.getTextureRegion( name ) );
		textures.put( "normal_xr", 	Assets.getTextureRegion( name ) );
		textures.put( "normal_xl", 	Assets.getFlipTextureRegion( name ) );
	}
	
	/**
	 * Load Archer animations
	 */
	public void loadAnimations() {
		int [] frames1 = { 1, 3 };
		
		loadAnimation( PREPARE, frames1, XR, false, 0.4f );
		loadAnimation( PREPARE, frames1, XL, false, 0.4f );
		
		int [] frames2 = { 1 };
		
		loadAnimation( SHOOT, frames2, XR, false, 0.4f );
		loadAnimation( SHOOT, frames2, XL, false, 0.4f );
		
		int [] frames3 = { 1, 2 };
		
		loadAnimation( WALK, frames3, XR, true, 0.4f );
		loadAnimation( WALK, frames3, XL, true, 0.4f );
	}

	/**
	 * Add walk action to actions queue
	 */
	public void walkAction( int x_direction ) {
		actions_queue.add( new CustomAnimation(
				getAnimation( WALK, x_direction ), 0.8f, null ) );		
	}

	/**
	 * Add attack action to actions queue
	 */
	public void attackAction() {
		
		CallBack callback = new CallBack() {
			
			public void completed() {
				if( orientation == Unit.XR)
					BattleController.throwArrow( x + WIDTH / 2, y, orientation );
				else
					BattleController.throwArrow( x - WIDTH / 2, y, orientation );
			}
		};
		
		// Prepare the arc
		actions_queue.add( new CustomAnimation(
				getAnimation( PREPARE, orientation ), 1, callback) );
		
		// Shoot and go back to original position
		actions_queue.add( new CustomAnimation(
				getAnimation( SHOOT, orientation ), 0, null) );
	}	
}
