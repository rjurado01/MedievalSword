package com.me.modules.battle;

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
import com.me.mygdxgame.Unit;

/**
 * Show information about battle when it finish
 */
public class BattleSummary extends Group {
	
	public static final int VICTORY = 0;
	public static final int DEFEAT = 1;
	
	static final int SIZE_H = 200;
	static final int SIZE_W = 380;
	
	final int RESULT_W = 100;
	final int RESULT_H = 170;
	
	final int N_CONTENTS = 5;
	
	float LOST_X 	= 30;
	float LOST_Y 	= SIZE_H - BattleSummaryUnit.SIZE_H - 30;
	
	float DESTROYED_X  = 30;
	float DESTROYED_Y  = 25;
	
	Image background;
	
	Button exit_btn;
	
	Label lost_label;
	Label destroyed_label;
	Label result_label;
	Label experience_label;
	
	Image result_img;
	Image experience_img;
	
	Array<BattleSummaryUnit> lost;
	Array<BattleSummaryUnit> destroyed;
	
	int result = -1;
	
	/**
	 * Class constructor
	 * @param stage
	 */
	public BattleSummary( Stage stage ) {
		
		this.width = SIZE_W;
		this.height = SIZE_H;
		
		this.x = ( BattleScreen.SIZE_W - this.width ) / 2;
		this.y = ( ( BattleScreen.SIZE_H - this.height - SquareBoard.SIZE_H ) / 2 ) +
				SquareBoard.SIZE_H;
		
		this.stage = stage;
		
		background = new Image( Assets.getTextureRegion( "menu" ) );
		background.height = SIZE_H;
		background.width = SIZE_W;
		
		this.addActor( background );
		
		loadUnitsPanels();
		
		// this.visible = false;
	}
	
	/**
	 * Load panels with lost and destroyed units info
	 */
	public void loadUnitsPanels() {
		
		lost = new Array<BattleSummaryUnit>();
		destroyed = new Array<BattleSummaryUnit>();
		
		for( int i = 0; i <  N_CONTENTS; i++ ) {
			lost.add( new BattleSummaryUnit( 
					LOST_X + BattleSummaryUnit.SIZE_W * i - ( i * 2 ), LOST_Y ) );
			
			destroyed.add( new BattleSummaryUnit( 
					DESTROYED_X + BattleSummaryUnit.SIZE_W * i - ( i * 2 ), DESTROYED_Y ) );
			
			this.addActor( lost.get( i ) );
			this.addActor( destroyed.get( i ) );
		}
		
		loadTopText();
	}
	
	/**
	 * Load left panel with info about battle result and experience
	 */
	public void loadResultPanel() {
		result_img = new Image( Assets.getTextureRegion( "content" ) );
		result_img.height = RESULT_H - 20;
		result_img.width = RESULT_W;
		result_img.x = SIZE_W - 130;
		result_img.y = 20;
		
		if( result == VICTORY )
			result_label = new Label( "Victory !!", Assets.font2 );
		else
			result_label = new Label( "Defeat !!", Assets.font2 );
		
		result_label.x = SIZE_W - 110;
		result_label.y = LOST_Y + 30;
		
		experience_label = new Label( "Experience:\n\n---- / ----", Assets.skin );
		experience_label.x = SIZE_W - 110;
		experience_label.y = LOST_Y - 20;
		
		this.addActor( result_img );
		this.addActor( result_label );
		this.addActor( experience_label );
		
		loadExitBtn();
	}
	
	/**
	 * Load exit button
	 */
	public void loadExitBtn() {
		exit_btn = new Button( Assets.getFrame( "exit", 1 ), Assets.getFrame( "exit", 2 ) );
		exit_btn.height = 25;
		exit_btn.width = 80;
		exit_btn.x = SIZE_W - exit_btn.width - 40;
		exit_btn.y = DESTROYED_Y + 5;
		
		exit_btn.setClickListener( new ClickListener() 
		{	
			public void click(Actor actor, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		this.addActor( exit_btn );
	}
	
	/**
	 * Load text with information
	 */
	public void loadTopText() {
		lost_label = new Label( "Creatures Lost", Assets.skin );
		lost_label.x = LOST_X;
		lost_label.y = LOST_Y + BattleSummaryUnit.SIZE_H;
		
		destroyed_label = new Label( "Creatures Destroyed", Assets.skin );
		destroyed_label.x = DESTROYED_X;
		destroyed_label.y = DESTROYED_Y + BattleSummaryUnit.SIZE_H;		
		
		this.addActor( lost_label );
		this.addActor( destroyed_label );
	}
	
	public void setUnits( Array<Unit> u_lost, Array<Unit> u_destroyed ) {
		for( int i = 0; i < u_lost.size; i++ )
			u_lost.get( i ).setSummaryUnit( lost.get( i ) );
		
		for( int i = 0; i < u_destroyed.size; i++ )
			u_destroyed.get( i ).setSummaryUnit( destroyed.get( i ) );
	}
	
	/**
	 * Show summary with final result of battle
	 * 
	 * @param result
	 */
	public void show( int result ) {
		this.result = result;
		
		for( int i = 0; i < N_CONTENTS; i ++ ) {
			lost.get( i ).addNumberLabel();
			destroyed.get( i ).addNumberLabel();
		}
		
		loadResultPanel();
		
		BattleController.stage.addActor( this );
	}
	
	/**
	 * Add and remove menu from window
	 * @param visible
	 */
	public void setVisible( boolean visible ) {
		if( visible ) {
			for( int i = 0; i < N_CONTENTS; i ++ ) {
				lost.get( i ).addNumberLabel();
				destroyed.get( i ).addNumberLabel();
			}
			
			BattleController.stage.addActor( this );
		}
		else {
			BattleController.stage.removeActor( this );
			BattleController.mutex = false;
		}
	}
}
