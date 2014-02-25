package com.modules.battle;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Army;
import com.game.Assets;
import com.game.Constants;
import com.game.MyGdxGame;
import com.game.Stack;
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
	BattleExitAlert menu;
	BattleSummary summary;

	Stage stage;
	TweenManager manager;

	int turn;
	int status = NORMAL;
	static boolean mutex = false; 	// Semaphore

	SquareBoard attacked_enemy_square = null;
	Stack stack_selected;
	Arrow arrow = null;

	MyGdxGame game;

	/* EVENTS INFO */
	static Object objectEvent = null;
	static int typeEvent = -1;


	public BattleController( MyGdxGame game, Army armies[], Stage stage ) {
		this.armies = armies;
		this.stage = stage;
		this.game = game;

		createBattleElements();

		manager = new TweenManager();
		Tween.registerAccessor( StackView.class, new StackViewAccessor() );
		Tween.registerAccessor( Image.class, new ImageAccessor() );
	}

	private void createBattleElements() {
		board = new Board( stage );
		panel = new BattlePanel();
		menu  = new BattleExitAlert(
			new Vector2i( Constants.SIZE_W, Constants.SIZE_H),
			new Vector2i( 0, 0 ), null );

		stage.addActor( panel );

		summary = new BattleSummary( game, stage );
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
			number_square_x = BattleConstants.NS_X - 1;

		for( Stack stack : army.getStacks() ) {
			placeStackInBoard( stack, number_square_x, number_square_y );
			number_square_y += 2;
		}
	}

	public void placeStackInBoard( Stack stack, int x, int y ) {
		stack.setSquare( board.getSquare(x, y) );
		stack.getSquare().setStack( stack );
		stack.getView().setSquarePosition( board.getSquare( x, y ).getStackPosition() );

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
		if( armies[turn].getStacks().size() == 0 ) {
			Timeline line = Timeline.createSequence();
			line.push( Tween.to( null, StackViewAccessor.POSITION_XY, 1.5f ).ease( Linear.INOUT ) );

			line.push( Tween.call( new TweenCallback() {
				public void onEvent(int type, BaseTween<?> source) {
					summary.show( getNextTurn() );
				}
			}));

			line.start( manager );
		}
		else {
			selectNextStack();
		}
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
		if( status == ATTACKING && !square.hasTexture() ) {
			board.resetAvailableSquares();
			status = NORMAL;
		}
		else if( square.isAvailable() ) {
			processMoveEvent( square );
		}
		else if( square.hasEnemyOn() ) {
			processAttackEvent( square );
		}

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
		Vector2 last_position = stack_selected.getView().getPosition();

		for( SquareBoard next_square : board.getLastWay() ) {
			Vector2 next_position = stack_selected.getView().getSquarePosition( next_square.getStackPosition() );

			line.push( Tween.to( stack_selected.getView(), StackViewAccessor.POSITION_XY,
				stack_selected.getUnit().getWalkTime() ).target(
					next_position.x, next_position.y ).ease( Linear.INOUT) );

			stack_selected.addWalkAction(
					getOrientation( last_position, next_position ) );

			last_position = next_position;
		}
	}

	/**
	 * Attack enemy with selected stack of units
	 * @param position_number enemy position
	 */
	public void makeDamage() {
		if( canAttackEnemyStack() ) {
			Stack target = attacked_enemy_square.getStack();
			target.receiveDamage( stack_selected.getAttackDamage( getDistance() ) );

			if(  target.isDead() ) {
				Assets.playSound( "slight_screem2", false );
				target.getSquare().setFree();
				stage.removeActor( target.getView() );
				armies[ getNextTurn() ].deleteStack( target );
			}
		}
	}

	private boolean canAttackEnemyStack() {
		if( stack_selected.getRange() > 0 )
			return true;

		Vector2i position = attacked_enemy_square.getNumber();
		Vector2i init = stack_selected.getSquare().getNumber();

		if( Math.abs(init.x - position.x) == 1 )
			return true;
		if( Math.abs(init.y - position.y) == 1 )
			return true;

		return false;
	}

	private int getDistance() {
		Vector2i position = attacked_enemy_square.getNumber();
		Vector2i init = stack_selected.getSquare().getNumber();

		int distance_x = Math.abs( init.x - position.x );
		int distance_y = Math.abs( init.y - position.y );

		return Math.max( distance_x, distance_y );
	}

	public void throwArrow() {
		arrow = new Arrow(
			stack_selected.getView().x + BattleConstants.SQUARE_SIZE_W / 2,
			stack_selected.getView().y  + BattleConstants.UNIT_H / 2,
			getAttackOrientation(), stage );

		Vector2 destination = attacked_enemy_square.getStack().getView().getPosition();
		destination.x += BattleConstants.SQUARE_SIZE_W / 2;
		destination.y += BattleConstants.UNIT_H / 2;

		createTweenToMoveArrow( arrow, destination );
	}

	public void createTweenToMoveArrow( Arrow arrow, Vector2 destination ) {
		int orientation = getAttackOrientation();
		float duration;

		if( orientation == Constants.RIGHT || orientation == Constants.LEFT )
			duration = Math.abs( stack_selected.getView().x - destination.x ) / 2000;
		else
			duration = Math.abs( stack_selected.getView().y - destination.y ) / 2000;

		Tween.to( arrow, ImageAccessor.POSITION_XY, duration )
	    	.target( destination.x, destination.y )
	    	.ease( Linear.INOUT )
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

	/**
	 * Calculate the direction of attack to a unit
	 * @return orientation identifier
	 */
	public int getAttackOrientation() {
		Vector2 square1 = stack_selected.getSquare().getPosition();
		Vector2 square2 = attacked_enemy_square.getPosition();

		Vector2 aux = new Vector2( square2.x - square1.x, square2.y - square1.y );

		if( Math.abs( aux.x ) > Math.abs( aux.y ) ) {
			if( aux.x < 0 )
				return Constants.LEFT;
			else
				return Constants.RIGHT;
		}
		else if( aux.y < 0 )
			return Constants.DOWN;
		else
			return Constants.TOP;
	}

	/**
	 * Calculate orientation from two positions, last and new
	 * @param last_position
	 * @param new_position
	 * @return orientation identifier
	 */
	public int getOrientation( Vector2 last_position, Vector2 new_position ) {
		if( new_position.x - last_position.x < 0)
			return Constants.LEFT;
		else if( new_position.x - last_position.x > 0)
			return Constants.RIGHT;
		else if( new_position.y - last_position.y < 0 )
			return Constants.DOWN;
		else if( new_position.y - last_position.y > 0 )
			return Constants.TOP;
		else
			return -1;
	}
}