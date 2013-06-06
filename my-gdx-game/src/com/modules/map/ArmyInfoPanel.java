package com.modules.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.modules.battle.BattleSummaryStack;
import com.mygdxgame.Army;
import com.mygdxgame.Assets;
import com.mygdxgame.Constants;

public class ArmyInfoPanel extends Group {
	final int SIZE_H = 180;
	final int SIZE_W = 280;
	
	Image alpha;
	Image background;
	
	Button exit_btn;
	
	Array<BattleSummaryStack> units;
	Array<Image> hero_properties; 
	
	Group icon;
	
	public ArmyInfoPanel( HeroTop hero, Vector2 position ) {
		
		this.width = SIZE_W;
		this.height = SIZE_H;		
		this.x = position.x;
		this.y = position.y;
		
		createAlphaImage();
		createBackgroundImage();
		createUnitsPanels( hero.getArmy() );
		createHeroInfo( hero );
		//createExitButton();
	}

	public ArmyInfoPanel( CreaturesGroup group, Vector2 position ) {
		this.width = SIZE_W;
		this.height = SIZE_H;		
		this.x = position.x;
		this.y = position.y;
		
		createAlphaImage();
		createBackgroundImage();
		createUnitsPanels( group.getArmy() );
		createGroupInfo( group );
	}

	private void createAlphaImage() {
		alpha = new Image( Assets.getTextureRegion( "greyBackground" ) );
		alpha.height = Constants.SIZE_H;
		alpha.width = Constants.SIZE_W;
		alpha.color.a = 0.5f;
		
		
		alpha.setClickListener( new ClickListener() {	
			public void click(Actor actor, float x, float y) {
				removePanel();
				MapController.status = MapController.NORMAL;
			}
		});
		
		this.addActor( alpha );
	}
	
	private void createBackgroundImage() {
		background = new Image( Assets.getTextureRegion( "menu" ) );
		background.height = SIZE_H;
		background.width = SIZE_W;
		background.x = 50;
		background.y = ( Constants.SIZE_H - this.height ) / 2;
		
		background.setClickListener( new ClickListener() {	
			public void click(Actor actor, float x, float y) {}
		});
		
		this.addActor( background );
	}
	
	public void createExitButton() {
		exit_btn = new Button( Assets.getFrame( "exit", 1 ), Assets.getFrame( "exit", 2 ) );
		exit_btn.height = 20;
		exit_btn.width = 60;
		exit_btn.x = Constants.SIZE_W - 220;
		exit_btn.y = background.y - 30;
		
		exit_btn.setClickListener( new ClickListener() 
		{	
			public void click(Actor actor, float x, float y) {
				removePanel();
			}
		});
		
		this.addActor( exit_btn );
	}
	
	private void createUnitsPanels( Army army ) {
		units = new Array<BattleSummaryStack>();
		
		for( int i = 0; i < 5; i++ ) {
			BattleSummaryStack summary = new BattleSummaryStack( 45 * i + 80, background.y + 20 );
			
			if( i < army.getStacks().size() ) {
				summary.addIcon( army.getStacks().get( i ).getIcon() );
				summary.addDeaths( army.getStacks().get(i).getNumber()  );
				summary.createNumberLabel();
			}
			
			addActor( summary );
		}
		
	}
	
	public void removePanel() {
		this.remove();
	}
	
	private void createHeroInfo( HeroTop hero ) {
		createIcon( hero.getIconTextureRegion() );
		createHeroProperties(hero);
		createAttributes( hero.getAttack(), hero.getDefense(), hero.getPower() );
	}
	
	private void createHeroProperties( HeroTop hero ) {
		Image image1 = new Image( Assets.getTextureRegion("number") );
		image1.width = 70;
		image1.height = 15;
		image1.x = 150;
		image1.y = 210;
		
		Image image2 = new Image( Assets.getTextureRegion("number") );
		image2.width = 70;
		image2.height = 15;
		image2.x = 150;
		image2.y = 190;
		
		Image image3 = new Image( Assets.getTextureRegion("number") );
		image3.width = 70;
		image3.height = 15;
		image3.x = 230;
		image3.y = 190;
		
		addActor( image1 );
		addActor( image2 );
		addActor( image3 );

		
		Label name;
		name = new Label( "Name", Assets.skin );
		name.x = 170;
		name.y = 210;
		
		Label level;
		level = new Label( "Level: " + Integer.toString( hero.getLevel() ), Assets.skin );
		level.x = 170;
		level.y = 190;
		
		Label exp;
		exp = new Label( "0/1000", Assets.skin );
		exp.x = 250;
		exp.y = 190;
		
		addActor( name );
		addActor( level );
		addActor( exp );
	}
	
	private void createIcon( TextureRegion texture_icon ) {
		icon = new Group();
		
		Image icon_back = new Image( Assets.getTextureRegion("number") );
		icon_back.width = 55;
		icon_back.height = 55;	
		icon.addActor( icon_back );
		
		Image image = new Image( texture_icon );
		image.width = 35;
		image.height = 35;
		image.x = 10;
		image.y = 10;
		icon.addActor( image );
		
		icon.x = 80;
		icon.y = 170;	
		
		addActor( icon );
	}
	
	private void createAttributes( int attack, int defense, int power ) {
		addProperty( Assets.getTextureRegion( "attackIcon" ), 
				Integer.toString( attack ),
				new Vector2( 150, 170 ) );
		
		addProperty( Assets.getTextureRegion( "defenseIcon" ), 
				Integer.toString( defense ),
				new Vector2( 198, 170 ) );
		
		addProperty( Assets.getTextureRegion( "powerIcon" ), 
				Integer.toString( power ),
				new Vector2( 245, 170 ) );
	}
	
	private void addProperty( TextureRegion icon, String value, Vector2 position ) {
		Image background = new Image( Assets.getTextureRegion("number") );
		background.width = 55;
		background.height = 15;
		background.x = position.x;
		background.y = position.y;
		
		Image image = new Image( icon );
		image.width = 10;
		image.height = 10;
		image.x = position.x + 15;
		image.y = position.y + 2.5f;
		
		Label info;
		info = new Label( value, Assets.skin );
		info.x = position.x + 30;
		info.y = position.y;
		
		addActor( background );
		addActor( image );
		addActor( info );
	}
	
	private void createGroupInfo( CreaturesGroup group ) {
		createIcon( group.getIconTextureRegion() );
		
		Image image = new Image( Assets.getTextureRegion("number") );
		image.width = 70;
		image.height = 15;
		image.x = 150;
		image.y = 190;
		
		Label name;
		name = new Label( "Name", Assets.skin );
		name.x = 170;
		name.y = 190;
		
		addActor( image );
		addActor( name );
		
		createAttributes( 1, 1, 0 );
	}
}
