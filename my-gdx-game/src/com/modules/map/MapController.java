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
	MyGdxGame game;
	
	int turn;
	List<Vector2i> path_found;	// show if path have been found 
	SquareTerrain attack_square;
	
	static boolean mutex = false; 	// Semaphore		
	
	
	public MapController( MyGdxGame game, List<Player> players, Terrain terrain ) {
		this.game = game;
		this.players = players;
		this.terrain = terrain;
		
		path_found = new ArrayList<Vector2i>();
		manager = new TweenManager();
		Tween.registerAccessor( HeroView.class, new HeroViewAccessor() );
		
		selected_hero = players.get( 0 ).getHeroSelected();
	}
	
	static void addEvent( int type, Object receiver ) {
		if( typeEvent == Constants.NONE ) {
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
		}
	}	
	
	public void checkSquareEvent( SquareTerrain square ) {
		if( square.isRoadAvailable() )
			processMoveEvent( square );
		else if( square.hasCreaturesGroup() )
			processAttackEvent( square );
		
		objectEvent = null;
		typeEvent = Constants.NONE;
	}
	
	private void processMoveEvent( SquareTerrain square ) {
		if( players.get( turn ).isHeroSelected() ) {
			if( path_found.size() == 0 )
				checkPath( square.getNumber() );
			else {
				terrain.removePathSelected();
				Vector2i end = path_found.get( path_found.size() - 1 );
				
				if( square.getNumber().x == end.x &&  square.getNumber().y == end.y )
					moveSelectedHero();
				else {
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
		
		// Add callback for when animation has finished'
		line.push( Tween.call( new TweenCallback() {
			public void onEvent( int type, BaseTween<?> source ) {
				Vector2i end = path_found.get( path_found.size() - 1 );
				players.get( turn ).getHeroSelected().setSquareTerrain(
						terrain.getSquareTerrain( end ));

				path_found = new ArrayList<Vector2i>();
			}
		}));
		
		line.start( manager );
	}
	
	/**
	 * Move stack from actual square to end square of board.wayList
	 * Adds a walk action for each element of wayList ( for each square )
	 * @param line line of animations
	 */
	public void createMoveHeroLine( Timeline line ) {
		Vector2i last_square = 
				players.get( turn ).getHeroSelected().getSquareTerrain().getNumber();
		
		for( Vector2i next_square : path_found ) {
			Vector2 next_position = terrain.getSquarePosition( next_square );
			
			line.push( Tween.to( players.get( turn ).getHeroSelected().getView(), 
					StackViewAccessor.POSITION_XY, 0.8f ).target(
							next_position.x, next_position.y ).ease( Linear.INOUT ) );
			
			
			int orientation = getOrientation( last_square, next_square );
			
			players.get( turn ).getHeroSelected().addWalkAction( orientation );
			
			last_square = next_square;
		}
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
	
	private void processAttackEvent( SquareTerrain square ) {
		if( players.get( turn ).isHeroSelected() ) {
			if( path_found.size() == 0 )
				checkPathForAttack( square.getNumber() );
			else {
				terrain.removePathSelected();
				Vector2i end = path_found.get( path_found.size() - 1 );
				
				if( square.getNumber().x == end.x &&  square.getNumber().y == end.y ) {
					attack_square = square;
					moveSelectedHeroAndAttack();
				}
				else {
					path_found = new ArrayList<Vector2i>();
					checkPath( square.getNumber() );
				}
			}
		}
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
				Vector2i end = path_found.get( path_found.size() - 1 );
				
				players.get( turn ).getHeroSelected().setSquareTerrain(
						terrain.getSquareTerrain( end ));

				game.throwBattleScreen( selected_hero.getArmy(), attack_square.getArmy() );
				
				path_found = new ArrayList<Vector2i>();				
			}
		}));
		
		line.start( manager );
	}
}
