package com.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Class that let "Tween Engine" move Images
 */
public class ImageAlphaAccessor implements TweenAccessor<Image> {

  public static final int POSITION_X = 1;
  public static final int POSITION_Y = 2;
  public static final int POSITION_XY = 3;

  public int getValues( Image target, int tweenType, float[] returnValues ) {
    switch (tweenType) {
      case POSITION_X: returnValues[0] = target.color.a; return 1;
      case POSITION_Y: return -1;
      case POSITION_XY: return -1;
      default: assert false; return -1;
    }
  }

  public void setValues( Image target, int tweenType, float[] newValues ) {
    switch (tweenType) {
      case POSITION_X: target.color.a = newValues[0]; break;
      default: assert false; break;
    }
  }
}
