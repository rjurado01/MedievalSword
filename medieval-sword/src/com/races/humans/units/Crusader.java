package com.races.humans.units;

import com.game.Constants;
import com.game.Stack;
import com.game.Unit;
import com.utils.CallBack;

public class Crusader extends Unit {

	public Crusader() {
		life 	 = 30;
		defense  = 10;
		range 	 = 0;
		damage 	 = 18;
		mobility = 5;
		price = 230;

		id = Constants.CRUSADER;

		map_width = 80;
		map_height = 80;

		enable_description.put("en", "You need to build the Barracks.");
		enable_description.put("es", "Necesitas construir las barracas.");
		name.put("en", "Crusader");
		name.put("es", "Crusado");

		loadTextures();
		loadAnimations();
	}

	@Override
	public void loadAnimations() {
		// TODO Auto-generated method stub

	}

	@Override
	public void walkAction(Stack stack, int orientation ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attackAction(Stack stack, int orientation, CallBack callback) {
		// TODO Auto-generated method stub

	}
}
