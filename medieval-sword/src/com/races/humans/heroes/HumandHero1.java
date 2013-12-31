package com.races.humans.heroes;

import com.game.Constants;
import com.modules.map.MapConstants;
import com.modules.map.heroes.Hero;
import com.modules.map.heroes.HeroTop;
import com.utils.CustomAnimation;
import com.utils.Vector2i;

public class HumandHero1 extends Hero {

	/* ANIMATIONS */
	public static final String RUN = "run";

	public HumandHero1() {
		defense  = 3;
		attack 	 = 3;
		mobility = 5;
		vision   = 5;

		name = "Blazh";
		size = new Vector2i( 80, 120 );

		loadTextures();
		loadAnimations();
	}

	public void loadAnimations() {
		int [][] frames = { {1, 2, 1, 2}, {1, 2, 1, 2}, {1, 2, 1, 2}, {1, 2, 1, 2} };

		// load animation for all orientations and in all colors
		for( int orientation = 0; orientation < Constants.MAP_ORIENTATIONS; orientation++ )
			for( int color = 0; color < Constants.N_COLORS; color++ )
				loadAnimation( RUN, frames[orientation], orientation, color,
						false, MapConstants.HERO_SQUARE_TIME / 2 );
	}

	/**
	 * Add walk action to actions queue of stack
	 */
	public void walkAction( HeroTop hero, int orientation ) {
		hero.addAction( new CustomAnimation(
				getAnimation( RUN, orientation, hero.getColor() ),
				MapConstants.HERO_SQUARE_TIME - 0.02f, null ) );
	}
}
