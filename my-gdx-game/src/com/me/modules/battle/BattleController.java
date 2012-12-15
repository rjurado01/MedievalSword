package com.me.modules.battle;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.mygdxgame.ImageAccessor;
import com.me.mygdxgame.Player;
import com.me.mygdxgame.Unit;
import com.me.mygdxgame.UnitAccessor;
import com.me.utils.Vector2i;

/**
 * Control the battle turns, events and all battle.
 */
public class BattleController {
	
	/* EVENTS TYPES */
	static final int NONE = -1;
	static final int SQUARE = 0;
	static final int UNIT = 1;
	static final int SHIELD = 2;
	static final int MAGIC = 3;
	static final int SETTINGS = 4;
	
	/* CONTROLLER STATUS */
	static final int NOTHING = 0;
	static final int ATTACK_POSITION = 1;
	
	/* BATTLE SIDES */
	public static final boolean RIGHT = true;
	public static final boolean LEFT = false;
	
	/* VARIABLES */
	static Board board;
	static Player players [];
	static BattlePanel panel;
	static BattleMenu menu;
	
	public static Stage stage;
	public static TweenManager manager;
	
	// Battle turn
	static int turn;	
	
	// Semaphore
	static boolean mutex = false;
	
	// Controller status
	static int status = NOTHING;
	
	// Square from enemy attacked
	public static SquareBoard enemy_square = null;
	
	// Objects
	static Arrow arrow = null;
	
	/* EVENTS INFO */
	static Object objectEvent = null;
	static int typeEvent = -1;
	
	/**
	 * Class constructor
	 * @param board
	 * @param player1
	 * @param manager
	 * @param renderer
	 */
	public BattleController( Board board, Player players[], TweenManager manager, BattlePanel panel, Stage stage, BattleMenu menu ) {
		this.board    = board;
		this.players  = players;
		this.manager  = manager;
		this.panel    = panel;
		this.stage 	  = stage;
		this.menu 	  = menu;
	}
	
	/**
	 * Add new event to process in update
	 * @param type event type
	 * @param object object that received the event
	 */
	static void addEvent( int type, Object object ) {
		if( typeEvent == NONE ) {
			typeEvent = type;
			objectEvent = object;
		}
	}
	
	/**
	 * Update battle controller
	 * Check if exist some event and process it
	 */
	public void update() {
		if( !mutex )	// check semaphore 
		{
			if( typeEvent == SQUARE && objectEvent != null ) {
				checkSquareEvent( (SquareBoard) objectEvent );
				
				objectEvent = null;
				typeEvent = NONE;
			}
			else if( typeEvent == SHIELD ) {
				board.resetSquares();
				passTurn();
				
				typeEvent = NONE;
			}
			else if( typeEvent == SETTINGS ) {
				menu.setVisible( true );
				
				mutex = true;
				typeEvent = NONE;
			}
		}
	}
	
	/**
	 * Check events on square: ( move unit, attack enemy )
	 * @param square square that received event
	 */
	public void checkSquareEvent( SquareBoard square ) {
		if( square.isAvailable() ) {
			moveUnit( square.getNumber() );
		}
		else if( square.hasEnemyOn() )
		{
			// Check if unit can attack from a distance
			if( players[ turn ].getSelectedUnit().getActualRange() > 0 ) {
				enemy_square = square;
				players[turn].getSelectedUnit().attackAction();
				status = NOTHING;
				
				board.resetSquares();
			}
			else {
				board.showAttackPositions( square.getNumber(), players[ turn ].getSelectedUnit().getSquare().getNumber() );
			
				status = ATTACK_POSITION;
				enemy_square = square;
			}
		}
	}

	/**
	 * Move selected unit to selected square
	 * @param touch touch position
	 */
	public void moveUnit( Vector2i end ) {
		mutex = true;	// block semaphore
		
		// Calcule init square
		Vector2i init = players[turn].getSelectedUnit().getSquare().getNumber();
		
		if( board.isValidDestination(init, end, players[turn].getSelectedUnit().getActualMovility()) )
		{
			// Hide number of units
			players[turn].getSelectedUnit().setShowNumber( false );
		
			// Find best way to destiny
			board.findWay(init.x, init.y, end.x, end.y);
			
			if( board.getWayList().size() > 0 ) 
			{	
				Timeline line = Timeline.createSequence();
				
				// Add tween to move unit, square to square
				for( int i = board.getWayList().size() - 2; i >= 0; i--) {
					Vector2 aux = new Vector2( Board.CORRECT_X, Board.CORRECT_Y ).add(
							board.getWayList().get(i).getPosition() );
					
					line.push( Tween.to(players[turn].getSelectedUnit(), 
							UnitAccessor.POSITION_XY, 0.8f).target(aux.x, aux.y).ease(Sine.IN) );
					
					// Initialize animation
					players[turn].getSelectedUnit().walkAction();
				}
				
				// When animation has finished, set mutex to 'false'
				line.push( Tween.call( new TweenCallback() {
					public void onEvent( int type, BaseTween<?> source ) {
						// Hide number of units
						players[turn].getSelectedUnit().setShowNumber( true );				
						
						if( status == ATTACK_POSITION ) {
							players[turn].getSelectedUnit().attackAction();
							
							status = NOTHING;
						} 
						else {
							mutex = false;
							passTurn();
						}
					}
				}));
				
				// Init movement animation
				line.start(manager);
				
				// Free origin square and occupies end square
				board.getSquareFromNumber(init.x, init.y).setFree();
				board.getSquareFromNumber(end.x, end.y).setUnit( players[turn].getUnitsId() );
				
				// Set end square like unit's square
				players[turn].getSelectedUnit().setSquare(
						board.getSquareFromNumber(end.x, end.y), players[turn].getUnitsId() );
				
				// Reset squares's attributes for find way
				board.resetSquares();
			}
		}
		else
			mutex = false;
	}
	
	/**
	 * Attack enemy with selected unit
	 * @param position enemy position
	 */
	public static void attack() {
		Vector2i position = enemy_square.getNumber();
		
		Vector2i init = players[turn].getSelectedUnit().getSquare().getNumber();
		
		// Check that unit can attack enemy position
		if( players[turn].getSelectedUnit().getActualRange() > 0 || 
				( Math.abs(init.x - position.x) == 1 || Math.abs(init.y - position.y) == 1 )) 
		{	
			Unit target = players[ getNextTurn() ].getUnitFromSquare(
					board.getSquareFromNumber(position.x, position.y) );
			
			Unit attacker = players[ turn ].getSelectedUnit();

			if( target.receiveDamage( attacker.getAttackDamage() ) == false ) {
				target.getSquare().setFree();
				stage.removeActor( target );
				players[ getNextTurn() ].deleteUnit( target );
			}
		}
	}
	
	/**
	 * Random first turn and position of players ( right / left )
	 */
	public void initBattle() {
		// Random player side
		int side = (int) (Math.random() * 2); 
		
		if( side == 0 ) {
			players[0].setBattleSide( LEFT );
			players[1].setBattleSide( RIGHT );
		}
		else {
			players[0].setBattleSide( RIGHT );
			players[1].setBattleSide( LEFT );
		}
		
		// Random turn
		turn = (int) (Math.random() * 2); 
		players[ turn ].selectLastUnit();
		
		placeUnits( players[0] );
		placeUnits( players[1] );

		for( Unit unit : players[0].getUnits() )
			stage.addActor( unit );
		for( Unit unit : players[1].getUnits() )
			stage.addActor( unit );	
				
		// Update board with available squares for select Unit
		board.selectUnit( players[turn].getSelectedUnit(),
				players[turn].getSelectedUnit().getActualMovility(), players[turn].getUnitsId() );
	}
	
	/**
	 * Place units in the board when initialize battle
	 * @param player
	 */
	public void placeUnits( Player player ) {
		int x = 0;
		int y = 0;
		
		if( player.getBattleSide() == RIGHT )
			x = Board.NS_X - 1;
			
		for( Unit u : player.getUnits() ) {
			u.setSquare( board.getSquareFromNumber(x, y), player.getUnitsId() );
			u.setPosition( new Vector2( Board.CORRECT_X, Board.CORRECT_Y ).add(
					board.getSquareFromNumber( x, y ).getPosition() ) );
				
			y+= 2;
		}
	}
	
	/**
	 * Go to next turn, select new turn unit and update board.
	 */
	public static void passTurn() {		
		// Pass turn
		turn = ( turn + 1 ) % 2;
		
		// Set next unit turn
		players[turn].nexUnit();
		
		// Update board with available squares (textures) from new unit
		board.selectUnit( players[turn].getSelectedUnit(), 
				players[turn].getSelectedUnit().getActualMovility(), players[turn].getUnitsId() );
		
		mutex = false;
	}
	
	/**
	 * Calculate next turn
	 * @return next turn
	 */
	public static int getNextTurn() {
		return ( turn + 1 ) % 2;
	}
	
	/**
	 * Add an arrow to stage when archer shoot to enemy and add tween to move it.
	 */
	public static void throwArrow( float x, float y, int orientation ) {
		arrow = new Arrow( x + SquareBoard.SIZE_H / 2, y, orientation, BattleController.stage );
		
		Vector2 destination = BattleController.enemy_square.getPosition();
		destination.y += SquareBoard.SIZE_H / 2;

		Tween.to( arrow, ImageAccessor.POSITION_XY, Math.abs( x - destination.x ) / 700 )
	    .target( destination.x, destination.y )
	    .setCallback( new TweenCallback() {
			
			// When animation end, remove arrow and damage enemy
			public void onEvent(int type, BaseTween<?> source) {
				arrow.remove();
				
				BattleController.attack();
				
				BattleController.passTurn();	
			}
		})
	    .start( BattleController.manager );
	}
}