package com.me.units;

import java.util.HashMap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.modules.battle.BattleController;
import com.me.modules.battle.SquareBoard;
import com.me.mygdxgame.CustomAnimation;
import com.me.mygdxgame.Assets;
import com.me.mygdxgame.Unit;
import com.me.utils.CallBack;

/**
 * Villager unit ( malee unit )
 */
public class Villager extends Unit{
	
	/* ANIMATIONS */
	public static final String WALK = "run";	
	public static final String ATTACK = "attack";
	
	/**
	 * Class constructor
	 * @param number number of units of this type
	 * @param player player code
	 */
	public Villager( int number, int player ) {
		super( 35f, 35f, number );

		initial_life = 		8;
		initial_shield = 	3;
		initial_range = 	0;
		initial_velocity = 	3;
		initial_damage = 	5;
		initial_mobility =  6;
		
		if( player == SquareBoard.UNIT_P1 )
			this.name = "P1Piqueman";
		else
			this.name = "P2Piqueman";
		
		initActualValues();
		
		loadTextures();
		loadAnimations();
	}
	
	/**
	 * Load Village textures
	 */
	public void loadTextures() {
		textures = new HashMap<String, TextureRegion>();
		
		textures.put( "normal_xr", Assets.getTextureRegion( name ) );
		textures.put( "normal_xl", Assets.getFlipTextureRegion( name ) );
	}
	
	/**
	 * Load Village animations
	 */
	public void loadAnimations() {
		int [] frames1 = { 1, 2 };
		
		loadAnimation( WALK, frames1, XR, true, 0.4f );
		loadAnimation( WALK, frames1, XL, true, 0.4f );
		
		int [] frames2 = { 1, 3, 1 };
		
		loadAnimation( ATTACK, frames2, XR, false, 0.2f );
		loadAnimation( ATTACK, frames2, XL, false, 0.2f );
	}

	/**
	 * Add walk action to actions queue
	 */
	public void walkAction() {	
		actions_queue.add( new CustomAnimation(
				getAnimation( WALK, orientation ), 0.8f, null ) );
	}

	/**
	 * Add attack action to actions queue
	 */
	public void attackAction() {
		
		CallBack callback = new CallBack() {
			
			public void completed() {
				BattleController.attack();			
				BattleController.passTurn();
			}
		};
		
		actions_queue.add( new CustomAnimation(
				getAnimation( ATTACK, orientation ), 1, callback) );
	}	
}
