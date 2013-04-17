package com.modules.map;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdxgame.Army;
import com.mygdxgame.Assets;
import com.utils.CustomAnimation;
import com.utils.Vector2i;

public class HeroTop {
	Hero hero;
	HeroView view;
	int color;
	
	ArrayList<CustomAnimation> actions_queue;
	Army army;
	SquareTerrain square;
	
	public HeroTop( Hero hero, int color ) {
		this.hero = hero;
		this.color = color;

		actions_queue = new ArrayList<CustomAnimation>();
		view = new HeroView( hero.getSize() );
		view.setRegion( hero.textures.get( "normal3" + color ) );
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
	
	public Actor getView() {
		return view;
	}
}
