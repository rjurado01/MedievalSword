package com.modules.map.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;
import com.game.Constants;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.modules.map.objetives.LevelObjectives;

public class ObjectivesPanel extends Group {

	final static int SIZE_W = 440;
	final static int SIZE_H = 150;
	final static int OBJECTIVE_H = 40;
	final int BUTTON_SIZE = 74;

	Image background;
	Image music_on, music_off;

	Label title_label;
	Image check_objectives [];
	Button exit_btn;

	String objectives_msg [] = { "OBJECTIVES", "OBJETIVOS" };
	String victory_msg [] = { "VICTORY !!", "VICTORIA !!" };
	String defeat_msg [] = { "DEFEAT", "DERROTA" };

	public ObjectivesPanel( LevelObjectives level_objectives ) {
		width = SIZE_W;
		height = SIZE_H + level_objectives.getNumber() * OBJECTIVE_H;
		check_objectives = new Image[ level_objectives.getNumber() ];

		this.x = ( Constants.SIZE_W - width ) / 2 + MapConstants.HUD_WIDTH / 2;
		this.y = ( Constants.SIZE_H - height ) / 2;

		createBackgroundImage();
		createTitle();
		createList( level_objectives );
		createReturnButton();
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
		title_label = new Label( objectives_msg[ Constants.LANGUAGE_CODE ], Assets.skin );
		title_label.x = background.x + ( SIZE_W - title_label.width ) / 2;
		title_label.y = background.y + height - 70;

		addActor( title_label );
	}

	private void createList( LevelObjectives level_objectives ) {
		for( int i=0; i < level_objectives.getNumber(); i++ ) {
			if( level_objectives.isCompleted(i) )
				check_objectives[i] = new Image( Assets.getFrame( "btnCheck", 2 ) );
			else
				check_objectives[i] = new Image( Assets.getFrame( "btnCheck", 1 ) );

			check_objectives[i].width = 20;
			check_objectives[i].height = 20;
			check_objectives[i].x = background.x + 80;
			check_objectives[i].y = background.y + height - 130 - OBJECTIVE_H * i;

			Label objetive_label = new Label(
				level_objectives.getDescription(i).getString( Constants.LANGUAGE ),
				Assets.skin );

			objetive_label.x = check_objectives[i].x + 30;
			objetive_label.y = check_objectives[i].y - 4;

			addActor( check_objectives[i] );
			addActor( objetive_label );
		}
	}

	private void createReturnButton() {
		exit_btn = new Button(
			Assets.getFrame( "btnReturn", 1 ),
			Assets.getFrame( "btnReturn", 2 ) );

		exit_btn.x = background.x + width - BUTTON_SIZE - 70;
		exit_btn.y = background.y + 50;
		exit_btn.width = BUTTON_SIZE;
		exit_btn.height = BUTTON_SIZE;

		exit_btn.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				Assets.playSound("button", false);
				removePanel();
			}
		});

		addActor( exit_btn );
	}

	public void removePanel() {
		this.remove();
		MapController.addEvent( MapConstants.CLOSE_PANEL, this );
	}

	public void completeObjective( int n_objective ) {
		check_objectives[n_objective].setRegion( Assets.getFrame( "btnCheck", 2 ) );
	}

	public void setEndGame( boolean win ) {
		Image result_background;
		removeActor( title_label );

		if( win) {
			result_background = new Image( Assets.getFrame("botBuild", 2 ) );
			title_label.setText( victory_msg[Constants.LANGUAGE_CODE] );
		}
		else {
			result_background = new Image( Assets.getFrame("botBuild", 3 ) );
			title_label.setText( defeat_msg[Constants.LANGUAGE_CODE] );
		}

		result_background.width = 140;
		result_background.height = 32;
		result_background.x = ( SIZE_W - result_background.width ) / 2;
		result_background.y = title_label.y - 2;

		title_label.x = ( SIZE_W - title_label.getText().length() * Constants.FONT1_WIDTH ) / 2;
		addActor( result_background );
		addActor( title_label );

		setExitButton();
	}

	private void setExitButton() {
		removeActor( exit_btn );

		exit_btn = new Button(
				Assets.getFrame( "btnExit", 1 ),
				Assets.getFrame( "btnExit", 2 ) );

		exit_btn.x = background.x + width - BUTTON_SIZE - 70;
		exit_btn.y = background.y + 50;
		exit_btn.width = BUTTON_SIZE;
		exit_btn.height = BUTTON_SIZE;

		exit_btn.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				Assets.playSound("button", false);
				MapController.addEvent( MapConstants.EXIT_GAME, null );
			}
		});

		addActor( exit_btn );
	}

	public void completeAllObjectives() {
		for( int i=0; i<check_objectives.length; i++ )
			completeObjective(i);
	}
}
