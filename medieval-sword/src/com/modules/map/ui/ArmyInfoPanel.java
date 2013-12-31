package com.modules.map.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Assets;
import com.game.Constants;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.utils.Vector2i;

/**
 * Show information about an army.
 * It is used by CreaturesGroupPanel and HeroPanel.
 */
public class ArmyInfoPanel extends Group {

	Image background;
	Button exit_btn;
	Group icon;

	public ArmyInfoPanel( Vector2i size ) {
		this.width = size.x;
		this.height = size.y;
		this.x = ( Constants.SIZE_W - width ) / 2 + MapConstants.HUD_WIDTH / 2;
		this.y = ( Constants.SIZE_H - height ) / 2;

		createBackgroundImage();
	}

	protected void createBackgroundImage() {
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

	protected void createIcon( TextureRegion texture_icon, Vector2i size ) {
		icon = new Group();

		Image icon_back = new Image( Assets.getTextureRegion("rect") );
		icon_back.width = 132;
		icon_back.height = 132;
		icon.addActor( icon_back );

		Image image = new Image( texture_icon );
		image.width = size.x;
		image.height = size.y;
		image.x = ( icon_back.width - image.width ) / 2;
		image.y = ( icon_back.height - image.height ) / 2;;
		icon.addActor( image );

		icon.x = 60;
		icon.y = height - 190;

		addActor( icon );
	}

	protected void createExitButton() {
		exit_btn = new Button(
				Assets.getFrame( "btnExitLarge", 1 ),
				Assets.getFrame( "btnExitLarge", 2 ) );

		exit_btn.width = 128;
		exit_btn.height = 64;
		exit_btn.x = width / 2 - exit_btn.width / 2;
		exit_btn.y = 50;

		exit_btn.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				removePanel();
			}
		});

		this.addActor( exit_btn );
	}

	public void removePanel() {
		this.remove();
		MapController.addEvent( MapConstants.CLOSE_PANEL, this );
	}
}