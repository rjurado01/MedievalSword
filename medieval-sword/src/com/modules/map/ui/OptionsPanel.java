package com.modules.map.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;
import com.game.Constants;
import com.modules.battle.BattleExitAlert;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.utils.Vector2i;

public class OptionsPanel extends Group {

	final static int SIZE_W = 440;
	final static int SIZE_H = 210;
	final int BUTTON_SIZE = 74;

	Actor panel_actor = this;
	Image background;
	Image music_on, music_off;

	String options_msg [] = { "OPTIONS", "OPCIONES" };

	public OptionsPanel() {
		width = SIZE_W;
		height = SIZE_H;

		this.x = ( Constants.SIZE_W - width ) / 2 + MapConstants.HUD_WIDTH / 2;
		this.y = ( Constants.SIZE_H - height ) / 2;

		createBackgroundImage();
		createTitle();
		createMusicOption();
		createSaveOption();
		createReturnOption();
		createExitOption();
	}

	private void createBackgroundImage() {
		background = new Image( Assets.getTextureRegion( "backgroundArmy" ) );
		background.width = width;
		background.height = height;
		background.x = 0;
		background.y = 0;

		background.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {}
		});

		this.addActor( background );
	}

	private void createTitle() {
		Label title_label = new Label( options_msg[ Constants.LANGUAGE_CODE ], Assets.skin );
		title_label.x = background.x + ( SIZE_W - title_label.width ) / 2;
		title_label.y = background.y + SIZE_H - 70;

		addActor( title_label );
	}

	private void createMusicOption() {
		music_on = new Image( Assets.getFrame( "btnMusic", 1 ) );
		music_on.x = background.x + 40;
		music_on.y = background.y + 40;
		music_on.width = BUTTON_SIZE;
		music_on.height = BUTTON_SIZE;

		music_off = new Image( Assets.getFrame( "btnMusic", 2 ) );
		music_off.x = background.x + 40;
		music_off.y = background.y + 40;
		music_off.width = BUTTON_SIZE;
		music_off.height = BUTTON_SIZE;

		music_on.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				Assets.playSound("button", false);
				Assets.pauseMusic( MapConstants.MUSIC_MAP );
				Constants.MUSIC_ON = false;
				removeActor( music_on );
				addActor( music_off );
			}
		});

		music_off.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				Assets.playSound("button", false);
				Constants.MUSIC_ON = true;
				Assets.playMusic( MapConstants.MUSIC_MAP );
				removeActor( music_off );
				addActor( music_on );
			}
		});

		addActor( music_on );
	}

	private void createSaveOption() {
		Button save_btn = new Button(
			Assets.getFrame( "btnSave", 1 ),
			Assets.getFrame( "btnSave", 2 ) );

		save_btn.x = background.x + 40 + ( BUTTON_SIZE + 20 ) * 1;
		save_btn.y = background.y + 40;
		save_btn.width = BUTTON_SIZE;
		save_btn.height = BUTTON_SIZE;

		addActor( save_btn );
	}

	private void createReturnOption() {
		Button return_btn = new Button(
			Assets.getFrame( "btnReturn", 1 ),
			Assets.getFrame( "btnReturn", 2 ) );

		return_btn.x = background.x + 40 + ( BUTTON_SIZE + 20 ) * 3;
		return_btn.y = background.y + 40;
		return_btn.width = BUTTON_SIZE;
		return_btn.height = BUTTON_SIZE;

		return_btn.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				Assets.playSound("button", false);
				removePanel();
			}
		});

		addActor( return_btn );
	}

	private void createExitOption() {
		Button exit_btn = new Button(
			Assets.getFrame( "btnExit", 1 ),
			Assets.getFrame( "btnExit", 2 ) );

		exit_btn.x = background.x + 40 + ( BUTTON_SIZE + 20 ) * 2;
		exit_btn.y = background.y + 40;
		exit_btn.width = BUTTON_SIZE;
		exit_btn.height = BUTTON_SIZE;

		exit_btn.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				Assets.playSound("button", false);
				BattleExitAlert alert = new BattleExitAlert(
					new Vector2i( Constants.SIZE_W - Constants.HUD_WIDTH, Constants.SIZE_H),
					new Vector2i( Constants.HUD_WIDTH, 0 ), panel_actor );
				alert.show( stage );

				panel_actor.visible = false;
			}
		});

		addActor( exit_btn );
	}

	public void removePanel() {
		this.remove();
		MapController.addEvent( MapConstants.CLOSE_PANEL, this );
	}
}
