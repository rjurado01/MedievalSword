package com.me.modules.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.me.mygdxgame.Army;
import com.me.mygdxgame.Assets;
import com.me.mygdxgame.Constants;
import com.me.mygdxgame.Hero;
import com.me.mygdxgame.MyGdxGame;
import com.me.mygdxgame.Player;
import com.me.mygdxgame.Stack;
import com.me.mygdxgame.Unit;
import com.me.units.Archer;
import com.me.units.Villager;

/**
 * Screen that initialize, update and render map. 
 */
public class MapScreen implements Screen {

	MyGdxGame game;
	Stage stage;
	
	public Map<String, Unit> units;
	
	public ArrayList<Army> enemies;
	public Player player;
	
	
	public MapScreen( MyGdxGame game ) {
		this.game = game;
		
		loadMapUnits();		
		addMapEnemies();
		addPlayerHero();
	}
	
	private void loadMapUnits() {
		units = new HashMap<String, Unit>();
		units.put( "villager", new Villager() );
		units.put( "archer", new Archer() );
	}
	
	private void addMapEnemies() {
		enemies = new ArrayList<Army>();
		enemies.add( new Army() );

		enemies.get( 0 ).addStack( new Stack( units.get( "villager" ), 10, Constants.RED ) );
		enemies.get( 0 ).addStack( new Stack( units.get( "archer" ), 12, Constants.RED ) );
		enemies.get( 0 ).addStack( new Stack( units.get( "villager" ), 10, Constants.RED ) );
	}
	
	private void addPlayerHero() {
		player = new Player();
		player.addHero( new Hero() );
		
		player.getHeroSelected().getArmy().addStack(
				new Stack( units.get( "villager" ), 22, Constants.BLUE ) );
		player.getHeroSelected().getArmy().addStack(
				new Stack( units.get( "villager" ), 18, Constants.BLUE ) );
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0.8f, 0.8f, 1.f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act( Gdx.graphics.getDeltaTime() );
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	public void show() {
		stage = new Stage( Constants.SIZE_W, Constants.SIZE_H, true);		
		Gdx.input.setInputProcessor( stage );
	
		// button for test application, this will remove
		createCheckButton();
	}
	
	private void createCheckButton() {
		Button button = new Button( Assets.getTextureRegion( "settings" ),
				Assets.getTextureRegion( "number" ) );
		
		stage.addActor( button );
		
		button.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				game.changeScreen( 2 );
			}
		});
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
