package com.modules.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	Parser parser;
	
	public Map<Integer, Unit> units;
	
	public List<Player> players;
	public List<CreaturesGroup> creatures;
	public List<ResourceStructure> resource_structures;
	Player humand_player;
	
	Level level;
	Terrain terrain;
	HUD hud;
	MapController controller;
	MapInputProcessor input;
	
	public MapScreen( MyGdxGame game, Level level ) {
		this.game = game;
		this.level = level;
		this.parser = new Parser();
		
		players = new ArrayList<Player>();

		loadTerrain();
		loadMapUnits();		
		loadMapCreatures();
		loadPlayers();
		loadStructures();
		loadHUD();
		
		terrain_stage.getCamera().translate( -Constants.HUD_WIDTH, 0, 0 );
		terrain_stage.addActor( terrain );
		//hud_stage.addActor( mini_map );

		input = new MapInputProcessor( terrain, hud );
	}
	
	private void loadTerrain() {
		terrain_stage = new Stage( Constants.SIZE_W, Constants.SIZE_H, true );	
		terrain = parser.getTerrain( level.terrain );
		terrain.addStage( terrain_stage );
	}
	
	private void loadHUD() {
		hud_stage = new Stage( Constants.SIZE_W, Constants.SIZE_H, true );
		hud = new HUD( hud_stage, terrain );
		hud.updateGold( humand_player.gold );
		hud.updateWood( humand_player.wood );
		hud.updateStone( humand_player.stone );
	}
	
	private void loadMapUnits() {
		units = new HashMap<Integer, Unit>();
		units.put( Constants.VILLAGER, new Villager() );
		units.put( Constants.ARCHER, new Archer() );
	}
	
	private void loadMapCreatures() {
		creatures = parser.getCreaturesGroups( terrain, units, level );
		
		for( CreaturesGroup group : creatures )
			terrain_stage.addActor( group.getImage() ); 
	}
	
	private void loadPlayers() {
		players.add( parser.getPlayer( terrain, units, level.players.get(0) ) );
		
		if( level.players.get(0).humand )
			humand_player = players.get( 0 );
		
		for( HeroTop hero : players.get(0).getHeroes() )
			terrain_stage.addActor( hero.getView() );
	}
	
	private void loadStructures() {
		terrain.resource_structures = parser.getResourceStructures( players, level );
		
		for( ResourceStructure structure : terrain.resource_structures )
			terrain_stage.addActor( structure.getActor() );
	}
	
	public void render(float delta) {
		controller.update();
		
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
		if( controller == null )
			controller = new MapController( game, players, terrain, hud );
		else
			controller.returnToBattle();
		
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
	
	public CreaturesGroup getCreaturesGroup() {
		return creatures.get( 0 );
	}

}
