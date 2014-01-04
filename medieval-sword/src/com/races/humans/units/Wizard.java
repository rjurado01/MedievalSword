package com.races.humans.units;

import com.game.Constants;
import com.game.Stack;
import com.game.Unit;
import com.utils.CallBack;

public class Wizard extends Unit {

	public Wizard() {
		life 	 = 40;
		defense  = 16;
		range 	 = 5;
		damage 	 = 20;
		mobility = 5;
		price = 380;

		id = Constants.WIZARD;

		map_width = 80;
		map_height = 80;

		enable_description.put("en", "You need to build the Monastery.");
		enable_description.put("es", "Necesitas construir el monasterio.");
		name.put("en", "Wizard");
		name.put("es", "Hechizero");

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
