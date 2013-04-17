package com.modules.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.level.Level;
import com.level.Parser;
import com.mygdxgame.Army;
import com.mygdxgame.Constants;
import com.mygdxgame.MyGdxGame;
import com.mygdxgame.Player;
import com.mygdxgame.Unit;
import com.races.humands.units.Archer;
import com.races.humands.units.Villager;

/**
 * Screen that initialize, update and render map. 
 */
public class MapScreen implements Screen {

	MyGdxGame game;
	Stage terrain_stage;
	Stage hud_stage;
	
	public Map<String, Unit> units;
	
	public ArrayList<Army> enemies;
	public Player player;
	
	Level level;
	Terrain terrain;
	HUD hud;
	
	public MapScreen( MyGdxGame game, Level level ) {
		this.game = game;
		this.level = level;

		loadTerrain();
		loadHUD();
		loadMapUnits();		
		loadMapEnemies();
		loadPlayers();
		loadStructures();
	}
	
	private void loadTerrain() {
		terrain_stage = new Stage( Constants.SIZE_W, Constants.SIZE_H, true );	
		terrain = new Terrain( terrain_stage, level );
	}
	
	private void loadHUD() {
		hud_stage = new Stage( Constants.SIZE_W, Constants.SIZE_H, true );
		hud = new HUD( hud_stage );
	}
	
	private void loadMapUnits() {
		units = new HashMap<String, Unit>();
		units.put( "villager", new Villager() );
		units.put( "archer", new Archer() );
	}
	
	private void loadMapEnemies() {
		enemies = new ArrayList<Army>();
		/*enemies.add( new Army() );

		enemies.get( 0 ).addStack( new Stack( units.get( "villager" ), 10, Constants.RED ) );
		enemies.get( 0 ).addStack( new Stack( units.get( "archer" ), 12, Constants.RED ) );
		enemies.get( 0 ).addStack( new Stack( units.get( "villager" ), 10, Constants.RED ) ); */
	}
	
	private void loadPlayers() {
		Parser parser = new Parser( terrain );
		player = parser.getPlayer( level.players.get(0) );
		
		for( HeroTop hero : player.getHeroes() )
			terrain_stage.addActor( hero.getView() );
	}
	
	private void loadStructures() {
		
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0.8f, 0.8f, 1.f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		terrain_stage.act( Gdx.graphics.getDeltaTime() );
		terrain_stage.draw();
		
		hud_stage.act( Gdx.graphics.getDeltaTime() );
		hud_stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	public void show() {
		terrain_stage.getCamera().translate( -Constants.HUD_WIDTH, 0, 0 );
		terrain_stage.addActor( terrain );

		Gdx.input.setInputProcessor( new MapInputProcessor( terrain ) );
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
	
	public Army getPlayerArmy() {
		return player.getHeroSelected().getArmy();
	}
	
	public Army getEnemyArmy() {
		return enemies.get( 0 );
	}

}
