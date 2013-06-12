package com.modules.battle;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Constants;

/**
 * Panel at the bottom of the screen.
 * It has options button, magic book button and pass turn button.
 */
public class BattlePanel {	
	
	/** Square size */
	static final float SIZE_H = 40.0f;
	static final float SIZE_W = Constants.SIZE_W;
	
	Button shield;
	Button magic_book;
	Button settings;
	
	Image background;
	
	/**
	 * Class constructor
	 * @param stage
	 */
	public BattlePanel( Stage stage ) {
		createBackgroundImage();
		createShieldButton();
		createMagicBookButton();
		createSettingButton();
		
		stage.addActor( background );
		stage.addActor( shield );
		stage.addActor( magic_book );
		stage.addActor( settings );
	}
	
	private void createBackgroundImage() {
		background = new Image( Assets.getTextureRegion( "number" ) );
		background.height = SIZE_H;
		background.width = SIZE_W;
	}
	
	private void createShieldButton() {
		shield = new Button(Assets.getTextureRegion( "shield" ),
				Assets.getTextureRegion( "number" ));
		
		shield.height = 30;
		shield.width = 35;
		shield.x = 80;
		shield.y = 5;
		
		shield.setClickListener( new ClickListener() {	
			public void click(Actor actor, float x, float y) {
				BattleController.addEvent( Constants.SHIELD, null );	
			}
		});
	}
	
	private void createMagicBookButton() {
		magic_book = new Button( Assets.getTextureRegion( "magicBook" ),
				Assets.getTextureRegion( "number" ) );
		
		magic_book.height = 30;
		magic_book.width = 35;
		magic_book.x = 140;
		magic_book.y = 5;
		
		magic_book.setClickListener( new ClickListener() {
			public void click( Actor actor, float x, float y ) {
				BattleController.addEvent( Constants.MAGIC, null );	
			}
		} );
	}
	
	private void createSettingButton() {
		settings = new Button( Assets.getTextureRegion( "settings" ),
				Assets.getTextureRegion( "number" ) );
		
		settings.height = 30;
		settings.width = 35;
		settings.x = 370;
		settings.y = 5;
		
		settings.setClickListener( new ClickListener() {
			public void click( Actor actor, float x, float y ) {
				BattleController.addEvent( Constants.SETTINGS, null );	
			}
		} );
	}
}
