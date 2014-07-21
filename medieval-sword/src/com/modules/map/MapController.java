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
import com.game.Assets;
import com.game.Constants;
import com.game.MyGdxGame;
import com.game.Player;
import com.modules.battle.BattleConstants;
import com.modules.castle.BuildingPanel;
import com.modules.castle.CastlePanel;
import com.modules.castle.TopCastle;
import com.modules.castle.TopCastleBuilding;
import com.modules.castle.TopCastleUnit;
import com.modules.castle.UnitPanel;
import com.modules.map.heroes.CreaturesGroup;
import com.modules.map.heroes.HeroPath;
import com.modules.map.heroes.HeroTop;
import com.modules.map.objetives.LevelObjectives;
import com.modules.map.terrain.ResourcePile;
import com.modules.map.terrain.ResourceStructure;
import com.modules.map.terrain.SquareTerrain;
import com.modules.map.terrain.Terrain;
import com.modules.map.ui.ArmyInfoPanel;
import com.modules.map.ui.CastleInfoPanel;
import com.modules.map.ui.CreaturesGroupPanel;
import com.modules.map.ui.HeroPanel;
import com.modules.map.ui.MapUserInterface;
import com.utils.StackViewAccessor;
import com.utils.Vector2i;

public class MapController {

	/* EVENTS INFO */
	static Object objectEvent;
	static int typeEvent;

	/* STATUS */
	static final int NORMAL = 0;
	static final int ANIMATION = 1;
	static final int INFO1 = 2;
	static final int INFO2 = 3;

	MyGdxGame game;
	List<Player> players;
	Terrain terrain;
	TweenManager manager;
	MapUserInterface ui;
	LevelObjectives objectives;

	// Castle panels
	CastlePanel castle_panel;
	BuildingPanel building_panel;
	UnitPanel unit_panel;

	// Map info panels
	ArmyInfoPanel army_info_panel;
	CastleInfoPanel castle_info_panel;

	// Selected things
	HeroTop selected_hero;
	HeroTop selected_hero_enemy;
	TopCastle selected_castle;
	CreaturesGroup selected_group;
	ResourceStructure selected_resource_structure;
	ResourcePile selected_pile;

	int turn;
	int day;

	HeroPath hero_path;
	SquareTerrain attack_square;

	static int status; 	// Semaphore


	public MapController( MyGdxGame game, List<Player> players, Terrain terrain,
			MapUserInterface ui, LevelObjectives objectives ) {
		this.game = game;
		this.players = players;
		this.terrain = terrain;
		this.ui = ui;
		this.objectives = objectives;

		objectEvent = null;
		typeEvent = -1;
		status = NORMAL;

		hero_path = new HeroPath( terrain );
		manager = new TweenManager();

		Assets.playMusic( Constants.MUSIC_MAP );
		//terrain.centerCamera( new Vector2(1800,1500) );
	}

	/**
	 * Add new event for processing it. Only one event can be added.
	 * @param type event type
	 * @param receiver object that has received the event
	 */
	public static void addEvent( int type, Object receiver ) {
		typeEvent = type;
		objectEvent = receiver;
	}

	public static void enableEvents() {
		status = NORMAL;
	}

	public void update() {
		manager.update( Gdx.graphics.getDeltaTime() );
		players.get( turn ).update( Gdx.graphics.getDeltaTime() );

		if( status != ANIMATION )
			checkEvents();
	}

	public void checkEvents() {
		switch(typeEvent) {
			case MapConstants.SQUARE:
				checkSquareEvent( (SquareTerrain) objectEvent );
				break;
			case MapConstants.HERO:
				checkHeroEvent( (HeroTop) objectEvent );
				break;
			case MapConstants.CREATURES:
				checkCreaturesGroupEvent( (CreaturesGroup) objectEvent );
				break;
			case MapConstants.INFO1:
				checkInfo1Event();
				break;
			case MapConstants.INFO2:
				checkInfo2Event();
				break;
			case MapConstants.RESOURCE_STRUCTURE:
				checkResourceStructureEvent( (ResourceStructure) objectEvent );
				break;
			case MapConstants.RESOURCE_PILE:
				checkResourcePileEvent( (ResourcePile) objectEvent );
				break;
			case MapConstants.CASTLE:
				checkCastleEvent( (TopCastle) objectEvent );
				break;
			case MapConstants.SHOW_BUILDING:
				showBuildingPanel(objectEvent);
				break;
			case MapConstants.BUILD_BUILDING:
					buyBuilding();
			case MapConstants.CLOSE_BUILDING:
				closeBuildingPanel();
				break;
			case MapConstants.SHOW_UNIT:
				showUnitPanel(objectEvent);
				break;
			case MapConstants.CLOSE_UNIT:
				closeUnitPanel();
				break;
			case MapConstants.BUY_UNITS:
				buyUnit();
				break;
			case MapConstants.TURN:
				passTurn();
				break;
			case MapConstants.SHOW_OPTIONS:
				MapInputProcessor.activatePanel();
				ui.showOptionsPanel();
				break;
			case MapConstants.SHOW_OBJECTIVES:
				MapInputProcessor.activatePanel();
				ui.showObjectivesPanel();
				break;
			case MapConstants.CLOSE_PANEL:
				closePanel();
				break;
			case MapConstants.EXIT_GAME:
				game.changeScreen( Constants.HOME_SCREEN );
				Assets.stopMusic( Constants.MUSIC_MAP );
				break;
		}

		if( typeEvent != BattleConstants.NONE ) {
			typeEvent = BattleConstants.NONE;
			objectEvent = null;
		}

		checkObjectives();
	}

	public Player getTurnPlayer() {
		return players.get( turn );
	}

	public void checkSquareEvent( SquareTerrain square ) {
		if( square.isRoadAvailable() )
			processMoveEvent( square );
		else
			System.out.println("Evento no registrado.");


		objectEvent = null;
		typeEvent = BattleConstants.NONE;
	}

	private void passTurn() {
		passDayAnimation();

		if( selected_hero != null ) {
			unselectHero();
		}
		else {
			players.get(turn).unselectHero();
		}

		terrain.passTurn( day );
		ui.updateHudResources( getTurnPlayer() );

		turn = ( turn + 1 ) % players.size();
		day = ( day + 1 ) % 7;
		ui.getHUD().passTurn( day + 1 );

		players.get(turn).passTurn();
		selectHero( players.get(turn).getHeroSelected() );
	}

	/**
	 * Show the animation for pass day
	 */
	private void passDayAnimation() {
		Assets.playSound("clock", false);

		Timeline line = Timeline.createSequence();
		line.push( Tween.to( null, StackViewAccessor.POSITION_XY, 1 ).ease( Linear.INOUT ) );

		line.push( Tween.call( new TweenCallback() {
			public void onEvent(int type, BaseTween<?> source) {
				ui.enableAll();
				Assets.setMusicVolume( Constants.MUSIC_MAP, 1 );
			}
		}));

		Assets.setMusicVolume( Constants.MUSIC_MAP, 0.5f );
		ui.disableAll();

		line.start( manager );
	}

	private void processMoveEvent( SquareTerrain square ) {
		if( players.get( turn ).isHeroSelected() ) {
			Assets.playSound("square_selected", false);

			if(  hero_path.isPathMarked() == false )
				findPath( square.getNumber() );
			else {
				// if square marked isn't the last of path, recalculates path
				if( hero_path.isLastMarked( square.getNumber() ) )
					moveSelectedHero( null );
				else {
					selected_hero.removePathMarked();
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

		if( hero_path.isLastAvailable() == false && selected_group == null ) {
			SquareTerrain square = hero_path.getLastSquareTerrain();

			if( square.hasCreaturesGroup() ) {
				selected_group = square.getGroup() ;
				ui.getHUD().selectEnemy( selected_group );
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

			line.push( Tween.call( new TweenCallback() {
				public void onEvent(int type, BaseTween<?> source) {
					Assets.stopSound("hero_walk");
				}
			}));

			Assets.playSound("hero_walk", true );
			selected_hero.removePathMarked();
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
		ui.getHUD().disable();

		SquareTerrain hero_square =
				players.get( turn ).getHeroSelected().getSquareTerrain();

		Vector2i last_square_number = hero_square.getNumber();

		terrain.removeFirstPathDrawnElement();

		for( Vector2i next_square_number : hero_path.getAvailableList() ) {
			Vector2 next_position = terrain.getSquarePosition( next_square_number );

			line.push( Tween.to( this,
					StackViewAccessor.POSITION_XY, MapConstants.HERO_SQUARE_TIME ).target(
							next_position.x + MapConstants.SQUARE_X_POSITION,
							next_position.y + MapConstants.SQUARE_Y_POSITION
					).ease( Linear.INOUT ) );

			line.push( Tween.call( getMoveItemCallback() ) );

			int orientation = getOrientation( last_square_number, next_square_number );
			players.get( turn ).getHeroSelected().addWalkAction( orientation );

			last_square_number = next_square_number;
		}

		line.push( Tween.call( new TweenCallback() {
			public void onEvent(int type, BaseTween<?> source) {
				status = NORMAL;
				ui.getHUD().enable();
			}
		}));
	}

	private TweenCallback getMoveItemCallback() {
		return new TweenCallback() {
			public void onEvent(int type, BaseTween<?> source) {
				Vector2i prev_square_number =
						selected_hero.getSquareTerrain().getNumber();
				SquareTerrain actual_square =
						terrain.getSquareTerrain( hero_path.getFirstElement() );

				terrain.explore( hero_path.getFirstElement(), selected_hero.getVision() );

				selected_hero.setSquareTerrain( actual_square );
				hero_path.removeFirstElement();

				ui.getHUD().getMiniMap().updatePosition( prev_square_number );
				ui.getHUD().getMiniMap().updatePosition(
						selected_hero.getSquareTerrain().getNumber() );

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

	private void checkHeroEvent( HeroTop hero ) {
		if( selected_hero == null ) {
			selectHero( hero );
			Assets.playSound( "chainmail1", false );
		}
		else {
			unselectPlayerHero();
			Assets.playSound( "chainmail2", false );
		}

		typeEvent = BattleConstants.NONE;
		objectEvent = null;
		// terrain.removePathDrawn();
	}

	private void selectHero( HeroTop hero ) {
		if( hero != null ) {
			selected_hero = hero;
			selected_hero.select();
			ui.getHUD().selectHero( selected_hero );

			hero_path.addPath( selected_hero.getPathMarked(),
					selected_hero.getActualMobility() );
		}
	}

	/**
	 * Deselect hero_selected from controller
	 */
	private void unselectHero() {
		if( selected_hero != null ) {
			if( hero_path.isPathMarked() ) {
				selected_hero.setPathMarked( hero_path.getPathList() );
				hero_path.removePath();
			}

			selected_hero = null;
		}
	}

	/**
	 * Deselect hero_selected from controller and player hero selected
	 */
	private void unselectPlayerHero() {
		if( selected_hero != null ) {
			if( hero_path.isPathMarked() ) {
				selected_hero.setPathMarked( hero_path.getPathList() );
				hero_path.removePath();
			}

			ui.getHUD().unselectHero();
			selected_hero.unselect();
			selected_hero = null;
		}
	}

	private void checkCreaturesGroupEvent( CreaturesGroup group ) {
		Assets.playSound("square_selected", false);

		if( selected_hero != null ) {
			SquareTerrain square = group.getSquare();

			selected_group = group;
			ui.getHUD().selectEnemy( selected_group );

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
				ui.getHUD().selectEnemy( group );
				selected_group = group;
			}
			else {
				ui.getHUD().unselectEnemy();
				selected_group = null;
			}
		}

		typeEvent = BattleConstants.NONE;
		objectEvent = null;
	}

	private TweenCallback getAttackCallback() {
		final TweenCallback battle_callback = new TweenCallback() {
			public void onEvent(int type, BaseTween<?> source) {
				game.throwBattleScreen( selected_hero.getArmy(), attack_square.getArmy() );
			}
		};

		return new TweenCallback() {
			public void onEvent( int type, BaseTween<?> source ) {
				ui.showBlackAnimation( manager, battle_callback );
			}
		};
	}

	/**
	 * Check click event in the first Info box from HUD
	 */
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

	/**
	 * Show army selected info panel
	 */
	private void showArmyInfoPanel() {
		if( selected_hero != null ) {
			Assets.playSound("button", false);
			ui.disableAll();

			army_info_panel = new HeroPanel( selected_hero );
			ui.getStage().addActor( army_info_panel );

			MapInputProcessor.activatePanel();
		}
	}

	/**
	 * Check click event in the second Info box from HUD
	 */
	private void checkInfo2Event() {
		if( status == NORMAL ) {
			showSecondInfoPanel();
			status = INFO2;
		}
		else if( status == INFO1 ) {
			if( army_info_panel != null )
				army_info_panel.remove();
			else if( castle_panel != null )
				castle_panel.remove();

			showSecondInfoPanel();
			status = INFO2;
		}
		else {
			if( army_info_panel != null )
				army_info_panel.remove();

			status = NORMAL;
		}
	}

	/**
	 * Show icon of army in the HUD
	 */
	private void showSecondInfoPanel() {
		if( selected_hero_enemy != null ) {
			Assets.playSound( "button", false );
			ui.disableAll();
			army_info_panel = new HeroPanel( selected_hero_enemy );
			ui.getStage().addActor( army_info_panel );
			MapInputProcessor.activatePanel();
		}
		else if( selected_group != null ) {
			Assets.playSound( "button", false );
			ui.disableAll();
			army_info_panel = new CreaturesGroupPanel( selected_group );
			ui.getStage().addActor( army_info_panel );
			MapInputProcessor.activatePanel();
		}
		else if( selected_castle != null ) {
			Assets.playSound( "button", false );

			if(  selected_castle.getOwner() == getTurnPlayer() )
				showCastlePanel();
			else
				showCastleInfoPanel();
		}
	}

	/**
	 * Return from battle module to map module
	 */
	public void returnFromBattle() {
		ui.hideBlackAnimation( manager, null );

		// player has won the battle
		if( selected_hero.getArmy().getStacks().size() > 0 ) {
			ui.getHUD().unselectEnemy();
			selected_group.destroy( terrain.getStage() );
		}
	}

	private void checkResourceStructureEvent( ResourceStructure structure ) {
		selected_resource_structure = structure;

		if( selected_hero != null ) {
			Assets.playSound("square_selected", false);
			Vector2i square_number = structure.getUseSquareNumber();

			if( hero_path.isPathMarked() && hero_path.isValidDestination( square_number ) ) {
				if( isHeroOnStructureSquare() )
					captureResourceStructure();
				else
					moveSelectedHero( getResourceStructureCallback() );
			}
			else if( hero_path.isPathMarked() && hero_path.isLastMarked( square_number ) )
				moveSelectedHero( null );
			else
				findPath( square_number );
		}
	}

	private boolean isHeroOnStructureSquare() {
		return selected_resource_structure.getUseSquareNumber().equal(
				selected_hero.getSquareTerrain().getNumber() );
	}

	private TweenCallback getResourceStructureCallback() {
		return new TweenCallback() {
			public void onEvent( int type, BaseTween<?> source ) {
				captureResourceStructure();
			}
		};
	}

	/**
	 * Selected hero capture resource structure
	 * Explore map and update mini_map.
	 */
	private void captureResourceStructure() {
		selected_resource_structure.use( players.get(turn) );
		terrain.captureStructure( selected_resource_structure );
		Assets.playSound("capture_structure", false);
	}

	private void checkResourcePileEvent( ResourcePile pile ) {
		if( selected_hero != null ) {
			Assets.playSound("square_selected", false);

			Vector2i square_number = pile.square_position_number;

			if( hero_path.isPathMarked() && hero_path.isValidDestination( square_number ) ) {
				selected_pile = pile;
				hero_path.removeLastElement();
				moveSelectedHero( getResourcePileCallback() );
			}
			else if( hero_path.isPathMarked() && hero_path.isLastMarked( square_number ) )
				moveSelectedHero( null );
			else
				findPath( square_number );
		}

		typeEvent = BattleConstants.NONE;
		objectEvent = null;
	}

	private TweenCallback getResourcePileCallback() {
		return new TweenCallback() {
			public void onEvent(int type, BaseTween<?> source) {
				selected_pile.use( players.get(turn) );
				terrain.getSquareTerrain( selected_pile.square_position_number ).setFree();
				terrain.getStage().removeActor( selected_pile.getActor() );
				ui.updateHudResources( getTurnPlayer() );
			}
		};
	}

	/******************** CASTLE **********************/

	private void checkCastleEvent( TopCastle castle ) {
		selected_castle = castle;
		selected_group = null;
		ui.getHUD().selectCastle( castle );
		Assets.playSound("square_selected", false);

		if( selected_hero != null ) {
			Vector2i square_number = castle.getUseSquareNumber();

			if( hero_path.isPathMarked() && hero_path.isValidDestination( square_number ) ) {
				if( isHeroOnCastleSquare() )
					useSelectedCastle();
				else
					moveSelectedHero( getCastleCallback() );
			}
			else if( hero_path.isPathMarked() && hero_path.isLastMarked( square_number ) )
				moveSelectedHero( null );
			else
				findPath( square_number );
		}
	}

	private void showCastleInfoPanel() {
		ui.disableAll();
		castle_info_panel = new CastleInfoPanel( selected_castle );
		ui.getStage().addActor( castle_info_panel );
		MapInputProcessor.activatePanel();
	}

	private void showCastlePanel() {
		ui.disableAll();

		if( selected_hero != null && selected_castle != null ) {
			Vector2i hero_square = selected_hero.getSquareTerrain().getNumber();
			Vector2i castle_square = selected_castle.getUseSquareNumber();

			if( hero_square.equal( castle_square ) )
				castle_panel = new CastlePanel( selected_castle, selected_hero.getArmy() );
			else
				castle_panel = new CastlePanel( selected_castle, null );
		}
		else if( selected_castle != null )
			castle_panel = new CastlePanel( selected_castle, null );

		ui.getStage().addActor( castle_panel );
		MapInputProcessor.activatePanel();
	}

	private boolean isHeroOnCastleSquare() {
		return selected_castle.getUseSquareNumber().equal(
				selected_hero.getSquareTerrain().getNumber() );
	}

	private void useSelectedCastle() {
		if( selected_hero == null ) {
			if( selected_castle.getOwner() == getTurnPlayer() )
				showCastlePanel();
			else
				showCastleInfoPanel();
		}
		else if( selected_castle.getOwner() == null ) {
			selected_castle.use( getTurnPlayer() );
			terrain.captureStructure( selected_castle.getStructure() );
			showCastlePanel();
		}
		else if( selected_castle.getOwner() == getTurnPlayer() ) {
			showCastlePanel();
		}
		else {
			// batlle
		}
	}

	private TweenCallback getCastleCallback() {
		return new TweenCallback() {
			public void onEvent( int type, BaseTween<?> source ) {
				useSelectedCastle();
			}
		};
	}

	public void showBuildingPanel( Object object_event ) {
		TopCastleBuilding building = (TopCastleBuilding) object_event;
		building_panel = new BuildingPanel( building );

		castle_panel.selectBuilding( building );
		castle_panel.hide();

		ui.getStage().addActor( building_panel );
	}

	public void buyBuilding() {
		castle_panel.buildSelectedBuilding();
		castle_panel.updateBuildings();

		ui.updateHudResources( getTurnPlayer() );
		ui.getStage().removeActor( building_panel );

		castle_panel.show();
	}

	public void closeBuildingPanel() {
		castle_panel.show();
		ui.getStage().removeActor( building_panel );
	}

	public void showUnitPanel( Object object_event ) {
		TopCastleUnit tc_unit = (TopCastleUnit) object_event;
		unit_panel = new UnitPanel( tc_unit );
		// castle_panel.selectUnit( tc_unit );
		castle_panel.hide();

		ui.getStage().addActor( unit_panel );
	}

	public void buyUnit() {
		castle_panel.updateUnits();
		castle_panel.updateArmy( selected_castle.getArmy() );
		castle_panel.show();
		ui.getStage().removeActor( unit_panel );
		ui.getHUD().updateGold( players.get(turn).gold );
	}

	public void closeUnitPanel() {
		castle_panel.show();
		ui.getStage().removeActor( unit_panel );
	}

	public void closePanel() {
		status = NORMAL;
		MapInputProcessor.deactivatePanel();
		ui.enableAll();
	}

	public HeroTop getSelectedHero() {
		return selected_hero;
	}

	public void centerCamera(Vector2 position) {
		terrain.centerCamera( position );
	}

	private void checkObjectives() {
		int aux = objectives.checkObjetives( getTurnPlayer(), terrain.getCastles() );

		if( aux == Constants.OBJ_WIN ) {
			Timeline line = Timeline.createSequence();
			line.push( Tween.to( null, StackViewAccessor.POSITION_XY, 1.5f ).ease( Linear.INOUT ) );

			line.push( Tween.call( new TweenCallback() {
				public void onEvent(int type, BaseTween<?> source) {
					ui.completeAllObjectives();
					ui.showEndPanel(true);
					Assets.stopMusic( Constants.MUSIC_MAP );
					Assets.playSound( "battle_win", false );
				}
			}));

			line.start( manager );
		}
		else if( aux == Constants.OBJ_LOST ) {
			ui.showEndPanel(false);
			Assets.playSound( "objective_completed", false );
		}
		else if( aux >= 0 ) {
			ui.completeObjective( aux );
			Assets.playSound( "objective_completed", false );
		}
	}
}
