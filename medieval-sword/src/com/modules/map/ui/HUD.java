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
import com.modules.map.heroes.CreaturesGroup;
import com.modules.map.heroes.HeroTop;
import com.modules.map.terrain.Terrain;

/**
 * Show HUD in the map screen at left
 */
public class HUD extends Group {

	final int GOLD_INDICATOR = 0;
	final int WOOD_INDICATOR = 1;
	final int STONE_INDICATOR = 2;

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

	Map<Integer, ResourceIndicator> resource_indicators;

	public HUD( Terrain terrain ) {
		this.x = 0;
		this.y = 0;
		this.width = Constants.HUD_WIDTH;
		this.height = Constants.HUD_HEIGHT;

		loadBackground();
		loadMiniMap( terrain );
		loadButtons();
		loadIndicators();
		loadInfoBoxes();
	}

	private void loadBackground() {
		background = new Image(Assets.getTextureRegion( "menu" ) );
		background.height = height;
		background.width = width;

		addActor( background );
	}

	private void loadMiniMap( Terrain terrain ) {
		mini_map = new MiniMap( stage, terrain );
		addActor( mini_map );
	}

	private void loadButtons() {
		system = new Button(
				Assets.getTextureRegion( "options" ),
				Assets.getTextureRegion( "number" ) );

		system.height = 30;
		system.width = 32;
		system.x = 5;
		system.y = 70;

		system.setClickListener( new ClickListener() {

			@Override
			public void click(Actor actor, float x, float y) {
				System.out.println("System");
			}
		});

		game = new Button(
				Assets.getTextureRegion( "stats" ),
				Assets.getTextureRegion( "number" ) );

		game.height = 30;
		game.width = 32;
		game.x = 42;
		game.y = 70;

		addActor( system );
		addActor( game );

		loadPassTurnButton();
	}

	private void loadPassTurnButton() {
		pass_turn = new Button(
				Assets.getFrame( "passTurn", 2 ),
				Assets.getFrame( "passTurn", 1 ) );

		pass_turn.height = 36;
		pass_turn.width = 60;
		pass_turn.x = 10;
		pass_turn.y = 22;

		pass_turn.setClickListener( new ClickListener() {
			public void click( Actor actor, float x, float y ) {
				MapController.addEvent( MapConstants.TURN, null );
			}
		} );

		day_counter = new Image( Assets.getFrame( "dayCounter", 1 ) );
		day_counter.width = 70;
		day_counter.height = 50;
		day_counter.x = 5;
		day_counter.y = 15;

		addActor( pass_turn );
		addActor( day_counter );
	}

	private void loadIndicators() {
		resource_indicators = new HashMap<Integer, ResourceIndicator>();

		ResourceIndicator gold_indicator = new ResourceIndicator( 5, 206 );
		gold_indicator.setIcon( Assets.getTextureRegion( "goldIcon" ) );
		gold_indicator.updateAmount( 10000 );

		ResourceIndicator wood_indicator = new ResourceIndicator( 5, 188 );
		wood_indicator.setIcon( Assets.getTextureRegion( "woodIcon" ) );
		wood_indicator.updateAmount( 1000 );

		ResourceIndicator stone_indicator = new ResourceIndicator( 5, 170 );
		stone_indicator.setIcon( Assets.getTextureRegion( "stoneIcon" ) );
		stone_indicator.updateAmount( 100 );

		resource_indicators.put( GOLD_INDICATOR, gold_indicator );
		resource_indicators.put( WOOD_INDICATOR, wood_indicator );
		resource_indicators.put( STONE_INDICATOR, stone_indicator );

		addActor( gold_indicator );
		addActor( wood_indicator );
		addActor( stone_indicator );
	}

	private void loadInfoBoxes() {
		info_box_1 = new Image(Assets.getTextureRegion( "content" ) );
		info_box_1.height = 60;
		info_box_1.width = 35;
		info_box_1.x = 5;
		info_box_1.y = 105;

		info_box_1.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				MapController.addEvent( MapConstants.INFO1, null );
			}
		});

		info_box_2 = new Image(Assets.getTextureRegion( "content" ) );
		info_box_2.height = 60;
		info_box_2.width = 35;
		info_box_2.x = 40;
		info_box_2.y = 105;

		info_box_2.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				MapController.addEvent( MapConstants.INFO2, null );
			}
		});

		addActor( info_box_1 );
		addActor( info_box_2 );
	}

	public void updateGold( int new_gold ) {
		resource_indicators.get( GOLD_INDICATOR ).updateAmount( new_gold );
	}

	public void updateWood( int new_wood ) {
		resource_indicators.get( WOOD_INDICATOR ).updateAmount( new_wood );
	}

	public void updateStone( int new_stone ) {
		resource_indicators.get( STONE_INDICATOR ).updateAmount( new_stone );
	}

	public MiniMap getMiniMap() {
		return mini_map;
	}

	public void selectHero(HeroTop selected_hero) {
		if( hero_selected != null )
			hero_selected.remove();

		hero_selected = new Image( selected_hero.getIconTextureRegion() );
		hero_selected.width = 30;
		hero_selected.height = 30;
		hero_selected.x = 8;
		hero_selected.y = 130;

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
		enemy_selected.width = 30;
		enemy_selected.height = 30;
		enemy_selected.x = 38;
		enemy_selected.y = 130;

		addActor( enemy_selected );
	}

	public void unselectEnemy() {
		if( enemy_selected != null ) {
			enemy_selected.remove();
			enemy_selected = null;
		}
	}

	public void passTurn( int day ) {
		day_counter.setRegion( Assets.getFrame( "dayCounter", day ) );
	}

	public void selectCastle( TopCastle castle ) {
		if( enemy_selected != null )
			enemy_selected.remove();

		enemy_selected = new Image( castle.getIconTextureRegion() );
		enemy_selected.width = 30;
		enemy_selected.height = 30;
		enemy_selected.x = 38;
		enemy_selected.y = 130;

		addActor( enemy_selected );
	}
}
