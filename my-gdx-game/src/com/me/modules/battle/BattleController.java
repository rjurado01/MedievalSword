package com.me.modules.battle;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.me.mygdxgame.Assets;
import com.me.mygdxgame.Player;
import com.me.mygdxgame.Unit;
import com.me.mygdxgame.UnitAccessor;
import com.me.utils.Vector2i;

public class BattleController {
	
	/* EVENTS TYPES */
	static final int NONE = -1;
	static final int SQUARE = 0;
	static final int UNIT = 1;
	static final int SHIELD = 2;
	static final int MAGIC = 3;
	static final int SETTINGS = 4;
	
	static final int NOTHING = 0;
	static final int ATTACK_POSITION = 1;
	
	Board board;
	Player players [];
	TweenManager manager;
	Stage stage;
	BattlePanel panel;
	BattleMenu menu;
	
	int turn;
	
	static boolean mutex = false;
	
	int status = NOTHING;
	Vector2i enemy_square_number = null;
	
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
	
	static void addEvent( int type, Object object ) {
		if( typeEvent == NONE ) {
			typeEvent = type;
			objectEvent = object;
		}
	}
	
	public void update() {
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
			
			typeEvent = NONE;
		}
	}
	
	public void checkSquareEvent( SquareBoard square ) {
		if( square.isAvailable() )
			moveUnit( square.getNumber() );
		else if( square.hasEnemyOn() ) {
			board.showAttackPositions( square.getNumber(), players[ turn ].getSelectedUnit().getSquare().getNumber() );
			status = ATTACK_POSITION;
			enemy_square_number = square.getNumber();
		}
	}

	/*public boolean touchUp(int x, int y, int pointer, int button) {
		if( mutex == false ) {
			Vector2 aux = renderer.unproject(x, y);
			
			// Check if user has touch in board or in panel
			Vector2i position = board.getSquareNumber(aux.x, aux.y);
			
			if( position != null ) {
				if( board.getSquareFromNumber(position.x, position.y).isAvailable() )
					moveUnit( position );
				else if( board.getSquareFromNumber(position.x, position.y).hasEnemy( players[ turn ].getUnitsId() ) ) {
					board.showAtackPositions( position, players[turn].getSelectedUnit().getSquare().getNumber(), 
							players[turn].getSelectedUnit().getActualMovility() );
					
					status = ATTACK_POSITION;
					enemy_position = position;
				}
				
			}
			else {
				touchPanel( aux );
			}
		}
		
		return false;
	} */

	/**
	 * Move unit selected to selected square
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
				for(int i = board.getWayList().size() - 2; i >= 0; i--) {
					Vector2 aux = new Vector2( board.CORRECT_X, board.CORRECT_Y ).add( board.getWayList().get(i).getPosition() );
					
					line.push( Tween.to(players[turn].getSelectedUnit(), 
							UnitAccessor.POSITION_XY, 0.8f).target(aux.x, aux.y).ease(Sine.IN) );
					
					// Calculate init position for animation
					Vector2 aux_init;
					
					if( i == board.getWayList().size() - 1)
						aux_init = players[turn].getSelectedUnit().getPosition();
					else
						aux_init = board.getWayList().get( i + 1 ).getPosition();
					
					// Init animation
					if( aux.x > aux_init.x )
						players[turn].getSelectedUnit().addAction( Unit.RUN_XR, 0.8f );
					else if( aux.x < aux_init.x )
						players[turn].getSelectedUnit().addAction( Unit.RUN_XL, 0.8f );
					else if( players[turn].isInverse() )
						players[turn].getSelectedUnit().addAction( Unit.RUN_XL, 0.8f );
					else
						players[turn].getSelectedUnit().addAction( Unit.RUN_XR, 0.8f );
				}
				
				// When animation has finished, set mutex to 'false'
				line.push( Tween.call( new TweenCallback() {
					public void onEvent( int type, BaseTween<?> source ) {
						// Hide number of units
						players[turn].getSelectedUnit().setShowNumber( true );				
						
						if( status == ATTACK_POSITION ) {
							attackEnemy( enemy_square_number );
							
							status = NOTHING;
						}

						mutex = false;
							
						passTurn();
					}
				}));
				
				// Init movement animation
				line.start(manager);
				
				// Free origin square and occupies end square
				board.getSquareFromNumber(init.x, init.y).setFree();
				board.getSquareFromNumber(end.x, end.y).setUnit( players[turn].getUnitsId() );
				
				// Set end square like unit's square
				players[turn].getSelectedUnit().setSquare( board.getSquareFromNumber(end.x, end.y), players[turn].getUnitsId() );
				
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
	public void attackEnemy( Vector2i position ) {
		Vector2i init = players[turn].getSelectedUnit().getSquare().getNumber();
		
		if( players[turn].getSelectedUnit().getActualRange() > 0 || 
				( Math.abs(init.x - position.x) == 1 || Math.abs(init.y - position.y) == 1 )) 
		{	
			Unit target = players[ getNextTurn() ].getUnitFromSquare( board.getSquareFromNumber(position.x, position.y) );
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
		turn = (int) (Math.random() * 2); 
		
		if( turn == 0 ) {
			players[0].setInverse( false );
			players[1].setInverse( true );
			
			players[1].selectLastUnit();
			
			players[0].setUnitsId( SquareBoard.UNIT_P1 );
			players[1].setUnitsId( SquareBoard.UNIT_P2 );
		}
		else {
			players[0].setInverse( true );
			players[1].setInverse( false );
			
			players[0].selectLastUnit();
			
			players[0].setUnitsId( SquareBoard.UNIT_P2 );
			players[1].setUnitsId( SquareBoard.UNIT_P1 );
		}
		
		placeUnits( players[0] );
		placeUnits( players[1] );
		
		players[turn].getSelectedUnit().getSquare().setStatus( SquareBoard.SELECTED_UNIT );

		for( Unit unit : players[0].getUnits() )
			stage.addActor( unit );
		for( Unit unit : players[1].getUnits() )
			stage.addActor( unit );	
				
		// Update board with available squares for select Unit
		board.selectUnit( players[turn].getSelectedUnit().getSquare().getNumber(), 
		players[turn].getSelectedUnit().getActualMovility(), players[turn].getUnitsId() );
	}
	
	/**
	 * Place units in the board when initialize battle
	 * @param player
	 */
	public void placeUnits( Player player) {
		int x = 0;
		int y = 0;
		
		if( player.isInverse() )
			x = board.NS_X - 1;
			
		for( Unit u : player.getUnits() ) {
			u.setSquare( board.getSquareFromNumber(x, y), player.getUnitsId() );
			u.setPosition( new Vector2( board.CORRECT_X, board.CORRECT_Y ).add( board.getSquareFromNumber( x, y ).getPosition() ) );
				
			y++;
		}
	}
	
	/**
	 * Go to next turn, select new turn unit and update board.
	 */
	public void passTurn() {
		// Pass turn
		turn = ( turn + 1 ) % 2;
		
		// Set next unit turn
		players[turn].nexUnit();
		
		// Update board with available squares (textures) from new unit
		board.selectUnit( players[turn].getSelectedUnit().getSquare().getNumber(), 
				players[turn].getSelectedUnit().getActualMovility(), players[turn].getUnitsId() );
	}
	
	/**
	 * Calculate next turn
	 * @return next turn
	 */
	public int getNextTurn() {
		return ( turn + 1 ) % 2;
	}
}