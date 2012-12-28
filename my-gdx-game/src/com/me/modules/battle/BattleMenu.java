package com.me.modules.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.mygdxgame.Assets;

/**
 * Represent options menu in battle screen.
 * It show when user touch options button.
 */
public class BattleMenu extends Group {
	
	static final int SIZE_H = 224;
	static final int SIZE_W = 300;
	
	Image alpha;
	Image background;
	
	Button exit_btn;
	Button resume_btn;
	
	/**
	 * Class constructor
	 * @param stage
	 */
	public BattleMenu( Stage stage ) {
		
		alpha = new Image( Assets.getFlipTextureRegion( "greyBackground" ) );
		alpha.height = BattleScreen.SIZE_H;
		alpha.width = BattleScreen.SIZE_W;
		alpha.color.a = 0.8f;
		
		background = new Image( Assets.getTextureRegion( "menu" ) );
		background.height = SIZE_H;
		background.width = SIZE_W;
		background.x = ( BattleScreen.SIZE_W - SIZE_W ) / 2;
		background.y = ( BattleScreen.SIZE_H - SIZE_H ) / 2;
		
		exit_btn = new Button( Assets.getFrame( "exit", 1 ), Assets.getFrame( "exit", 2 ) );
		exit_btn.height = 40;
		exit_btn.width = 120;
		exit_btn.x = background.x + ( ( SIZE_W - exit_btn.width ) / 2 );
		exit_btn.y = background.y + 50;
		
		exit_btn.setClickListener( new ClickListener() 
		{	
			public void click(Actor actor, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		resume_btn = new Button( Assets.getFrame( "resume", 1 ), Assets.getFrame( "resume", 2 ) );
		resume_btn.height = 40;
		resume_btn.width = 120;
		resume_btn.x = background.x + ( ( SIZE_W - exit_btn.width ) / 2 );;
		resume_btn.y = 160;
		
		resume_btn.setClickListener( new ClickListener() 
		{	
			public void click(Actor actor, float x, float y) {
				setVisible( false );
			}
		});

		
		this.width = SIZE_W;
		this.height = SIZE_H;
		
		this.addActor( alpha );
		this.addActor( background );
		this.addActor( exit_btn );
		this.addActor( resume_btn );
		
		this.x = 0;
		this.y = 0;
		
		this.stage = stage;
	}

	/**
	 * Add and remove menu from window
	 * @param visible
	 */
	public void setVisible( boolean visible ) {
		if( visible ) {
			BattleController.stage.addActor( this );
		}
		else {
			BattleController.stage.removeActor( this );
			BattleController.mutex = false;
		}
	}
}
