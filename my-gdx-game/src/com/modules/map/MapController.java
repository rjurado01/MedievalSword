package com.modules.map;

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

	/* STATUS */
	static final int NORMAL = 0;
	static final int ANIMATION = 1;
	static final int INFO1 = 2;
	static final int INFO2 = 3;

	List<Player> players;
	Terrain terrain;
	TweenManager manager;
	HeroTop selected_hero;
	CreaturesGroup selected_group;
	HeroTop selected_hero_enemy;
	ArmyInfoPanel army_info_panel;
	MyGdxGame game;
	HUD hud;

	int turn;
	int day;
	HeroPath hero_path;
	SquareTerrain attack_square;

	static int status = NORMAL; 	// Semaphore


	public MapController( MyGdxGame game, List<Player> players, Terrain terrain, HUD hud ) {
		this.game = game;
		this.players = players;
		this.terrain = terrain;
		this.hud = hud;

		hero_path = new HeroPath( terrain );
		manager = new TweenManager();
		Tween.registerAccessor( HeroView.class, new HeroViewAccessor() );
	}

	static void addEvent( int type, Object receiver ) {
		if( typeEvent == MapConstants.NONE && status == NORMAL  ) {
			typeEvent = type;
			objectEvent = receiver;
		}
		else if( (status == INFO1 || status == INFO2 ) &&
				( type == MapConstants.INFO1 || type == MapConstants.INFO2 ) ) {
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
		if( typeEvent == MapConstants.SQUARE && objectEvent != null ) {
			checkSquareEvent( (SquareTerrain) objectEvent );
		}
		else if( typeEvent == MapConstants.HERO && objectEvent != null) {
			checkHeroEvent( (HeroTop) objectEvent );
		}
		else if( typeEvent == MapConstants.CREATURES && objectEvent != null) {
			checkCreaturesGroupEvent( (CreaturesGroup) objectEvent );
		}
		else if( typeEvent == MapConstants.INFO1 && selected_hero != null ) {
			checkInfo1Event();
		}
		else if( typeEvent == MapConstants.INFO2 && selected_group != null) {
			checkInfo2Event();
		}
		else if( typeEvent == MapConstants.TURN ) {
			passTurn();
		}

		typeEvent = Constants.NONE;
		objectEvent = null;
	}

	public void checkSquareEvent( SquareTerrain square ) {
		if( square.isRoadAvailable() )
			processMoveEvent( square );
		else
			System.out.println("Evento no registrado.");


		objectEvent = null;
		typeEvent = Constants.NONE;
	}

	private void passTurn() {
		if( selected_hero != null ) {
			players.get(turn).selectHero( selected_hero );
			unselectHero();
		}
		else {
			players.get(turn).unselectHero();
		}

		turn = ( turn + 1 ) % players.size();
		day = ( day + 1 ) % 7;
		hud.passTurn( day + 1 );

		players.get(turn).passTurn();
		selectHero( players.get(turn).getHeroSelected() );
	}

	private void processMoveEvent( SquareTerrain square ) {
		if( players.get( turn ).isHeroSelected() ) {
			if(  hero_path.isPathMarked() == false )
				findPath( square.getNumber() );
			else {
				// if square marked isn't the last of path, recalculates path
				if( hero_path.isLastMarked( square.getNumber() ) )
					moveSelectedHero( null );
				else {
					hero_path.removePath();
					findPath( square.getNumber() );
				}
			}
		}
	}

	private void findPath( Vector2i destination ) {
		Vector2i origin =
				players.get( turn ).getHeroSelected().getSquareTerrain().getNumber();

		hero_path.findPath( origin, destination, selected_hero.getActualMobility() );

		if( hero_path.isLastAvailable() == false ) {
			SquareTerrain square = hero_path.getLastSquareTerrain();

			if( square.hasCreaturesGroup() ) {
				selected_group = square.group;
				hud.selectEnemy( square.group );
			}
		}
	}

	public void moveSelectedHero( TweenCallback callback ) {
		if( selected_hero.getActualMobility() > 0 ) {
			Timeline line = Timeline.createSequence();
			createMoveHeroLine( line );

			// Add callback for when animation has finished'
			if( callback != null)
				line.push( Tween.call(callback) );

			line.start( manager );
		}
	}

	/**
	 * Move stack from actual square to end square of board.wayList
	 * Adds a walk action for each element of wayList ( for each square )
	 * @param line line of animations
	 */
	public void createMoveHeroLine( Timeline line ) {
		status = ANIMATION;

		Vector2i last_square_number =
				players.get( turn ).getHeroSelected().getSquareTerrain().getNumber();

		terrain.removeFirstPathDrawnElement();

		for( Vector2i next_square_number : hero_path.getAvailableList() ) {
			Vector2 next_position = terrain.getSquarePosition( next_square_number );

			line.push( Tween.to(players.get( turn ).getHeroSelected().getView(),
					StackViewAccessor.POSITION_XY, 0.8f ).target(
							next_position.x, next_position.y ).ease( Linear.INOUT ) );

			line.push( Tween.call( getMoveItemCallback() ) );

			int orientation = getOrientation( last_square_number, next_square_number );
			players.get( turn ).getHeroSelected().addWalkAction( orientation );

			last_square_number = next_square_number;
		}

		line.push( Tween.call( new TweenCallback() {
			public void onEvent(int type, BaseTween<?> source) { status = NORMAL; }
		}));
	}

	private TweenCallback getMoveItemCallback() {
		return new TweenCallback() {
			public void onEvent(int type, BaseTween<?> source) {
				Vector2i prev_square_number = selected_hero.getSquareTerrain().getNumber();
				SquareTerrain actual_square = terrain.getSquareTerrain( hero_path.getFirstElement() );

				selected_hero.setSquareTerrain( actual_square );
				hero_path.removeFirstElement();

				hud.getMiniMap().updatePosition( prev_square_number );
				hud.getMiniMap().updatePosition( selected_hero.getSquareTerrain().getNumber() );

				if( hero_path.isLastPahtItem( actual_square.getNumber() ) == false &&
						selected_hero.getActualMobility() > 1 )
					terrain.removeFirstPathDrawnElement();

				selected_hero.decreaseMobility();
			}
		};
	}

	/**
	 * Calculate orientation from two positions, last and new
	 * @param last_position
	 * @param new_position
	 * @return orientation identifier
	 */
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

	private void checkHeroEvent( HeroTop hero ) {
		if( selected_hero == null )
			selectHero( hero );
		else
			unselectHero();

		typeEvent = Constants.NONE;
		objectEvent = null;
		terrain.removePathDrawn();
	}

	private void selectHero( HeroTop hero ) {
		if( hero != null ) {
			selected_hero = hero;
			selected_hero.select();
			hud.selectHero( selected_hero );

			hero_path.addPath( selected_hero.getPathMarked(),
					selected_hero.getActualMobility() );
		}
	}

	private void unselectHero() {
		if( selected_hero != null ) {
			if( hero_path.isPathMarked() ) {
				selected_hero.setPathMarked( hero_path.getPathList() );
				hero_path.removePath();
			}

			hud.unselectHero();
			selected_hero = null;
		}
	}

	private void checkCreaturesGroupEvent( CreaturesGroup group ) {
		if( players.get( turn ).isHeroSelected() ) {
			SquareTerrain square = group.getSquare();

			hud.selectEnemy( group );
			selected_group = group;

			if( hero_path.isPathMarked() && hero_path.isValidDestination( square.getNumber() ) ) {
				attack_square = square;
				hero_path.removeLastElement();
				moveSelectedHero( getAttackCallback() );
			}
			else if( hero_path.isPathMarked() && hero_path.isLastMarked( square.getNumber() ) )
				moveSelectedHero( null );
			else
				findPath( square.getNumber() );
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

	private TweenCallback getAttackCallback() {
		return new TweenCallback() {
			public void onEvent( int type, BaseTween<?> source ) {
				game.throwBattleScreen( selected_hero.getArmy(), attack_square.getArmy() );
			}
		};
	}

	private void checkInfo1Event() {
		if( status == NORMAL ) {
			showArmyInfoPanel();
			status = INFO1;
		}
		else if( status == INFO2 ) {
			army_info_panel.remove();
			showArmyInfoPanel();
			status = INFO1;
		}
		else {
			army_info_panel.remove();
			status = NORMAL;
		}
	}

	private void showArmyInfoPanel() {
		Vector2 panel_position = new Vector2(
			terrain.getStage().getCamera().position.x - 160,
			terrain.getStage().getCamera().position.y - MapConstants.TERRAIN_HEIGHT / 2 );

		army_info_panel = new ArmyInfoPanel( selected_hero, panel_position );
		terrain.getStage().addActor( army_info_panel );
	}

	private void checkInfo2Event() {
		if( status == NORMAL ) {
			showEnemyArmyInfoPanel();
			status = INFO2;
		}
		else if( status == INFO1 ) {
			army_info_panel.remove();
			showEnemyArmyInfoPanel();
			status = INFO2;
		}
		else {
			army_info_panel.remove();
			status = NORMAL;
		}
	}

	private void showEnemyArmyInfoPanel() {
		Vector2 position = new Vector2(
			terrain.getStage().getCamera().position.x - 160,
			terrain.getStage().getCamera().position.y - MapConstants.TERRAIN_HEIGHT / 2 );

		if( selected_hero_enemy != null ) {
			army_info_panel = new ArmyInfoPanel( selected_hero_enemy, position );
			terrain.getStage().addActor( army_info_panel );
		}
		else if( selected_group != null ) {
			army_info_panel = new ArmyInfoPanel( selected_group, position );
			terrain.getStage().addActor( army_info_panel );
		}
	}
}
