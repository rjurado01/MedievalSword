package com.me.modules.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.mygdxgame.CustomAnimation;
import com.me.mygdxgame.Assets;
import com.me.mygdxgame.Unit;
import com.me.utils.CallBack;

public class Unit1 extends Unit{
	
	/* ANIMATIONS */
	public static final String WALK_XR = "run_xr";
	public static final String WALK_XL = "run_xl";
	
	public static final String ATTACK_XR = "attack_xr";
	public static final String ATTACK_XL = "attack_xl";
	
	/**
	 * Class constructor
	 * @param number
	 * @param player
	 */
	public Unit1( int number, int player ) {
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
	
	public void loadTextures() {
		textures = new HashMap<String, TextureRegion>();
		
		textures.put( "normal_xr", Assets.getTextureRegion( name ) );
		textures.put( "normal_xl", Assets.getFlipTextureRegion( name ) );
	}
	
	public void loadAnimations() {
		int [] frames1 = { 1, 2 };
		
		loadAnimation( WALK_XR, frames1, false, true, 0.4f );
		loadAnimation( WALK_XL, frames1, true, true, 0.4f );
		
		int [] frames2 = { 1, 3, 1 };
		
		loadAnimation( ATTACK_XR, frames2, false, false, 0.2f );
		loadAnimation( ATTACK_XL, frames2, true, false, 0.2f );
	}

	/**
	 * Add walk action to actions queue
	 * @param direction animation direction
	 */
	public void walkAction() {
		CustomAnimation aux;
		
		if( orientation == Unit.XR )
			aux = new CustomAnimation( animations.get( WALK_XR ), 0.8f );
		else
			aux = new CustomAnimation( animations.get( WALK_XL ), 0.8f );
		
		actions_queue.add( aux );
	}

	/**
	 * Add attack action to actions queue
	 */
	public void attackAction() {
		CustomAnimation aux;
		
		if( orientation == Unit.XR )
			aux = new CustomAnimation( animations.get( ATTACK_XR ), 1);
		else
			aux = new CustomAnimation( animations.get( ATTACK_XL ), 1);
		
		aux.addCallBack( new CallBack() {
			
			public void completed() {
				BattleController.attack();
				
				BattleController.passTurn();
			}
		});
		
		actions_queue.add( aux );
	}	
}
