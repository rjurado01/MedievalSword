package com.me.modules.battle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.me.mygdxgame.Assets;
import com.me.mygdxgame.Object;

public class BattlePanel {
	static final int NOTHING = -1;
	static final int SHIELD = 0;
	static final int BOOK = 1;
	
	Object shield;
	Object magic_book;
	
	List<Object> objects_list = new ArrayList<Object>();
	
	public BattlePanel() {
		shield = new Object( new Vector2( 80, 5 ), 40f, 30f );
		magic_book = new Object( new Vector2( 120, 5 ), 40f, 30f );
		
		shield.setActualTexture( Assets.getTexture( "shield" ) );
		magic_book.setActualTexture( Assets.getTexture( "magicBook" ) );
		
		objects_list.add( shield );
		objects_list.add( magic_book );
	}
	
	public boolean isShieldTouch(  ) {
		return false;
	}
	
	public List<Object> getObjects() {
		return objects_list;
	}
	
	public int getTouch( Vector2 touch ) {
		if( shield.getBounds().contains( touch.x, touch.y) )
			return SHIELD;
		else
			return NOTHING;
	}
}
