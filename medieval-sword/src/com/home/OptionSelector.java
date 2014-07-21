package com.home;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;
import com.utils.Vector2i;

public class OptionSelector extends Group {

	Label language_text;
	public Button left_btn;
	public Button right_btn;
	
	String values [];
	int selected;
	
	public OptionSelector( Vector2i position, String name, String [] values, int selected ) {
		this.values = values.clone();
		this.selected = selected;
		loadOption( position, name );
	}
	
	public void loadOption( Vector2i position, String name ) {
		Image title_back = new Image( Assets.getTextureRegion("rectBlack") );
		title_back.x = position.x;
		title_back.y = position.y;
		
		Label title = new Label( name + ":", Assets.font2 );
		title.x = title_back.x + 50;
		title.y = title_back.y + 5;
		
		left_btn = new Button(
			Assets.getFrame( "arrowLeft", 1 ),
			Assets.getFrame( "arrowLeft", 2 ) );
		
		right_btn = new Button(
				Assets.getFrame( "arrowRight", 1 ),
				Assets.getFrame( "arrowRight", 2 ));
		
		left_btn.width = 25;
		left_btn.height = 25;
		left_btn.x = position.x + 210;
		left_btn.y = position.y + 4;
		
		language_text = new Label( values[selected], Assets.skin );
		language_text.x = left_btn.x + 80;
		language_text.y = position.y + 6;
		
		if( values.length == 1 ) {
			language_text.setColor(1, 1, 1, 0.5f);
			left_btn.color.a = 0.5f;
			right_btn.color.a = 0.5f;
		}
		
		right_btn.width = 25;
		right_btn.height = 25;
		right_btn.x = left_btn.x + 185;
		right_btn.y = position.y + 4;
		
		left_btn.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				selected = ( selected + values.length - 1 ) % values.length;
				language_text.setText( values[ selected ] );
				language_text.x = left_btn.x + 80;
			}
		});
		
		right_btn.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				selected = ( selected + 1 ) % values.length;
				language_text.setText( values[ selected ] );
				language_text.x = left_btn.x + 80;
			}
		});
		
		addActor( title_back );
		addActor( title );
		addActor( left_btn );
		addActor( right_btn );
		addActor( language_text );
	}
}
