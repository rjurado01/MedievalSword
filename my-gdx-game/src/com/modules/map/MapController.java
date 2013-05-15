package com.modules.map;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdxgame.Constants;
import com.mygdxgame.MyGdxGame;
import com.mygdxgame.Player;
import com.utils.HeroViewAccessor;
import com.utils.StackViewAccessor;
import com.utils.Vector2i;

public class MapController {

	/* EVENTS INFO */
	static Object objectEvent = null;
	static int typeEvent = -1;
	
	List<Player> players;
	Terrain terrain;
	TweenManager manager;
	HeroTop selected_hero;
	CreaturesGroup selected_group;
	HeroTop selected_hero_enemy;
	MyGdxGame game;
	HUD hud;
	
	int turn;
	List<Vector2i> path_found;	// show if path have been found 
	SquareTerrain attack_square;
	
	static boolean mutex = false; 	// Semaphore		
	static boolean showInfo = false;
	
	
	public MapController( MyGdxGame game, List<Player> players, Terrain terrain, HUD hud ) {
		this.game = game;
		this.players = players;
		this.terrain = terrain;
		this.hud = hud;
		
		path_found = new ArrayList<Vector2i>();
		manager = new TweenManager();
		Tween.registerAccessor( HeroView.class, new HeroViewAccessor() );
		
		// selected_hero = players.get( 0 ).getHeroSelected();
	}
	
	static void addEvent( int type, Object receiver ) {
		if( typeEvent == Constants.NONE && !mutex ) {
			typeEvent = type;
			objectEvent = receiver;
		}
	}
	
	public void update() {
		manager.update( Gdx.graphics.getDeltaTime() );
		players.get( turn ).update( Gdx.graphics.getDeltaTime() );
		
		checkEvents();
	}
	
	public void checkEvents() {
		if( !mutex )	// check semaphore 
		{
			if( typeEvent == MapConstants.SQUARE && objectEvent != null ) {
				checkSquareEvent( (SquareTerrain) objectEvent );
			}
			else if( typeEvent == MapConstants.HERO && objectEvent != null) {
				checkHeroEvent( (HeroTop) objectEvent );
			}
			else if( typeEvent == MapConstants.CREATURES && objectEvent != null) {
				checkCreaturesGroupEvent( (CreaturesGroup) objectEvent );
			}
			else if( typeEvent == MapConstants.INFO1 && selected_hero != null) {
				showArmyInfoPanel();
			}
			else if( typeEvent == MapConstants.INFO2 && selected_hero != null) {
				showEnemyArmyInfoPanel();
			}
			
			typeEvent = Constants.NONE;
			objectEvent = null;
		}
	}	
	
	public void checkSquareEvent( SquareTerrain square ) {
		if( square.isRoadAvailable() )
			processMoveEvent( square );
		else
			System.out.println("Evento no registrado.");
		
		
		objectEvent = null;
		typeEvent = Constants.NONE;
	}
	
	private void processMoveEvent( SquareTerrain square ) {
		if( players.get( turn ).isHeroSelected() ) {
			if( path_found.size() == 0 )
				checkPath( square.getNumber() );
			else {
				Vector2i end = path_found.get( path_found.size() - 1 );
				
				if( square.getNumber().x == end.x &&  square.getNumber().y == end.y )
					moveSelectedHero();
				else {
					terrain.removePathSelected();
					path_found = new ArrayList<Vector2i>();
					checkPath( square.getNumber() );
				}
			}
		}
	}
	
	private void checkPath( Vector2i destination ) {
		PathFinder path_finder = new PathFinder(
				terrain.getRoadsMatrix( destination ), terrain.SQUARES_X, terrain.SQUARES_Y );
		
		Vector2i origin = players.get( turn ).getHeroSelected().getSquareTerrain().getNumber();
		List<Vector2i> camino = path_finder.findWay( origin, destination );
		
		if( camino.size() > 0 ) {
			path_found = camino;
			terrain.drawPathSelected( path_found );
		}
	}
	
	public void moveSelectedHero() {
		Timeline line = Timeline.createSequence();		
		createMoveHeroLine( line );
		line.start( manager );
	}
	
	/**
	 * Move stack from actual square to end square of board.wayList
	 * Adds a walk action for each element of wayList ( for each square )
	 * @param line line of animations
	 */
	public void createMoveHeroLine( Timeline line ) {
		mutex = true;
		
		Vector2i last_square_number = 
				players.get( turn ).getHeroSelected().getSquareTerrain().getNumber();
		
		terrain.removeFirstPathElement();
		
		for( Vector2i next_square_number : path_found ) {
			Vector2 next_position = terrain.getSquarePosition( next_square_number );
			
			line.push( Tween.to( players.get( turn ).getHeroSelected().getView(), 
					StackViewAccessor.POSITION_XY, 0.8f ).target(
							next_position.x, next_position.y ).ease( Linear.INOUT ) );
			
			line.push( Tween.call( new TweenCallback() {
				public void onEvent(int type, BaseTween<?> source) {
					Vector2i prev_square_number = selected_hero.getSquareTerrain().getNumber();
					
					selected_hero.setSquareTerrain( terrain.getSquareTerrain( path_found.get(0) ) );
					path_found.remove( 0 );
					
					hud.getMiniMap().updatePosition( prev_square_number );
					hud.getMiniMap().updatePosition( selected_hero.getSquareTerrain().getNumber() );
					terrain.removeFirstPathElement();
				}
			}));
			
			
			int orientation = getOrientation( last_square_number, next_square_number );
			
			players.get( turn ).getHeroSelected().addWalkAction( orientation );
			
			last_square_number = next_square_number;
		}
		
		line.push( Tween.call( new TweenCallback() {
			public void onEvent(int type, BaseTween<?> source) { mutex = false; }
		}));
	}
	
	public int getOrientation( Vector2i last_position, Vector2i new_position ) {
		if( new_position.x - last_position.x < 0)
			return Constants.XL;
		else if( new_position.x - last_position.x > 0)
			return Constants.XR;
		else if( new_position.y - last_position.y < 0 )
			return Constants.YD;
		else if( new_position.y - last_position.y > 0 )
			return Constants.YU;
		else
			return -1;
	}
	
	private void checkPathForAttack( Vector2i destination ) {
		PathFinder path_finder = new PathFinder(
				terrain.getRoadsMatrix( destination ), terrain.SQUARES_X, terrain.SQUARES_Y );
		
		Vector2i origin = players.get( turn ).getHeroSelected().getSquareTerrain().getNumber();
		List<Vector2i> camino = path_finder.findWay( origin, destination );

		if( camino.size() > 0 ) {
			path_found = camino;
			terrain.drawPathSelected( path_found.subList(0 , path_found.size() - 1) );
		}
	}
	
	public void moveSelectedHeroAndAttack() {
		path_found.remove( path_found.size() - 1 );
		
		Timeline line = Timeline.createSequence();		
		createMoveHeroLine( line );
		
		// Add callback for when animation has finished'
		line.push( Tween.call( new TweenCallback() {
			public void onEvent( int type, BaseTween<?> source ) {
				game.throwBattleScreen( selected_hero.getArmy(), attack_square.getArmy() );	
			}
		}));
		
		line.start( manager );
	}
	
	private void checkHeroEvent( HeroTop hero ) {
		if( selected_hero == null ) {
			selected_hero = hero;
			selected_hero.select();
			hud.selectHero( selected_hero );
		}
		else {
			selected_hero.unselect();
			hud.unselectHero();
			selected_hero = null;
		}
		
		typeEvent = Constants.NONE;
		objectEvent = null;
		terrain.removePathSelected();
	}
	
	private void checkCreaturesGroupEvent( CreaturesGroup group ) {
		if( players.get( turn ).isHeroSelected() ) {
			SquareTerrain square = group.getSquare();
			
			hud.selectEnemy( group );
			selected_group = group;
			
			if( path_found.size() == 0 )
				checkPathForAttack( square.getNumber() );
			else
				processAttackAction( square );
		}
		else {
			if( selected_group == null ) {
				hud.selectEnemy( group );
				selected_group = group;
			}
			else {
				hud.unselectEnemy();
			}	
		}
		
		typeEvent = Constants.NONE;
		objectEvent = null;
	}
	
	private void processAttackAction( SquareTerrain square ) {
		terrain.removePathSelected();
		Vector2i end = path_found.get( path_found.size() - 1 );
		
		if( square.getNumber().x == end.x &&  square.getNumber().y == end.y ) {
			attack_square = square;
			moveSelectedHeroAndAttack();
		}
		else {
			path_found = new ArrayList<Vector2i>();
			checkPathForAttack( square.getNumber() );
		}
	}
	
	private void showArmyInfoPanel() {
		mutex = true;
		show_info = true;
		
		Vector2 position = new Vector2(
			terrain.getStage().getCamera().position.x - 160,
			terrain.getStage().getCamera().position.y - MapConstants.TERRAIN_HEIGHT / 2 );
		
		ArmyInfoPanel panel = new ArmyInfoPanel( selected_hero, position );
		terrain.getStage().addActor( panel );
	}
	
	private void showEnemyArmyInfoPanel() {
		Vector2 position = new Vector2(
			terrain.getStage().getCamera().position.x - 160,
			terrain.getStage().getCamera().position.y - MapConstants.TERRAIN_HEIGHT / 2 );
		
		if( selected_hero_enemy != null ) {
			ArmyInfoPanel panel = new ArmyInfoPanel( selected_hero_enemy, position );
			terrain.getStage().addActor( panel );
		}
		else if( selected_group != null ) {
			ArmyInfoPanel panel = new ArmyInfoPanel( selected_group, position );
			terrain.getStage().addActor( panel );
		}
	}
}