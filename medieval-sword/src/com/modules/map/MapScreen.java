package com.modules.map;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Army;
import com.game.Assets;
import com.game.Constants;
import com.game.MyGdxGame;
import com.game.Player;
import com.level.Level;
import com.level.LoadParser;
import com.modules.castle.TopCastle;
import com.modules.map.heroes.CreaturesGroup;
import com.modules.map.heroes.HeroTop;
import com.modules.map.objetives.LevelObjectives;
import com.modules.map.objetives.LevelObjectives1;
import com.modules.map.terrain.MapObjectsTypes;
import com.modules.map.terrain.ResourcePile;
import com.modules.map.terrain.ResourceStructure;
import com.modules.map.terrain.Terrain;
import com.modules.map.ui.MapUserInterface;
import com.utils.HeroAccessor;
import com.utils.ImageAlphaAccessor;

/**
 * Screen that initializes, updates and renders the map.
 */
public class MapScreen implements Screen {

  MyGdxGame game;
  Stage terrain_stage;
  Stage ui_stage;

  LoadParser parser;

  public List<Player> players;
  public List<CreaturesGroup> creatures;
  public List<ResourceStructure> resource_structures;
  Player humand_player;

  Level level;
  Terrain terrain;
  MapController controller;
  MapInputProcessor input;
  MapUserInterface ui;
  LevelObjectives objectives;

  public MapScreen( MyGdxGame game, Level level ) {
    this.game = game;
    this.level = level;
    this.parser = new LoadParser();

    players = new ArrayList<Player>();

    loadTerrain();
    loadMapObjects();
    loadMapUnits();
    loadMapCreatures();
    loadPlayers();
    loadStructures();
    loadResourcePiles();
    loadCastles();
    loadHeroes();
    loadFog();
    loadObjectives();
    loadUserInterface();

    terrain_stage.getCamera().translate( -Constants.HUD_WIDTH, 0, 0 );
    input = new MapInputProcessor( terrain, ui_stage );
  }

  private void loadTerrain() {
    terrain_stage = new Stage( Constants.SIZE_W, Constants.SIZE_H, true );
    terrain = parser.getTerrain( level.terrain );
    terrain.addStage( terrain_stage );
  }

  private void loadMapObjects() {
    MapObjectsTypes object_types = new MapObjectsTypes();

    terrain.setObjects( parser.getMapObjects( level, object_types ) );

    for( MapActor object : terrain.getObjects() )
      terrain_stage.addActor( object.getActor() );
  }

  private void loadObjectives() {
    if( level.level == 1 ) {
      objectives = new LevelObjectives1();

      if( level.objectives_completed != null ) {
        for( int i = 0; i < objectives.getNumber(); i++ ) {
          if( level.objectives_completed[i] == 1 )
            objectives.setCompleted(i);
        }
      }
    }
  }

  private void loadUserInterface() {
    ui_stage = new Stage( Constants.SIZE_W, Constants.SIZE_H, true );
    ui = new MapUserInterface( ui_stage, objectives );
    ui.createHUD( humand_player, terrain );

    terrain.setMiniMap( ui.getHUD().getMiniMap() );
  }

  private void loadMapUnits() {
    Assets.loadUnits();
  }

  private void loadMapCreatures() {
    creatures = parser.getCreaturesGroups( terrain, level );

    for( CreaturesGroup group : creatures )
      terrain_stage.addActor( group.getImage() );
  }

  private void loadPlayers() {
    players.add( parser.getPlayer( terrain, level.players.get(0) ) );

    if( level.players.get(0).human )
      humand_player = players.get( 0 );
  }

  private void loadHeroes() {
    for( HeroTop hero : players.get(0).getHeroes() )
      terrain_stage.addActor( hero.getView() );
  }

  private void loadStructures() {
    terrain.setResourceStructures( parser.getResourceStructures( players, level ) );

    for( ResourceStructure structure : terrain.getResourceStructures() ) {
      terrain_stage.addActor( structure.getActor() );
      terrain.addStructure( structure );
    }
  }

  private void loadResourcePiles() {
    terrain.setResourcePiles( parser.getResourcePiles( level ) );

    for( ResourcePile pile : terrain.getResourcePiles() ) {
      terrain.getSquareTerrain(
          pile.square_position_number ).setResourcePileStatus();
      terrain_stage.addActor( pile.getActor() );
    }
  }

  private void loadCastles() {
    terrain.setCastles( parser.getMapCastles(players, level) );

    for( TopCastle castle : terrain.getCastles() ) {
      terrain_stage.addActor( castle.getView() );
      terrain.addStructure( castle.getStructure() );
    }
  }

  private void loadFog() {
    terrain.drawFog();
  }

  public void render(float delta) {
    controller.update();

    Gdx.gl.glClearColor(0.8f, 0.8f, 1.f, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    terrain_stage.act( Gdx.graphics.getDeltaTime() );
    terrain_stage.draw();

    ui_stage.act( Gdx.graphics.getDeltaTime() );
    ui_stage.draw();
  }

  public void resize(int width, int height) {
    /*// save aspect ratio
      terrain_stage.setViewport( Constants.SIZE_W, Constants.SIZE_H, false );
      float crop_x1 = ( Constants.SIZE_W - terrain_stage.getCamera().viewportWidth) / 2;
      terrain_stage.getCamera().translate( crop_x1, 0, 0 );

      ui_stage.setViewport( Constants.SIZE_W, Constants.SIZE_H, false );
      float crop_x2 = ( Constants.SIZE_W - ui_stage.getCamera().viewportWidth) / 2;
      ui_stage.getCamera().translate( crop_x2, 0, 0 );*/
  }

  public void show() {
    Tween.registerAccessor( MapController.class, new HeroAccessor() );
    Tween.registerAccessor( Image.class, new ImageAlphaAccessor() );

    if( controller == null )
      controller = new MapController( game, players, terrain, ui, objectives, creatures );
    else
      controller.returnFromBattle();

    Gdx.input.setInputProcessor( input );
  }

  public void loadLevel( int level ) {
    switch( level ) {
      case 0:
        break;
    }

  }

  @Override
  public void hide() {
    // TODO Auto-generated method stub

  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub

  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub

  }

  @Override
  public void dispose() {
    // TODO Auto-generated method stub

  }

  public Army getPlayerArmy( int player_number) {
    return players.get( player_number ).getHeroSelected().getArmy();
  }

  public CreaturesGroup getCreaturesGroup2() {
    return creatures.get( 0 );
  }

}
