package com.races.humans.units;

import com.game.Constants;
import com.game.Stack;
import com.game.Unit;
import com.utils.CallBack;
import com.utils.CustomAnimation;

/**
 * Villager unit
 */
public class Villager extends Unit{

	/* ANIMATIONS */
	public static final String WALK = "run";
	public static final String ATTACK = "attack";


	public Villager() {
		life 	 = 10;
		defense  = 5;
		range 	 = 0;
		damage 	 = 7;
		mobility = 6;
		price = 60;

		id = Constants.VILLAGER;

		map_width = 80;
		map_height = 80;

		enable_description.put("en", "You need to build the Farm.");
		enable_description.put("es", "Necesitas construir la granja.");
		name.put("en", "Villager");
		name.put("es", "Aldeano");

		loadTextures();
		loadAnimations();
	}

	public void loadAnimations() {
		int [] frames1 = { 1, 2 };
		int [] frames2 = { 1, 3, 1 };

		// Load animation for all orientations and all colors
		for( int orientation = 0; orientation < Constants.N_ORIENTATIONS; orientation++ )
			for( int color = 0; color < Constants.N_COLORS; color++ ) {
				loadAnimation( WALK, frames1, orientation, color, true, 0.4f );
				loadAnimation( ATTACK, frames2, orientation, color, false, 0.2f );
			}
	}

	/**
	 * Add walk action to actions queue of stack
	 */
	public void walkAction( Stack stack, int orientation ) {
		stack.addAction( new CustomAnimation(
				getAnimation( WALK, orientation, stack.getColor() ), 0.8f, null ) );
	}

	/**
	 * Add attack action to actions queue of stack
	 */
	public void attackAction( Stack stack, int orientation, CallBack callback ) {
		stack.addAction( new CustomAnimation(
				getAnimation( ATTACK, orientation, stack.getColor() ), 1, callback ) );
	}
}
