package com.races.humands.heroes;

import com.modules.map.Hero;
import com.modules.map.HeroTop;
import com.mygdxgame.Constants;
import com.utils.CustomAnimation;
import com.utils.Vector2i;

public class HumandHero1 extends Hero {
	
	/* ANIMATIONS */
	public static final String RUN = "run";
	
	public HumandHero1() {
		defense  = 3;
		attack 	 = 3;
		mobility = 4;
		
		name = "HeroOne";
		size = new Vector2i( 35, 35 );
		
		loadTextures();
		loadAnimations();
	}

	public void loadAnimations() {
		int [][] frames = { {1, 2}, {1, 2}, {1, 2, 3}, {1, 2, 3} };
		
		// load animation for all orientations and in all colors
		for( int orientation = 0; orientation < Constants.MAP_ORIENTATIONS; orientation++ )
			for( int color = 0; color < Constants.N_COLORS; color++ )
				loadAnimation( RUN, frames[orientation], orientation, color, false, 0.4f );
	}

	/**
	 * Add walk action to actions queue of stack
	 */
	public void walkAction( HeroTop hero, int orientation ) {
		hero.addAction( new CustomAnimation(
				getAnimation( RUN, orientation, hero.getColor() ), 0.8f, null ) );		
	}
}
