package com.modules.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Constants;

/**
 * Represent options menu in battle screen.
 * It is show when user touch options button.
 */
public class BattleMenu extends Group {
	
	static final int SIZE_H = 224;
	static final int SIZE_W = 300;
	
	Image alpha;
	Image background;
	
	Button exit_btn;
	Button resume_btn;	

	public BattleMenu() {
		
		createAlphaImage();
		createBackgroundImage();
		createExitButton();		
		createResumeButton();
		
		this.width = SIZE_W;
		this.height = SIZE_H;
		this.x = 0;
		this.y = 0;		
		
		this.addActor( alpha );
		this.addActor( background );
		this.addActor( exit_btn );
		this.addActor( resume_btn );
	}
	
	private void createAlphaImage() {
		alpha = new Image( Assets.getTextureRegion( "greyBackground" ) );
		alpha.height = Constants.SIZE_H;
		alpha.width = Constants.SIZE_W;
		alpha.color.a = 0.5f;
	}
	
	private void createBackgroundImage() {
		background = new Image( Assets.getTextureRegion( "menu" ) );
		background.height = SIZE_H;
		background.width = SIZE_W;
		background.x = ( Constants.SIZE_W - SIZE_W ) / 2;
		background.y = ( Constants.SIZE_H - SIZE_H ) / 2;
	}
	
	private void createExitButton() {
		exit_btn = new Button( Assets.getFrame( "exit", 1 ), Assets.getFrame( "exit", 2 ) );
		exit_btn.height = 40;
		exit_btn.width = 120;
		exit_btn.x = background.x + ( ( SIZE_W - exit_btn.width ) / 2 );
		exit_btn.y = background.y + 50;
		
		exit_btn.setClickListener( new ClickListener() {	
			public void click(Actor actor, float x, float y) { Gdx.app.exit(); }
		});
	}
	
	private void createResumeButton() {
		resume_btn = new Button( Assets.getFrame( "resume", 1 ), Assets.getFrame( "resume", 2 ) );
		resume_btn.height = 40;
		resume_btn.width = 120;
		resume_btn.x = background.x + ( ( SIZE_W - exit_btn.width ) / 2 );;
		resume_btn.y = 160;
		
		resume_btn.setClickListener( new ClickListener() {	
			public void click(Actor actor, float x, float y) { close(); }
		});
	}

	public void show( Stage stage ) {
		stage.addActor( this );
	}
	
	public void close() {
		stage.removeActor( this );
		BattleController.mutex = false;
	}

}
