package com.modules.battle;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdxgame.Army;
import com.mygdxgame.Constants;
import com.mygdxgame.Stack;
import com.utils.CallBack;
import com.utils.ImageAccessor;
import com.utils.StackViewAccessor;
import com.utils.Vector2i;

/**
 * Control the battle turns, events and all battle.
 */
public class BattleController {
	
	/* CONTROLLER STATUS */
	final int NORMAL = 0;
	final int ATTACKING = 1;
	
	Board board;
	Army armies [];
	BattlePanel panel;
	BattleMenu menu;
	BattleSummary summary;
	
	Stage stage;
	TweenManager manager;
	
	int turn;
	int status = NORMAL;
	static boolean mutex = false; 	// Semaphore		 
	
	SquareBoard attacked_enemy_square = null;
	Stack stack_selected;
	Arrow arrow = null;
	
	/* EVENTS INFO */
	static Object objectEvent = null;
	static int typeEvent = -1;
	

	public BattleController( Army armies[], Stage stage ) {
		this.armies = armies;
		this.stage = stage;
		
		createBattleElements();
		
		manager = new TweenManager();
		Tween.registerAccessor( StackView.class, new StackViewAccessor() );
		Tween.registerAccessor( Image.class, new ImageAccessor() );
	}	
	
	private void createBattleElements() {
		board = new Board( stage );
		panel = new BattlePanel( stage );
		menu  = new BattleMenu();
		
		summary = new BattleSummary( stage );
		summary.setSummaryStacks( armies[0].getStacks(), armies[1].getStacks() );
	}

	static void addEvent( int type, Object receiver ) {
		if( typeEvent == Constants.NONE ) {
			typeEvent = type;
			objectEvent = receiver;
		}
	}
	
	public void initBattle() {
		randomSide();
		randomTurn();
		
		placeArmyInBoard( armies[0] );
		placeArmyInBoard( armies[1] );
				
		board.selectStack( stack_selected, armies[turn].getBattleSide() );
	}
	
	public void randomSide() {
		int side = (int) (Math.random() * 2); 
		
		if( side == 0 ) {
			armies[0].setBattleSide( Constants.LEFT_SIDE );
			armies[1].setBattleSide( Constants.RIGHT_SIDE );
		}
		else {
			armies[0].setBattleSide( Constants.RIGHT_SIDE );
			armies[1].setBattleSide( Constants.LEFT_SIDE );
		}
	}
	
	public void randomTurn() {
		turn = (int) (Math.random() * 2); 
		armies[ getNextTurn() ].selectLastStack();
		armies[ turn ].selectFirstStack();
		stack_selected = armies[ turn ].getSelectedStack();
	}
	
	public int getNextTurn() {
		return ( turn + 1 ) % 2;
	}	
	
	public void placeArmyInBoard( Army army ) {
		int number_square_x = 0;
		int number_square_y = 0;
		
		if( army.getBattleSide() == Constants.RIGHT_SIDE )
			number_square_x = Board.NS_X - 1;

		for( Stack stack : army.getStacks() ) {
			placeStackInBoard( stack, number_square_x, number_square_y );
			number_square_y += 2;
		}
	}
	
	public void placeStackInBoard( Stack stack, int x, int y ) {
		stack.setSquare( board.getSquare(x, y) );
		stack.getSquare().setStack( stack );
		
		stack.getView().setPosition( new Vector2( Board.CORRECT_X, Board.CORRECT_Y ).add(
				board.getSquare( x, y ).getPosition() ) );
		
		stage.addActor( stack.getView() );
	}

	public void update() {
		manager.update( Gdx.graphics.getDeltaTime() );
		checkEvents();
	}	
	
	public void checkEvents() {
		if( !mutex )	// check semaphore 
		{
			if( typeEvent == Constants.SQUARE && objectEvent != null )
				checkSquareEvent( (SquareBoard) objectEvent );
			else if( typeEvent == Constants.SHIELD )
				passTurnByEvent();
			else if( typeEvent == Constants.SETTINGS )
				showMenu();
			else if( typeEvent == Constants.MAGIC )
				typeEvent = Constants.NONE;
		}
	}	
	
	private void passTurnByEvent() {
		typeEvent = Constants.NONE;
		board.resetSquares();
		passTurn();
	}
	
	public void passTurn() {	
		turn = getNextTurn();
		
		// Check if player has any unit or battle has finished
		if( armies[turn].getStacks().size() == 0 )
			summary.show( getNextTurn() );			
		else
			selectNextStack();
	}
	
	public void selectNextStack() {
		armies[turn].selectNexStack();
		stack_selected = armies[ turn ].getSelectedStack();
		
		// Update board with available squares (textures) from new unit
		board.selectStack( stack_selected, armies[turn].getBattleSide() );
		
		mutex = false;
	}
	
	private void showMenu() {
		menu.show( stage );
		
		mutex = true;
		typeEvent = Constants.NONE;
	}
	
	public void checkSquareEvent( SquareBoard square ) {
		if( square.isAvailable() )
			processMoveEvent( square );
		else if( square.hasEnemyOn() )
			processAttackEvent( square );
		
		objectEvent = null;
		typeEvent = Constants.NONE;
	}
	
	public void processAttackEvent( SquareBoard enemySquare ) {
		if( stack_selected.getRange() > 0 )
			shootToStack( enemySquare );
		else
			showAttackAvailablePositions( enemySquare);
	}
	
	public void shootToStack( SquareBoard enemySquare ) {
		attacked_enemy_square = enemySquare;
		stack_selected.addAttackAction( getAttackOrientation(), attackRangeCallback() );
		status = NORMAL;
		
		board.resetSquares();
	}
	
	public void showAttackAvailablePositions( SquareBoard enemySquare ) {
		board.showAttackPositions( enemySquare.getNumber(),
				armies[ turn ].getSelectedStack().getSquare().getNumber() );
	
		status = ATTACKING;
		attacked_enemy_square = enemySquare;
	}

	/**
	 * Move selected unit to selected square
	 * @param end_square selected square ( destiny )
	 */
	public void processMoveEvent( SquareBoard end_square ) {
		mutex = true;	// block semaphore

		SquareBoard init_square = stack_selected.getSquare();
		
		if( board.isAchievable( init_square, end_square, stack_selected.getMovility() ) )
			checkWayAndMoveStack( init_square, end_square );
		else
			mutex = false;
	}
	
	public void checkWayAndMoveStack( SquareBoard init_square, SquareBoard end_square ) {
		stack_selected.getView().showIndicator( false );

		if( status == ATTACKING && init_square == end_square ) {
			stack_selected.getView().showIndicator( true );
			attackFromNormalUnit();
		}
		else if( board.findWay( init_square, end_square ) ) 
			moveStack( init_square, end_square );
		
		board.resetSquares();
	}

	public void moveStack( SquareBoard init_square, SquareBoard end_square ) {
		createMoveStackTween();
		
		init_square.setFree();
		end_square.setStack( stack_selected );
		stack_selected.setSquare( end_square );
	}
	
	public void createMoveStackTween() {
		Timeline line = Timeline.createSequence();		
		moveStack( line );
		
		// Add callback for when animation has finished'
		line.push( Tween.call( new TweenCallback() {
			public void onEvent( int type, BaseTween<?> source ) {
				stack_selected.getView().showIndicator( true );
				
				if( status == ATTACKING )
					attackFromNormalUnit();
				else
					passTurn();
			}
		}));
		
		line.start( manager );
	}
	
	public void attackFromNormalUnit() {
		stack_selected.addAttackAction( getAttackOrientation(), getNormalAttackCallback() );
		status = NORMAL;
	}
	
	/**
	 * Move stack from actual square to end square of board.wayList
	 * Adds a walk action for each element of wayList ( for each square )
	 * @param line line of animations
	 */
	public void moveStack( Timeline line ) {
		for( SquareBoard next_square : board.getLastWay() ) {
			Vector2 next_position = next_square.getStackPosition();
			
			line.push( Tween.to( stack_selected.getView(), 
					StackViewAccessor.POSITION_XY, 0.8f ).target(
							next_position.x, next_position.y ).ease( Sine.IN ) );
			
			stack_selected.setOrientationByFocus( next_position );
			stack_selected.addWalkAction();
		}
	}
	
	/**
	 * Attack enemy with selected stack of units
	 * @param position enemy position
	 */
	public void makeDamage() {
		if( canAttackEnemyStack() ) {	
			Stack target = attacked_enemy_square.getStack();			
			target.receiveDamage( stack_selected.getAttackDamage() );
			
			if(  target.isDead() ) {
				target.getSquare().setFree();
				stage.removeActor( target.getView() );
				armies[ getNextTurn() ].deleteStack( target );
			}
		}
	}
	
	private boolean canAttackEnemyStack() {
		Vector2i position = attacked_enemy_square.getNumber();		
		Vector2i init = stack_selected.getSquare().getNumber();
		
		if( stack_selected.getRange() > 0 )
			return true;
		if( Math.abs(init.x - position.x) == 1 )
			return true;
		if( Math.abs(init.y - position.y) == 1 )
			return true;
		
		return false;
	}

	public void throwArrow() {
		arrow = new Arrow( stack_selected.getView().x + SquareBoard.SIZE_W / 2, 
				stack_selected.getView().y,
				getAttackOrientation(), stage );
		
		Vector2 destination = attacked_enemy_square.getPosition();
		destination.y += SquareBoard.SIZE_H / 2;

		createTweenToMoveArrow( arrow, destination );
	}
	
	public void createTweenToMoveArrow( Arrow arrow, Vector2 destination ) {
		Tween.to( arrow, ImageAccessor.POSITION_XY, Math.abs(
				stack_selected.getView().x - destination.x ) / 700 )
	    .target( destination.x, destination.y )
	    .setCallback( getArrowCallback() )
	    .start( manager );
	}
	
	public TweenCallback getArrowCallback() {
		return new TweenCallback() {
			public void onEvent(int type, BaseTween<?> source) {
				arrow.remove();			
				makeDamage();
				passTurn();	
			}
		};
	}
	
	public CallBack attackRangeCallback() {
		return new CallBack() {			
			public void completed() {
				throwArrow();
			}
		};
	}
	
	public CallBack getNormalAttackCallback() {
		return new CallBack() {
			public void completed() {
				makeDamage();			
				passTurn();
			}
		};
	}
	
	public int getAttackOrientation() {
		if( stack_selected.getView().x  > attacked_enemy_square.x )
			return Constants.XL;
		else if( stack_selected.getView().x < attacked_enemy_square.x )
			return Constants.XR;
		else
			return Constants.UNDEFINED;
	}
}