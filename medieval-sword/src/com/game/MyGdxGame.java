package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.level.Level;
import com.modules.battle.BattleScreen;
import com.modules.map.MapScreen;

/**
 * Principal class that create and change between screens.
 * Also load assets when start.
 */
public class MyGdxGame extends Game {
	
	final int MAP = 1;
	final int BATTLE = 2;

	Screen menuScreen;
	Screen mapScreen;
	Screen castleScreen;
	Screen battleScreen;
	
	
	public void create() {
		Assets.load();
		
		// we need decide if it is new level or load saved game
		Level level = Assets.getLevel( 1 );
		
		mapScreen = new MapScreen( this, level );		
		setScreen( mapScreen );
	}
	
	public void changeScreen( int screen ) {
		switch ( screen ) {
			case MAP:
				setScreen( mapScreen );
				break;
			case BATTLE:
				setScreen( battleScreen );
				break;
		}
	}
	
	public void throwBattleScreen( Army player_army, Army enemy_army ) {
		battleScreen = new BattleScreen( this, player_army, enemy_army );
		setScreen( battleScreen );
	}
	
	public void returnToMapScreen() {
		setScreen( mapScreen );
	}
}
