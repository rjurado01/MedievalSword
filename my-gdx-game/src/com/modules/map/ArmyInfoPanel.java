package com.modules.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.modules.battle.SquareBoard;
import com.mygdxgame.Assets;
import com.mygdxgame.Constants;

public class ArmyInfoPanel extends Group {
	final int SIZE_H = 200;
	final int SIZE_W = 380;
	
	Image alpha;
	Image background;
	
	Button exit_btn;
	
	public ArmyInfoPanel( Stage stage ) {
		
		this.stage = stage;
		this.width = SIZE_W;
		this.height = SIZE_H;		
		
		createAlphaImage();
		createBackgroundImage();
		//createUnitsPanels();
		createExitButton();
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
	
	public void createExitButton() {
		exit_btn = new Button( Assets.getFrame( "exit", 1 ), Assets.getFrame( "exit", 2 ) );
		exit_btn.height = 25;
		exit_btn.width = 80;
		exit_btn.x = Constants.SIZE_W - 175;;
		exit_btn.y = 10;
		
		exit_btn.setClickListener( new ClickListener() 
		{	
			public void click(Actor actor, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		this.addActor( exit_btn );
	}
}
