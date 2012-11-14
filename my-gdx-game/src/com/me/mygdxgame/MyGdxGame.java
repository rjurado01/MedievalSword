package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.me.modules.battle.BattleScreen;

public class MyGdxGame  extends Game {

	Screen menuScreen;
	Screen mapScreen;
	Screen castleScreen;
	Screen battleScreen;
	
	@Override
	public void create() {
		//Assets.load();
		
		Assets assets = new Assets();
		
		battleScreen = new BattleScreen();
		
		//inputGame = new InputGame((GameScreen) gameScreen);
		
		//Gdx.input.setInputProcessor(inputMenu);
		setScreen(battleScreen);
	}
	
	/*public void changeScreen(int op) {
		if(op == 0) {
			Gdx.input.setInputProcessor(inputMenu);
			setScreen(menuScreen);
		}
		else if(op == 1) {
			Gdx.input.setInputProcessor(inputGame);
			setScreen(gameScreen);
			//setScreen(pruebaScreen);
		}
	}*/
}
