package com.races.humans.units;

import com.game.Constants;
import com.game.Stack;
import com.game.Unit;
import com.modules.battle.Arrow;
import com.utils.CallBack;
import com.utils.CustomAnimation;

/**
 * Archer Unit ( distance unit )
 */
public class Archer extends Unit {

  /* ANIMATIONS */
  public static final String PREPARE = "prepare";
  public static final String SHOOT = "shoot";
  public static final String WALK = "walk";

  /* SIZE */
  public static final int HEIGHT = 90;
  public static final int WIDTH = 90;

  Arrow arrow = null;
  float prepare_time;
  float shoot_time;


  public Archer() {
    life 	 = 10;
    defense  = 3;
    range 	 = 4;
    damage 	 = 8;
    mobility = 4;
    price = 100;

    id = Constants.ARCHER;

    map_width = HEIGHT;
    map_height = WIDTH;

    enable_description.put("en", "You need to build the Archery tower.");
    enable_description.put("es", "Necesitas construir la Torre de arqueros.");
    name.put("en", "Archer");
    name.put("es", "Arquero");

    loadTextures();
    loadAnimations();
  }

  public void loadAnimations() {
    int [] frames1 = { 10, 11 };
    int [] frames2 = { 12, 13, 1 };
    int [] frames3 = { 1, 2, 1, 3 };

    // load animation for all orientations and in all colors
    for( int orientation = 0; orientation < Constants.N_ORIENTATIONS; orientation++ )
      for( int color = 0; color < Constants.N_COLORS; color++ ) {
        loadAnimation( PREPARE, frames1, orientation, color, false, 0.3f );
        loadAnimation( SHOOT, frames2, orientation, color, false, 0.3f );
        loadAnimation( WALK, frames3, orientation, color, true, 0.25f );
      }

    walk_time = 1f;
    prepare_time = 1f;
    shoot_time = 0.9f;
  }

  /**
   * Add walk action to actions queue of stack
   */
  public void walkAction( Stack stack, int orientation ) {
    stack.addAction( new CustomAnimation(
      getAnimation( WALK, orientation, stack.getColor() ),
      walk_time, null, "unit_walk" )
    );
  }

  /**
   * Add attack action to actions queue of stack.
   * This action is composed by two actions:
   * 	- prepare arc and shoot arrow
   *  - return to initialize position
   */
  public void attackAction( Stack stack, int orientation, CallBack callback  ) {
    // prepare the arc and throw arrow
    stack.addAction( new CustomAnimation(
      getAnimation( PREPARE, orientation, stack.getColor() ),
      prepare_time, callback, null )
    );

    // go back to original position
    stack.addAction( new CustomAnimation(
      getAnimation( SHOOT, orientation, stack.getColor() ),
      shoot_time, null, "arrow" )
    );
  }
}
