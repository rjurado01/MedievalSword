package com.races.humans.units;

import com.game.Constants;
import com.game.Stack;
import com.game.Unit;
import com.utils.CallBack;

/**
 * Knight Unit ( hand-to-hand unit )
 */
public class Knight extends Unit {

  public Knight() {
    life     = 50;
    defense  = 20;
    range    = 0;
    damage   = 30;
    mobility = 7;
    price    = 450;

    id = Constants.KNIGHT;

    map_width = 80;
    map_height = 80;

    enable_description.put("en", "You need to build the Stables.");
    enable_description.put("es", "Necesitas construir los establos.");
    name.put("en", "Knight");
    name.put("es", "Caballero");

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
