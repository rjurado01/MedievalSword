package com.me.modules.battle;

import com.me.mygdxgame.Assets;
import com.me.mygdxgame.Object;
import com.me.mygdxgame.Player;
import com.me.mygdxgame.Unit;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class BattleRenderer {

	private Board board;
	Player players [];
	BattlePanel panel;
	
	private OrthographicCamera guiCam;
	
	private SpriteBatch spriteBatch;

	ShapeRenderer debugRenderer = new ShapeRenderer(); 	// for debug rendering
	
	private BitmapFont font;

	/**
	 * Class constructor
	 * @param board
	 * @param player1
	 */
	public BattleRenderer(Board board, Player players [], BattlePanel panel ) {
		this.board = board;
		this.players = players;
		this.panel = panel;
		
		this.guiCam = new OrthographicCamera(BattleScreen.SIZE_W, BattleScreen.SIZE_H);
		this.guiCam.position.set(BattleScreen.SIZE_W / 2, BattleScreen.SIZE_H / 2, 0);
		
		font = new BitmapFont(Gdx.files.internal("fonts/font1.fnt"),
		         Gdx.files.internal("fonts/font1_0.png"), false);
		
		//font.setScale( 1.2f );
		
		spriteBatch = new SpriteBatch();
	}

	/**
	 * Render game
	 */
	public void render() {
		GL10 gl = Gdx.graphics.getGL10();
		
		// Clear screen
		gl.glClearColor(0,0,0,1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		guiCam.update();
		
		spriteBatch.setProjectionMatrix(guiCam.combined);
		
		spriteBatch.enableBlending();
		spriteBatch.begin();

		// Render board
		for (SquareBoard square : board.getSquaresList()) {
			renderObject(square, square.getTextureName());
			
			if( square.getStatusTexture() != null )
				renderObjectAlphaSize(square, square.getStatusTexture(), 
						square.getPosition().x + 1, 
						square.getPosition().y + 1, 
						square.getBounds().width - 2, 
						square.getBounds().height - 2,0.6f);
		}

		// Render units
		for( int i = 0; i < 2; i++) {
			for( Unit u : players[i].getUnits() ) {
				u.update(Gdx.graphics.getDeltaTime());

				spriteBatch.draw(u.getRenderFrame(Gdx.graphics.getDeltaTime()),
						u.getPosition().x, u.getPosition().y,  u.getBounds().width, u.getBounds().height);
				
				if( u.showNumber() ) {
					spriteBatch.draw( Assets.getTexture("number"), u.getPosition().x - 5, u.getPosition().y - 5, 20, 15 );
			
					if( Integer.toString( u.getNumber() ).length() == 1 )
						font.draw(spriteBatch, Integer.toString( u.getNumber() ), u.getPosition().x + 4, u.getPosition().y + 6);
					else
						font.draw(spriteBatch, Integer.toString( u.getNumber() ), u.getPosition().x, u.getPosition().y + 6);
				}
			}
		}

		// Render Panel
		spriteBatch.draw( Assets.getTexture("number"), 0, 0, BattleScreen.SIZE_W, 40 );
		
		for( Object obj : panel.getObjects() ) {
			renderObject( obj );
		}
		
		spriteBatch.end();
	}
	
	/**
	 * Render a object
	 * @param obj object to render
	 * @param texture texture to render object ( if it's null use object texture )
	 */
	public void renderObject(Object obj, String texture) {
		spriteBatch.draw(Assets.getTexture(texture), 
				obj.getPosition().x, 
				obj.getPosition().y, 
				obj.getBounds().width, 
				obj.getBounds().height);
	}
	
	/**
	 * Render a object
	 * @param obj object to render
	 */
	public void renderObject( Object obj ) {
		spriteBatch.draw( obj.getActualTexture(), 
				obj.getPosition().x, 
				obj.getPosition().y, 
				obj.getBounds().width, 
				obj.getBounds().height);
	}
	
	/**
	 * Render a object
	 * @param obj object to render
	 * @param texture texture to render object ( if it's null use object texture )
	 * @param alpha alpha index ( 0 - 1)
	 */
	public void renderObjectAlpha(Object obj, String texture, float alpha) {
		drawTextureAlpha(Assets.getTexture(texture), 
				obj.getPosition().x + 1, 
				obj.getPosition().y + 1, 
				obj.getBounds().width - 2, 
				obj.getBounds().height - 2,
				alpha);
	}
	
	/**
	 * Render a object
	 * @param obj object to render
	 * @param texture texture to render object ( if it's null use object texture )
	 * @param alpha alpha index ( 0 - 1)
	 */
	public void renderObjectAlphaSize(Object obj, String texture, float x, float y, float w, float h, float alpha) {
		drawTextureAlpha(Assets.getTexture(texture), x, y, w, h, alpha);
	}
	
	/**
	 * Calculate screen coordinates from absolute coordinates
	 * @param x
	 * @param y
	 * @return
	 */
	public Vector2 unproject(int x, int y) {
		Vector3 aux = new Vector3();
		guiCam.unproject(aux.set(x, y, 0));
		
		return new Vector2(aux.x, aux.y);
	}
	
	/**
	 * Draw a texture with alpha
	 * @param texture
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param alpha
	 */
	public void drawTextureAlpha(TextureRegion texture, float x, float y, float width, float height, float alpha ) {
		// Get current Color and save alpha
		Color color = spriteBatch.getColor();
		float oldAlpha = color.a;
		
		// Set new alpha
		color.a = oldAlpha * alpha;
		spriteBatch.setColor(color);
		
		spriteBatch.draw(texture, x, y, width, height);
		
		// Set it back to orginial alpha
		color.a = oldAlpha;
		spriteBatch.setColor(color);
	}
}