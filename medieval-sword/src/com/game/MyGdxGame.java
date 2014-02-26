package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.home.HomeScreen;
import com.level.Level;
import com.modules.battle.BattleScreen;
import com.modules.map.MapScreen;

/**
 * Principal class that create and change between screens.
 * Also load assets when start.
 */
public class MyGdxGame extends Game {

	Screen homeScreen;
	Screen mapScreen;
	Screen battleScreen;

	Level level;

	public void create() {
		Assets.load();

		// we need decide if it is new level or load saved game
		level = Assets.getLevel( 1 );

		homeScreen = new HomeScreen( this );
		setScreen( homeScreen );
	}

	public void changeScreen( int screen ) {
		switch ( screen ) {
			case Constants.HOME_SCREEN:
				setScreen( homeScreen );
				break;
			case Constants.MAP_SCREEN:
				mapScreen = new MapScreen( this, level );
				setScreen( mapScreen );
				break;
			case Constants.BATTLE_SCREEN:
				setScreen( battleScreen );
				break;
		}
	}

	public void throwBattleScreen( Army player_army, Army enemy_army ) {
		Assets.pauseMusic( Constants.MUSIC_MAP );
		Assets.playMusic( Constants.MUSIC_BATTLE );
		battleScreen = new BattleScreen( this, player_army, enemy_army );
		setScreen( battleScreen );
	}

	public void returnToMapScreen() {
		Assets.stopMusic( Constants.MUSIC_BATTLE );
		Assets.playMusic( Constants.MUSIC_MAP );
		setScreen( mapScreen );
	}
}
