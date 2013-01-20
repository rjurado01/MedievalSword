package com.me.modules.battle;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.Assets;
import com.me.mygdxgame.Constants;
import com.me.mygdxgame.Stack;

/**
 * Show information about battle when it finish
 */
public class BattleSummary extends Group {
	
	public static final int VICTORY = 0;
	public static final int DEFEAT = 1;
	
	final int SIZE_H = 200;
	final int SIZE_W = 380;
	
	final int RESULT_W = 100;
	final int RESULT_H = 170;
	
	final int N_CONTENTS = 5;
	
	float LOST_X = 80;
	float LOST_Y = 190;
	
	float DESTROYED_X = 80;
	float DESTROYED_Y = 105;
	
	Image alpha;
	Image background;
	Image result_img;
	Image experience_img;
	
	Label lost_label;
	Label destroyed_label;
	Label result_label;
	Label experience_label;
	
	Button exit_btn;
	
	Array<BattleSummaryStack> lost;
	Array<BattleSummaryStack> destroyed;
	
	int result = -1;
	
	public BattleSummary( Stage stage ) {
		
		this.stage = stage;
		this.width = SIZE_W;
		this.height = SIZE_H;		
		
		createAlphaImage();
		createBackgroundImage();
		createUnitsPanels();
	}
	
	private void createAlphaImage() {
		alpha = new Image( Assets.getTextureRegion( "greyBackground" ) );
		alpha.height = Constants.SIZE_H;
		alpha.width = Constants.SIZE_W;
		alpha.color.a = 0.5f;
		
		this.addActor( alpha );
	}
	
	private void createBackgroundImage() {
		background = new Image( Assets.getTextureRegion( "menu" ) );
		background.height = SIZE_H;
		background.width = SIZE_W;
		background.x = ( Constants.SIZE_W - this.width ) / 2;
		background.y = ( ( Constants.SIZE_H - this.height - SquareBoard.SIZE_H ) / 2 ) +
				SquareBoard.SIZE_H;
		
		this.addActor( background );
	}
	
	public void createUnitsPanels() {
		
		lost = new Array<BattleSummaryStack>();
		destroyed = new Array<BattleSummaryStack>();
		
		for( int i = 0; i <  N_CONTENTS; i++ ) {
			lost.add( new BattleSummaryStack( 
					LOST_X + BattleSummaryStack.SIZE_W * i - ( i * 2 ), LOST_Y ) );
			
			destroyed.add( new BattleSummaryStack( 
					DESTROYED_X + BattleSummaryStack.SIZE_W * i - ( i * 2 ), DESTROYED_Y ) );
			
			this.addActor( lost.get( i ) );
			this.addActor( destroyed.get( i ) );
		}
		
		createTopText();
	}
	
	public void createResultPanel() {
		createResultImage();
		createResultLabel();
		createExperienceLabel();
		
		this.addActor( result_img );
		this.addActor( result_label );
		this.addActor( experience_label );
		
		createExitButton();
	}
	
	private void createResultImage() {
		result_img = new Image( Assets.getTextureRegion( "content" ) );
		result_img.height = RESULT_H - 20;
		result_img.width = RESULT_W;
		result_img.x = background.x + background.width - RESULT_W - 35;
		result_img.y = DESTROYED_Y;
	}
	
	private void createResultLabel() {
		if( result == VICTORY )
			result_label = new Label( "Victory !!", Assets.font2 );
		else
			result_label = new Label( "Defeat !!", Assets.font2 );
		
		result_label.x = Constants.SIZE_W - 165;
		result_label.y = LOST_Y + 30;
	}
	
	private void createExperienceLabel() {
		experience_label = new Label( "Experience:\n\n---- / ----", Assets.skin );
		experience_label.x = Constants.SIZE_W - 165;;
		experience_label.y = LOST_Y - 20;
	}
	
	public void createExitButton() {
		exit_btn = new Button( Assets.getFrame( "exit", 1 ), Assets.getFrame( "exit", 2 ) );
		exit_btn.height = 25;
		exit_btn.width = 80;
		exit_btn.x = Constants.SIZE_W - 175;;
		exit_btn.y = DESTROYED_Y + 10;
		
		exit_btn.setClickListener( new ClickListener() 
		{	
			public void click(Actor actor, float x, float y) {
				Gdx.app.exit();
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
		lost_label = new Label( "Creatures Lost", Assets.skin );
		lost_label.x = LOST_X;
		lost_label.y = LOST_Y + BattleSummaryStack.SIZE_H;
	}
	
	public void createDestroyedLabel() {
		destroyed_label = new Label( "Creatures Destroyed", Assets.skin );
		destroyed_label.x = DESTROYED_X;
		destroyed_label.y = DESTROYED_Y + BattleSummaryStack.SIZE_H;
	}
	
	public void setSummaryStacks( ArrayList<Stack> u_lost, ArrayList<Stack> u_destroyed ) {
		for( int i = 0; i < u_lost.size(); i++ )
			u_lost.get( i ).setSummary( lost.get( i ) );
		
		for( int i = 0; i < u_destroyed.size(); i++ )
			u_destroyed.get( i ).setSummary( destroyed.get( i ) );
	}
	
	public void show( int result ) {
		this.result = result;
		
		for( int i = 0; i < N_CONTENTS; i ++ ) {
			lost.get( i ).createNumberLabel();
			destroyed.get( i ).createNumberLabel();
		}
		
		createResultPanel();
		
		stage.addActor( this );
	}
}
