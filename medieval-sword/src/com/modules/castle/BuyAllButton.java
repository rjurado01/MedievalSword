package com.modules.castle;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;
import com.game.Constants;
import com.modules.battle.UnitIcon;

public class BuyAllButton extends Group {

  final int SIZE_W = UnitIcon.SIZE_W;
  final int SIZE_H = 88;

  Button button;
  Image icon;
  Image disable;
  Label price_label;

  public BuyAllButton( int price ) {
    createButton();
    createPrice( Integer.toString( price ) );
    createDisableLayer( price );
  }

  private void createButton() {
    button = new Button(
        Assets.getFrame("btnBuyUnits", 1),
        Assets.getFrame("btnBuyUnits", 2) );

    button.width = SIZE_W;
    button.height = SIZE_H;

    addActor( button );
  }

  private void createDisableLayer( int price ) {
    disable = new Image( Assets.getTextureRegion("disabledBackground") );
    disable.width = SIZE_W;
    disable.height = SIZE_H;

    // stop click propagation
    disable.setClickListener( new ClickListener() {
      public void click(Actor actor, float x, float y) {};
    });

    if( price > 0 )
      disable.visible = false;

    addActor( disable );
  }

  private void createPrice( String price ) {
    float x_off = price.length() * Constants.FONT1_WIDTH;

    icon = new Image( Assets.getTextureRegion( "iconPrice" ) );
    icon.x = ( UnitIcon.SIZE_W - 45 - x_off ) / 2;
    icon.y = 1;
    icon.width = 32;
    icon.height = 32;

    price_label = new Label( price, Assets.font2 );
    price_label.x = icon.x + 40;
    price_label.y = 6;

    addActor( icon );
    addActor( price_label );
  }

  public void setClickAction( ClickListener action ) {
    button.setClickListener( action );
  }

  public void disable() {
    disable.visible = true;
  }

  public void enable() {
    disable.visible = false;
  }

  public void updatePrice( int new_price) {
    price_label.setText( Integer.toString( new_price ) );

    if( new_price > 0 )
      disable.visible = false;
    else
      disable.visible = true;
  }
}
