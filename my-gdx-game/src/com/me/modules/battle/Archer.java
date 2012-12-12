package com.me.modules.battle;

import java.util.Hashtable;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.mygdxgame.Assets;
import com.me.mygdxgame.ImageAccessor;
import com.me.mygdxgame.CustomAnimation;
import com.me.mygdxgame.Unit;
import com.me.utils.CallBack;

public class Archer extends Unit {
	
	/* ANIMATIONS */
	public static final String PREPARE_XR = "prepare_xr";
	public static final String PREPARE_XL = "prepare_xl";
	public static final String SHOOT_XR = "shoot_xr";
	public static final String SHOOT_XL = "shoot_xl";
	public static final String WALK_XR = "walk_xr";
	public static final String WALK_XL = "walk_xl";
	
	/* SIZE */
	public static final float HEIGHT = 35;
	public static final float WIDTH = 35;
	
	Arrow arrow = null;

	public Archer( int number, int player ) {
		super( WIDTH, HEIGHT, number );

		initial_life = 		8;
		initial_shield = 	3;
		initial_range = 	3;
		initial_velocity = 	3;
		initial_damage = 	5;
		initial_mobility =  6;
		
		if( player == SquareBoard.UNIT_P1 )
			this.name = "P1Archer";
		else
			this.name = "P2Archer";
		
		initActualValues();
		
		loadTextures();
		loadAnimations();
	}
	
	public void loadTextures() {
		textures = new Hashtable<String, TextureRegion>();
		
		textures.put( "normal_xr", Assets.getTextureRegion( name ) );
		textures.put( "normal_xl", Assets.getFlipTextureRegion( name ) );
	}
	
	public void loadAnimations() {
		int [] frames1 = { 1, 3 };
		
		loadAnimation( PREPARE_XR, frames1, false, false, 0.4f );
		loadAnimation( PREPARE_XL, frames1, true, false, 0.4f );
		
		int [] frames2 = { 1 };
		
		loadAnimation( SHOOT_XR, frames2, false, false, 0.4f );
		loadAnimation( SHOOT_XL, frames2, true, false, 0.4f );
		
		int [] frames3 = { 1, 2 };
		
		loadAnimation( WALK_XR, frames3, false, true, 0.4f );
		loadAnimation( WALK_XL, frames3, true, true, 0.4f );
	}

	@Override
	public void walkAction() {
		CustomAnimation aux;
		
		if( orientation == Unit.XR )
			aux = new CustomAnimation( animations.get( WALK_XR ), 0.8f );
		else
			aux = new CustomAnimation( animations.get( WALK_XL ), 0.8f );
		
		actions_queue.add( aux );		
	}

	@Override
	public void attackAction() {
		CustomAnimation aux;
		
		if( orientation == Unit.XR )
			aux = new CustomAnimation( animations.get( PREPARE_XR ), 1);
		else
			aux = new CustomAnimation( animations.get( PREPARE_XL ), 1);
		
		aux.addCallBack( new CallBack() {
			
			public void completed() {
				throwArrow();
			}
		});
		
		actions_queue.add( aux );
	}	
	
	public void throwArrow() {
		arrow = new Arrow( x + WIDTH / 2, y, orientation, BattleController.stage );
		
		Vector2 destination = BattleController.enemy_square.getPosition();
		destination.y += HEIGHT / 2;

		Tween.to( arrow, ImageAccessor.POSITION_XY, Math.abs( x - destination.x ) / 700 )
	    .target( destination.x, destination.y )
	    .setCallback( new TweenCallback() {
			
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				arrow.remove();
				
				BattleController.attack();
				
				BattleController.passTurn();	
			}
		})
	    .start( BattleController.manager );
		
		if( orientation == Unit.XR )
			actions_queue.add( new CustomAnimation( animations.get( SHOOT_XR ), 0) );
		else
			actions_queue.add( new CustomAnimation( animations.get( SHOOT_XL ), 0) );
	}
}
