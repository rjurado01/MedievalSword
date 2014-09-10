package com.modules.map.ui;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;
import com.game.Constants;
import com.modules.castle.TopCastle;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.modules.map.MapInputProcessor;
import com.modules.map.heroes.CreaturesGroup;
import com.modules.map.heroes.HeroTop;
import com.modules.map.terrain.Terrain;

/**
 * Show the HUD in the map screen at left
 */
public class HUD extends Group {

  final int GOLD_INDICATOR = 0;
  final int WOOD_INDICATOR = 1;
  final int STONE_INDICATOR = 2;

  final float BUTTONS_W = 68;
  final float BUTTONS_H = 68;
  final float BUTTONS_1_X = 24;
  final float BUTTONS_2_X = 99;
  final float BUTTONS_Y = 204;

  final int RI_X = 24;
  final int RI1_Y = 508;
  final int RI2_Y = 465;
  final int RI3_Y = 422;

  final float INFOBOX_W = 68;
  final float INFOBOX_H = 100;
  final int INFOBOX_Y = 295;
  final int INFOBOX_1_X = 24;
  final int INFOBOX_2_X = 99;

  final int INFOBOX_ICON_W = 50;
  final int INFOBOX_ICON_H = 68;
  final int INFOBOX_ICON_X = 10;
  final int INFOBOX_ICON_Y = 15;

  final int DAYS_X = 26;
  final int DAYS_Y = 40;
  final int DAYS_W = 140;
  final int DAYS_H = 140;

  final int TURN_X = 46;
  final int TURN_Y = 60;
  final int TURN_W = 100;
  final int TURN_H = 100;

  final int DISABLE_X = 15;
  final int DISABLE_Y = 20;
  final int DISABLE_W = 162;
  final int DISABLE_H = 544;

  MiniMap mini_map;

  Image background;
  Image map;
  Image day_counter;
  Button pass_turn;
  Label text;
  Group gold;
  Group stone;
  Group wood;

  Image info_box_1;
  Image info_box_2;
  Button system;
  Button game;

  Image hero_selected;
  Image enemy_selected;
  Image disabled_background;

  Map<Integer, ResourceIndicator> resource_indicators;

  public HUD( Terrain terrain ) {
    this.x = 0;
    this.y = 0;
    this.width = Constants.HUD_WIDTH;
    this.height = Constants.HUD_HEIGHT;

    // loadBorders( terrain.getStage() .getCamera().viewportHeight );
    loadBackground();
    loadMiniMap( terrain );
    loadButtons();
    loadIndicators();
    loadInfoBoxes();
  }

  /*private void loadBorders( float viewportHeight ) {
    Image black_left = new Image( Assets.getTextureRegion("black") );
    black_left.x = -200;
    black_left.y = -200;
    black_left.width = 210;
    black_left.height = viewportHeight + 420;

    Image black_right = new Image( Assets.getTextureRegion("black") );
    black_right.x = Constants.SIZE_W;
    black_right.y = -200;
    black_right.width = 200;
    black_right.height = viewportHeight + 400;

    addActor( black_left );
    addActor( black_right );
    }*/

  private void loadBackground() {
    background = new Image( Assets.getTextureRegion( "background" ) );
    background.height = height;
    background.width = width;

    disabled_background = new Image( Assets.getTextureRegion("disabledBackground") );
    disabled_background.height = DISABLE_H;
    disabled_background.width = DISABLE_W;
    disabled_background.x = DISABLE_X;
    disabled_background.y = DISABLE_Y;

    addActor( background );
  }

  private void loadMiniMap( Terrain terrain ) {
    mini_map = new MiniMap( stage, terrain );
    addActor( mini_map );
  }

  private void loadButtons() {
    system = new Button(
        Assets.getFrame( "btnOptions", 1 ),
        Assets.getFrame( "btnOptions", 2 ) );

    system.height = BUTTONS_H;
    system.width = BUTTONS_W;
    system.x = BUTTONS_1_X;
    system.y = BUTTONS_Y;

    system.setClickListener( new ClickListener() {
      public void click(Actor actor, float x, float y) {
        Assets.playSound("button", false);
        MapController.addEvent( MapConstants.SHOW_OPTIONS, null );
      }
    });

    game = new Button(
        Assets.getTextureRegion( "objetive" ),
        Assets.getTextureRegion( "number" ) );

    game.height = BUTTONS_H;
    game.width = BUTTONS_W;
    game.x = BUTTONS_2_X;
    game.y = BUTTONS_Y;

    game.setClickListener( new ClickListener() {
      public void click(Actor actor, float x, float y) {
        Assets.playSound("button", false);
        MapController.addEvent( MapConstants.SHOW_OBJECTIVES, null );
      }
    });

    addActor( system );
    addActor( game );

    loadPassTurnButton();
  }

  private void loadPassTurnButton() {
    pass_turn = new Button(
        Assets.getFrame( "turn", 1 ),
        Assets.getFrame( "turn", 2 ) );

    pass_turn.height = TURN_H;
    pass_turn.width = TURN_W;
    pass_turn.x = TURN_X;
    pass_turn.y = TURN_Y;

    pass_turn.setClickListener( new ClickListener() {
      public void click( Actor actor, float x, float y ) {
        MapController.addEvent( MapConstants.TURN, null );
      }
    } );

    day_counter = new Image( Assets.getFrame( "turnDays", 1 ) );
    day_counter.width = DAYS_H;
    day_counter.height = DAYS_W;
    day_counter.x = DAYS_X;
    day_counter.y = DAYS_Y;

    addActor( pass_turn );
    addActor( day_counter );
  }

  private void loadIndicators() {
    resource_indicators = new HashMap<Integer, ResourceIndicator>();

    ResourceIndicator gold_indicator = new ResourceIndicator( RI_X, RI1_Y );
    gold_indicator.setIcon( Assets.getTextureRegion( "iconGold" ) );
    gold_indicator.updateText( 10000 );

    ResourceIndicator wood_indicator = new ResourceIndicator( RI_X, RI2_Y );
    wood_indicator.setIcon( Assets.getTextureRegion( "iconWood" ) );
    wood_indicator.updateText( 1000 );

    ResourceIndicator stone_indicator = new ResourceIndicator( RI_X, RI3_Y );
    stone_indicator.setIcon( Assets.getTextureRegion( "iconStone" ) );
    stone_indicator.updateText( 100 );

    resource_indicators.put( GOLD_INDICATOR, gold_indicator );
    resource_indicators.put( WOOD_INDICATOR, wood_indicator );
    resource_indicators.put( STONE_INDICATOR, stone_indicator );

    addActor( gold_indicator );
    addActor( wood_indicator );
    addActor( stone_indicator );
  }

  private void loadInfoBoxes() {
    info_box_1 = new Image(Assets.getTextureRegion( "rect" ) );
    info_box_1.height = INFOBOX_H;
    info_box_1.width = INFOBOX_W;
    info_box_1.x = INFOBOX_1_X;
    info_box_1.y = INFOBOX_Y;

    info_box_1.setClickListener( new ClickListener() {
      public void click(Actor actor, float x, float y) {
        MapController.addEvent( MapConstants.INFO1, null );
      }
    });

    info_box_2 = new Image(Assets.getTextureRegion( "rect" ) );
    info_box_2.height = INFOBOX_H;
    info_box_2.width = INFOBOX_W;
    info_box_2.x = INFOBOX_2_X;
    info_box_2.y = INFOBOX_Y;

    info_box_2.setClickListener( new ClickListener() {
      public void click(Actor actor, float x, float y) {
        MapController.addEvent( MapConstants.INFO2, null );
      }
    });

    addActor( info_box_1 );
    addActor( info_box_2 );
  }

  public void updateGold( int new_gold ) {
    resource_indicators.get( GOLD_INDICATOR ).updateText( new_gold );
  }

  public void updateWood( int new_wood ) {
    resource_indicators.get( WOOD_INDICATOR ).updateText( new_wood );
  }

  public void updateStone( int new_stone ) {
    resource_indicators.get( STONE_INDICATOR ).updateText( new_stone );
  }

  public MiniMap getMiniMap() {
    return mini_map;
  }

  public void selectHero(HeroTop selected_hero) {
    if( hero_selected != null )
      hero_selected.remove();

    hero_selected = new Image( selected_hero.getIconTextureRegion() );
    hero_selected.width = INFOBOX_ICON_W;
    hero_selected.height = INFOBOX_ICON_H;
    hero_selected.x = INFOBOX_1_X + INFOBOX_ICON_X;
    hero_selected.y = INFOBOX_Y + INFOBOX_ICON_Y;

    addActor( hero_selected );
  }

  public void unselectHero() {
    if( hero_selected != null ) {
      hero_selected.remove();
      hero_selected = null;
    }
  }

  public void selectEnemy(CreaturesGroup group) {
    if( enemy_selected != null )
      enemy_selected.remove();

    enemy_selected = new Image( group.getIconTextureRegion() );
    enemy_selected.width = 70;
    enemy_selected.height = 70;
    enemy_selected.x = INFOBOX_2_X;
    enemy_selected.y = INFOBOX_Y + INFOBOX_ICON_Y;

    addActor( enemy_selected );
  }

  public void unselectEnemy() {
    if( enemy_selected != null ) {
      enemy_selected.remove();
      enemy_selected = null;
    }
  }

  public void selectCastle( TopCastle castle ) {
    if( enemy_selected != null )
      enemy_selected.remove();

    enemy_selected = new Image( castle.getIconTextureRegion() );
    enemy_selected.width = 50;
    enemy_selected.height = 50;
    enemy_selected.x = INFOBOX_2_X + ( INFOBOX_W - enemy_selected.width ) / 2;
    enemy_selected.y = INFOBOX_Y + ( INFOBOX_H - enemy_selected.height ) / 2;

    addActor( enemy_selected );
  }

  public void passTurn( int day ) {
    day_counter.setRegion( Assets.getFrame( "turnDays", day ) );
  }

  public void disable() {
    addActor( disabled_background );
    MapInputProcessor.deactivateHUD();
  }

  public void enable() {
    removeActor( disabled_background );
    MapInputProcessor.activateHUD();
  }
}
