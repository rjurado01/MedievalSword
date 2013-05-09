package com.modules.map;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdxgame.Assets;
import com.mygdxgame.Constants;

public class HUD extends Group {
	
	final int GOLD_INDICATOR = 0;
	final int WOOD_INDICATOR = 1;	
	final int STONE_INDICATOR = 2;
	
	MiniMap mini_map;
	
	Image background;
	Image map;
	Button pass_turn;
	Label text;
	Group gold;
	Group stone;
	Group wood;
	
	Image hero;
	Image castle;
	Button system;
	Button game;
	
	Image hero_selected;
	Image enemy_selected;
	
	Map<Integer, ResourceIndicator> resource_indicators;
	
	public HUD( Stage stage, Terrain terrain ) {
		this.stage = stage;		
		this.x = 0;
		this.y = 0;
		this.width = Constants.HUD_WIDTH;
		this.height = Constants.HUD_HEIGHT;
		
		loadBackground();
		loadMiniMap( terrain );
		loadButtons();
		loadIndicators();
		loadHero();
	}

	private void loadBackground() {
		background = new Image(Assets.getTextureRegion( "menu" ) );
		background.height = height;
		background.width = width;
		
		stage.addActor( background );
	}
	
	private void loadMiniMap( Terrain terrain ) {
		mini_map = new MiniMap( stage, terrain );
		stage.addActor( mini_map );
	}
	
	private void loadButtons() {
		system = new Button(
				Assets.getTextureRegion( "options" ), 
				Assets.getTextureRegion( "number" ) );
		
		system.height = 30;
		system.width = 32;
		system.x = 5;
		system.y = 70;
		
		game = new Button(
				Assets.getTextureRegion( "stats" ), 
				Assets.getTextureRegion( "number" ) );
		
		game.height = 30;
		game.width = 32;
		game.x = 42;
		game.y = 70;
		
		pass_turn = new Button(
				Assets.getTextureRegion( "hourglass" ), 
				Assets.getTextureRegion( "number" ) );
		
		pass_turn.height = 50;
		pass_turn.width = 70;
		pass_turn.x = 5;
		pass_turn.y = 15;
		
		pass_turn.setClickListener( new ClickListener() {
			public void click( Actor actor, float x, float y ) {
				System.out.println("pass turn");
			}
		} );
		
		stage.addActor( system );
		stage.addActor( game );
		stage.addActor( pass_turn );	
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
		
		stage.addActor( gold_indicator );
		stage.addActor( wood_indicator );
		stage.addActor( stone_indicator );
	}
	
	private void loadHero() {
		hero = new Image(Assets.getTextureRegion( "content" ) );
		hero.height = 60;
		hero.width = 35;
		hero.x = 5;
		hero.y = 105;
		
		castle = new Image(Assets.getTextureRegion( "content" ) );
		castle.height = 60;
		castle.width = 35;
		castle.x = 40;
		castle.y = 105;
		
		stage.addActor( hero );
		stage.addActor( castle );
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
		hero_selected = new Image( selected_hero.getIconTextureRegion() );
		hero_selected.width = 30;
		hero_selected.height = 30;
		hero_selected.x = 8;
		hero_selected.y = 130;
		
		stage.addActor( hero_selected );
	}
	
	public void unselectHero() {
		hero_selected.remove();
		//stage.addActor( hero_selected );
	}

	public void selectEnemy(CreaturesGroup group) {
		enemy_selected = new Image( group.getIconTextureRegion() );
		enemy_selected.width = 30;
		enemy_selected.height = 30;
		enemy_selected.x = 38;
		enemy_selected.y = 130;
		
		stage.addActor( enemy_selected );		
	}
	
	public void unselectEnemy() {
		enemy_selected.remove();
		enemy_selected = null;
	}
}
