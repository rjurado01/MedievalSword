package com.modules.map.ui;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;
import com.game.Constants;
import com.modules.battle.BattleConstants;
import com.modules.battle.BattleController;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.utils.Vector2i;

/**
 * Represent options menu in battle screen.
 * It is show when user touch options button.
 */
public class ExitAlert extends Group {

	static final int SIZE_H = 200;
	static final int SIZE_W = 300;

	Actor resume_panel;

	Image alpha;
	Image background;

	Button exit_btn;
	Button resume_btn;

	Label alert_label;

	protected Map<String, String> alert_msg;

	public ExitAlert( Vector2i window_size, Vector2i window_position, Actor actor ) {
		alert_msg = new HashMap<String, String>();
		alert_msg.put("es", "¿Seguro que quiere salir?");
		alert_msg.put("en", "Are you sure you want to exit?");
		resume_panel = actor;

		createAlphaImage();
		createBackgroundImage( window_size, window_position );
		createAlertLabel();
		createExitButton();
		createResumeButton();

		this.width = SIZE_W;
		this.height = SIZE_H;
		this.x = 0;
		this.y = 0;
	}

	private void createAlphaImage() {
		alpha = new Image( Assets.getTextureRegion( "disabledBackground" ) );
		alpha.height = Constants.SIZE_H;
		alpha.width = Constants.SIZE_W;
		alpha.color.a = 0.5f;

		alpha.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {}
		});

		addActor( alpha );
	}

	private void createBackgroundImage( Vector2i window_size, Vector2i window_position ) {
		background = new Image( Assets.getTextureRegion( "battlePanelBackground" ) );
		background.height = SIZE_H;
		background.width = SIZE_W;
		background.x = window_position.x + ( window_size.x - SIZE_W ) / 2;
		background.y = window_position.y + ( window_size.y - SIZE_H ) / 2;
		addActor( background );
	}

	private void createAlertLabel() {
		alert_label = new Label( alert_msg.get( Constants.LANGUAGE), Assets.skin );
		alert_label.x = background.x + ( background.width - alert_label.width ) / 2;
		alert_label.y = background.y + 125;
		addActor( alert_label );
	}

	private void createExitButton() {
		exit_btn = new Button(
			Assets.getFrame( "okButton", 1 ),
			Assets.getFrame( "okButton", 2 ) );

		exit_btn.height = 50;
		exit_btn.width = 100;
		exit_btn.x = background.x + 40;
		exit_btn.y = background.y + 50;

		exit_btn.setClickListener( new ClickListener() {
			public void click( Actor actor, float x, float y ) {
				Assets.playSound( "button", false );
				MapController.addEvent( MapConstants.EXIT_GAME, null );
				BattleController.addEvent( BattleConstants.EXIT, null );
			}
		});

		this.addActor( exit_btn );
	}

	private void createResumeButton() {
		resume_btn = new Button(
			Assets.getFrame( "cancelButton", 1 ),
			Assets.getFrame( "cancelButton", 2 ) );

		resume_btn.height = 50;
		resume_btn.width = 100;
		resume_btn.x = background.x + background.width - resume_btn.width - 40;
		resume_btn.y = background.y + 50;

		resume_btn.setClickListener( new ClickListener() {
			public void click( Actor actor, float x, float y ) {
				Assets.playSound( "button", false );
				close();
			}
		});

		this.addActor( resume_btn );
	}

	public void show( Stage stage ) {
		stage.addActor( this );
	}

	public void close() {
		if( resume_panel != null )
			resume_panel.visible = true;

		stage.removeActor( this );
	}
}
