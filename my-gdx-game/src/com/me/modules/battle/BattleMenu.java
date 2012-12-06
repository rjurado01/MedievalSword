package com.me.modules.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.mygdxgame.Assets;

public class BattleMenu extends Group {
	
	static final int SIZE_H = 224;
	static final int SIZE_W = 300;
	
	Image background;
	
	Button exit_btn;
	Button resume_btn;
	
	public BattleMenu( Stage stage ) {
		background = new Image( Assets.getTextureRegion( "menu" ) );
		background.height = SIZE_H;
		background.width = SIZE_W;
		
		exit_btn = new Button( Assets.getFrame( "exit", 1 ), Assets.getFrame( "exit", 2 ) );
		exit_btn.height = 40;
		exit_btn.width = 120;
		exit_btn.x = 90;
		exit_btn.y = 60;
		
		exit_btn.setClickListener( new ClickListener() 
		{	
			public void click(Actor actor, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		resume_btn = new Button( Assets.getFrame( "resume", 1 ), Assets.getFrame( "resume", 2 ) );
		resume_btn.height = 40;
		resume_btn.width = 120;
		resume_btn.x = 90;
		resume_btn.y = 130;
		
		resume_btn.setClickListener( new ClickListener() 
		{	
			public void click(Actor actor, float x, float y) {
				setVisible( false );
			}
		});

		
		this.width = SIZE_W;
		this.height = SIZE_H;
		
		this.addActor( background );
		this.addActor( exit_btn );
		this.addActor( resume_btn );
		
		this.x = ( BattleScreen.SIZE_W - this.width ) / 2;
		this.y = ( BattleScreen.SIZE_H - this.height ) / 2;
		
		this.visible = false;
		
		stage.addActor( this );
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
