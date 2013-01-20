package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.me.modules.battle.BattleScreen;
import com.me.modules.map.MapScreen;

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
		mapScreen = new MapScreen( this );		
		setScreen( mapScreen );
	}
	
	public void changeScreen( int screen ) {
		switch ( screen ) {
			case MAP:
				setScreen( mapScreen );
				break;
			case BATTLE:
				createTestBattleScreen();
				setScreen( battleScreen );
				break;
		}
	}
	
	private void createTestBattleScreen() {
		if( battleScreen == null )
			battleScreen = new BattleScreen( this, 
					((MapScreen) mapScreen).getPlayerArmy(), 
					((MapScreen) mapScreen).getEnemyArmy());
	}
	
}
