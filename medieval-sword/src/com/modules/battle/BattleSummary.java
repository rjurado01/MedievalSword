package com.modules.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.game.Assets;
import com.game.Constants;
import com.game.MyGdxGame;
import com.game.Stack;

/**
 * Show information about battle when it finish
 */
public class BattleSummary extends Group {

	public static final int VICTORY = 0;
	public static final int DEFEAT = 1;

	final int SIZE_H = 620;
	final int SIZE_W = 650;

	final int RESULT_W = 100;
	final int RESULT_H = 170;

	final int N_CONTENTS = 5;

	float panel_x;
	float panel_y;
	float col1_x;
	float col2_x;
	float lost_y;
	float destroyed_y;

	Image alpha;
	Image background;
	Image result_img;
	Image experience_img;

	Label lost_label;
	Label destroyed_label;
	Label result_label;
	Label experience_label;

	String lost_units_msg [] = { "Creatures lost", "Criaturas perdidas" };
	String destroyed_units_msg [] = { "Creatures destroyed", "Criaturas eliminadas" };

	//
	Button exit_btn;

	Array<UnitIcon> lost;
	Array<UnitIcon> destroyed;

	MyGdxGame game;

	int result = -1;

	Map<String, String> victory_msg;
	Map<String, String> defeat_msg;

	public BattleSummary( MyGdxGame game, Stage stage ) {
		this.game = game;
		this.stage = stage;
		this.width = SIZE_W;
		this.height = SIZE_H;
		this.x = 0;
		this.y = 0;

		loadTranslates();
		calculatePositions();
		createAlphaImage();
		createBackgroundImage();
		createUnitsPanels();
	}

	private void loadTranslates() {
		victory_msg = new HashMap<String, String>();
		victory_msg.put("es", "Victoria !!");
		victory_msg.put("en", "Victory !!");

	    defeat_msg = new HashMap<String, String>();
	    defeat_msg.put("es", "Derrorta");
	    defeat_msg.put("en", "Defeat");
	}

	private void calculatePositions() {
		panel_x = ( Constants.SIZE_W - SIZE_W ) / 2;
		panel_y = ( Constants.SIZE_H - SIZE_H ) / 2;

		col1_x = panel_x + 80;
		col2_x = col1_x + N_CONTENTS * UnitIcon.SIZE_W + 40;
		lost_y = panel_y + SIZE_H / 2 + 25;
		destroyed_y = panel_y + SIZE_H / 2 - Constants.FONT1_HEIGHT - UnitIcon.SIZE_H - 15;
	}

	private void createAlphaImage() {
		alpha = new Image( Assets.getTextureRegion( "disabledBackground" ) );
		alpha.height = Constants.SIZE_H;
		alpha.width = Constants.SIZE_W;

		alpha.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {}
		});

		this.addActor( alpha );
	}

	private void createBackgroundImage() {
		background = new Image( Assets.getTextureRegion( "backgroundArmy" ) );
		background.height = SIZE_H;
		background.width = SIZE_W;
		background.x = panel_x;
		background.y = panel_y;

		this.addActor( background );
	}

	public void createUnitsPanels() {

		lost = new Array<UnitIcon>();
		destroyed = new Array<UnitIcon>();

		for( int i = 0; i <  N_CONTENTS; i++ ) {
			lost.add( new UnitIcon(
					col1_x + UnitIcon.SIZE_W * i - ( i * 2 ),
					lost_y ) );

			destroyed.add( new UnitIcon(
					col1_x + UnitIcon.SIZE_W * i - ( i * 2 ),
					destroyed_y ) );

			this.addActor( lost.get( i ) );
			this.addActor( destroyed.get( i ) );
		}

		createTopText();
	}

	public void createResultPanel() {
		createResultImage();
		createResultLabel();
		// createExperienceLabel();
		createExitButton();
	}

	private void createResultImage() {
		result_img = new Image( Assets.getTextureRegion( "content" ) );
		result_img.height = RESULT_H - 20;
		result_img.width = RESULT_W;
		result_img.x = panel_x + background.width - RESULT_W - 35;
		result_img.y = destroyed_y;

		this.addActor( result_img );
	}

	private void createResultLabel() {
		Image result_background;

		if( result == VICTORY ) {
			result_background = new Image( Assets.getFrame("botBuild", 2 ) );
			result_label = new Label( victory_msg.get( Constants.LANGUAGE ), Assets.font2 );
		}
		else {
			result_background = new Image( Assets.getFrame("botBuild", 3 ) );
			result_label = new Label( defeat_msg.get( Constants.LANGUAGE ), Assets.font2 );
		}

		result_background.width = 140;
		result_background.height = 32;
		result_background.x = panel_x + ( SIZE_W - result_background.width ) / 2;
		result_background.y = panel_y + SIZE_H - 90;

		result_label.x = panel_x + ( SIZE_W - result_label.width ) / 2;
		result_label.y = result_background.y +
			( result_background.height - result_label.height ) / 2;

		addActor( result_background );
		addActor( result_label );
	}

	/*private void createExperienceLabel() {
		experience_label = new Label( "Experience:\n\n---- / ----", Assets.skin );
		experience_label.x = col2_x;
		experience_label.y = lost_y - 20;

		this.addActor( experience_label );
	}*/

	public void createExitButton() {
		exit_btn = new Button(
			Assets.getFrame( "btnExitLarge", 1 ),
			Assets.getFrame( "btnExitLarge", 2 ) );

		exit_btn.height = 64;
		exit_btn.width = 128;
		exit_btn.x = panel_x + ( SIZE_W - exit_btn.width ) / 2;
		exit_btn.y = panel_y + 55;

		exit_btn.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				Assets.playSound("button", false);
				game.returnToMapScreen();
			}
		});

		this.addActor( exit_btn );
	}

	public void createTopText() {
		createLostLabel();
		createDestroyedLabel();

		this.addActor( lost_label );
		this.addActor( destroyed_label );
	}

	public void createLostLabel() {
		lost_label = new Label( lost_units_msg[Constants.LANGUAGE_CODE], Assets.skin );
		lost_label.x = panel_x + ( SIZE_W - lost_label.width ) / 2;
		lost_label.y = lost_y + UnitIcon.SIZE_H + 5;
	}

	public void createDestroyedLabel() {
		destroyed_label = new Label( destroyed_units_msg[Constants.LANGUAGE_CODE], Assets.skin );
		destroyed_label.x = panel_x + ( SIZE_W - destroyed_label.width ) / 2;
		destroyed_label.y = destroyed_y + UnitIcon.SIZE_H + 5;
	}

	public void setSummaryStacks( ArrayList<Stack> u_lost, ArrayList<Stack> u_destroyed ) {
		for( int i = 0; i < u_lost.size(); i++ ) {
			u_lost.get( i ).setSummary( lost.get( i ) );
			lost.get(i).showNumberLabel();
		}

		for( int i = 0; i < u_destroyed.size(); i++ ) {
			u_destroyed.get( i ).setSummary( destroyed.get( i ) );
			destroyed.get(i).showNumberLabel();
		}
	}

	public void show( int result ) {
		this.result = result;
		Assets.setMusicVolume( Constants.MUSIC_BATTLE, 0.4f );

		if( result == VICTORY )
			Assets.playSound( "battle_win", false );
		else
			Assets.playSound( "battle_lost", false );

		for( int i = 0; i < N_CONTENTS; i ++ ) {
			lost.get( i ).createNumberLabel();
			destroyed.get( i ).createNumberLabel();
		}

		createResultPanel();
		stage.addActor( this );
	}
}
