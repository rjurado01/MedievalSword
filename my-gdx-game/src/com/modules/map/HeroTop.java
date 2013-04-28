package com.modules.map;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdxgame.Army;
import com.mygdxgame.Constants;
import com.mygdxgame.Stack;
import com.utils.CustomAnimation;

public class HeroTop {
	Hero hero;
	HeroView view;
	int color;
	int orientation;
	
	ArrayList<CustomAnimation> actions_queue;
	Army army;
	SquareTerrain square;
	
	public HeroTop( Hero hero, int color ) {
		this.hero = hero;
		this.color = color;

		actions_queue = new ArrayList<CustomAnimation>();
		view = new HeroView( hero.getSize() );
		view.setRegion( hero.textures.get( "normal3" + color ) );
		army = new Army();
		//view.setRegion( Assets.getTextureRegion("HeroOne01"));
	}
	
	public void setPosition( Vector2 position ) {
		view.setPosition( position );
	}
	
	public int getDefense() {
		return hero.defense;
	}

	public void setDefense(int defense) {
		hero.defense = defense;
	}

	public int getAttack() {
		return hero.attack;
	}

	public void setAttack(int attack) {
		hero.attack = attack;
	}

	public int getMobility() {
		return hero.mobility;
	}

	public void setMobility(int mobility) {
		hero.mobility = mobility;
	}
	
	public Army getArmy() {
		return army;
	}
	
	public void setSquareTerrain( SquareTerrain square ) {
		this.square = square;
		this.view.setPosition( square.getPosition() );
	}
	
	public SquareTerrain getSquareTerrain() {
		return square;
	}
	
	public Actor getView() {
		return view;
	}
	
	public void updateActions( float time ) {
		if( actions_queue.size() > 0 ) {
			updateCurrentAction( time );
			updateActionFrame();
		}
	}
	
	private void updateCurrentAction( float time ) {
		if( actions_queue.get( 0 ).isFinished() )
			actions_queue.remove(0);
		else
			actions_queue.get( 0 ).increaseTime( time );
	}
	
	private void updateActionFrame() {
		if( actions_queue.size() > 0 )
			view.setRegion( actions_queue.get(0).getCurrentFrame() );
		//else
		//	setFrameSide();
	}
	
	/*public void setFrameSide() {
		orientation = battle_side == Constants.LEFT_SIDE ? Constants.XR : Constants.XL;
		view.setFrame( unit.getTexture( "normal" + orientation + color ) );
	}*/
	
	public void addWalkAction( int orientation ) {
		hero.walkAction( this, orientation );
	}
	
	public void addAction( CustomAnimation action ) {
		actions_queue.add( action );
	}
	
	public int getColor() {
		return color;
	}
	
	public void addStack( Stack stack ) {
		army.addStack( stack );
	}
}
