package com.modules.battle;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Constants;

/**
 * Panel at the bottom of the screen.
 * It has options button, magic book button and pass turn button.
 */
public class BattlePanel extends Group {

	/** Square size */
	static final float SIZE_H = 40.0f;
	static final float SIZE_W = Constants.SIZE_W;

	Button shield;
	Button magic_book;
	Button settings;

	Image background;

	float free_space;


	/**
	 * Class constructor
	 * @param stage
	 */
	public BattlePanel() {
		this.height = BattleConstants.SQUARE_SIZE_H + BattleConstants.SQUARE_SIZE_3D;
		this.width = BattleConstants.SQUARE_SIZE_W * 4;
		this.x = ( Constants.SIZE_W - width ) / 2;
		this.free_space = ( width - BattleConstants.BUTTONS_SIZE * 3 ) / 4;

		createBackgroundImage();
		createShieldButton();
		createSettingButton();
		createMagicBookButton();
	}

	private void createBackgroundImage() {
		background = new Image( Assets.getTextureRegion( "battlePanelBackground" ) );
		background.height = height;
		background.width = width;

		addActor( background );
	}

	private void createShieldButton() {
		shield = new Button(
			Assets.getFrame( "btnUnits", 1 ),
			Assets.getFrame( "btnUnits", 2 ));

		shield.height = BattleConstants.BUTTONS_SIZE;
		shield.width = BattleConstants.BUTTONS_SIZE;
		shield.x =  free_space;
		shield.y = ( background.height - shield.height ) / 2;

		shield.setClickListener( new ClickListener() {
			public void click( Actor actor, float x, float y ){
				Assets.playSound( "battle_turn", false );
				BattleController.addEvent( Constants.SHIELD, null );
			}
		});

		addActor( shield );
	}

	private void createMagicBookButton() {
		magic_book = new Button(
			Assets.getTextureRegion( "magicBook" ),
			Assets.getTextureRegion( "magicBook" ) );

		magic_book.height = BattleConstants.BUTTONS_SIZE;
		magic_book.width = BattleConstants.BUTTONS_SIZE;
		magic_book.x = BattleConstants.BUTTONS_SIZE + free_space * 2;
		magic_book.y = ( background.height - shield.height ) / 2;

		/*magic_book.setClickListener( new ClickListener() {
			public void click( Actor actor, float x, float y ) {
				BattleController.addEvent( Constants.MAGIC, null );
			}
		} );*/

		addActor( magic_book );
	}

	private void createSettingButton() {
		settings = new Button(
			Assets.getFrame( "btnExit", 1 ),
			Assets.getFrame( "btnExit", 2 ) );

		settings.height = BattleConstants.BUTTONS_SIZE;
		settings.width = BattleConstants.BUTTONS_SIZE;
		settings.x = BattleConstants.BUTTONS_SIZE * 2 + free_space * 3;
		settings.y = ( height - settings.height ) / 2;

		settings.setClickListener( new ClickListener() {
			public void click( Actor actor, float x, float y ) {
				Assets.playSound("button", false);
				BattleController.addEvent( Constants.SETTINGS, null );
			}
		} );

		addActor( settings );
	}
}
