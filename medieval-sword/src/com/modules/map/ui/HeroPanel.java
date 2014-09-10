package com.modules.map.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.game.Army;
import com.game.Assets;
import com.modules.battle.UnitIcon;
import com.modules.map.heroes.HeroTop;
import com.utils.Vector2i;

/**
 * Show information about an hero and its army.
 * Show the number of units that compounds the army an the type of unit.
 * Show the hero properties ( attack, defense and mobility ).
 */
public class HeroPanel extends ArmyInfoPanel {
  final static int SIZE_H = 500;
  final static int SIZE_W = 606;

  final int ROW_1_Y = 310;
  final int ROW_2_Y = 140;

  Array<UnitIcon> units;
  Array<Image> hero_properties;

  public HeroPanel( HeroTop hero ) {
    super( new Vector2i(SIZE_W, SIZE_H) );

    createUnitsPanels( hero.getArmy() );
    createHeroInfo( hero );
    createExitButton();
  }

  private void createUnitsPanels( Army army ) {
    units = new Array<UnitIcon>();

    for( int i = 0; i < 5; i++ ) {
      UnitIcon summary = new UnitIcon( 96 * i + 60, background.y + ROW_2_Y );

      if( i < army.getStacks().size() ) {
        summary.addIcon( army.getStacks().get( i ).getIcon() );
        summary.setNumber( army.getStacks().get(i).getNumber()  );
        summary.showNumberLabel();
      }

      addActor( summary );
    }
  }

  private void createHeroInfo( HeroTop hero ) {
    createIcon( hero.getIconTextureRegion(), new Vector2i( 50, 68 ) );
    createHeroProperties(hero);
    createAttributes( hero.getAttack(), hero.getDefense(), hero.getPower() );
  }

  private void createHeroProperties( HeroTop hero ) {
    ResourceIndicator name_label = new ResourceIndicator( 225, ROW_1_Y + 94 );
    name_label.setIcon( Assets.getTextureRegion( "iconHero2" ) );
    name_label.updateText( hero.getName() );

    ResourceIndicator level_label = new ResourceIndicator( 225, ROW_1_Y + 47 );
    level_label.setIcon( Assets.getTextureRegion( "iconLevel" ) );
    level_label.updateText( hero.getLevel() );

    ResourceIndicator exp_label = new ResourceIndicator( 225, ROW_1_Y );
    exp_label.setIcon( Assets.getTextureRegion( "iconExperience" ) );
    exp_label.updateText("----");

    addActor( name_label );
    addActor( level_label );
    addActor( exp_label );
  }

  private void createAttributes( int attack, int defense, int power ) {
    ResourceIndicator attack_indicator = new ResourceIndicator( 400, ROW_1_Y + 94 );
    attack_indicator.setIcon( Assets.getTextureRegion( "iconDamage" ) );
    attack_indicator.updateText( attack );

    ResourceIndicator defense_indicator = new ResourceIndicator( 400, ROW_1_Y + 47 );
    defense_indicator.setIcon( Assets.getTextureRegion( "iconDefense" ) );
    defense_indicator.updateText( attack );

    ResourceIndicator mobility_indicator = new ResourceIndicator( 400, ROW_1_Y );
    mobility_indicator.setIcon( Assets.getTextureRegion( "iconMobility2" ) );
    mobility_indicator.updateText( attack );

    addActor( attack_indicator );
    addActor( defense_indicator );
    addActor( mobility_indicator );
  }
}
