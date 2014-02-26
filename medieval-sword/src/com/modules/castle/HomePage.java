package com.modules.castle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Army;
import com.game.Assets;
import com.game.Stack;
import com.modules.battle.UnitIcon;

/**
 * Show the castle army and the hero army if there is an hero in the castle.
 * It allows the player pass units of castle army to hero army.
 */
public class HomePage extends Group {

	final int N_UNITS = 5;
	final int SPACE_W = UnitIcon.SIZE_W - 2; // space between two UnitIcons
	final int HOME_X = 185;
	final int ROW2_Y = 60;
	final int ROW1_Y = 220;
	final int ALL_BUTTON_X = 705;
	final int ALL_BUTTON_Y = 175;

	Army castle_army;
	Army hero_army;

	ArmyUnitIcon[] castle_army_icons;	// units that are inside of castle army
	ArmyUnitIcon[] hero_army_icons;  	// units that are inside of hero army

	Image castle_icon;
	Image hero_icon;
	Image pass_all_disabled;
	Button pass_all;

	int color;	// owner color (player)

	public HomePage( Army castle_army, Army hero_army, int color ) {
		this.castle_army = castle_army;
		this.hero_army = hero_army;
		this.color = color;

		castle_army_icons = new ArmyUnitIcon[5];
		hero_army_icons = new ArmyUnitIcon[5];

		loadCastleArmy( castle_army );
		loadHeroArmy( hero_army );
		loadButtons();
		loadIcons();
	}

	private void loadCastleArmy( Army army ) {
		for( int i = 0; i < N_UNITS; i++ ) {
			this.castle_army_icons[i] = new ArmyUnitIcon( HOME_X + SPACE_W * i, ROW1_Y );
			addActor( this.castle_army_icons[i] );
		}

		updateArmy( army, castle_army_icons );
	}

	private void loadHeroArmy( Army army ) {
		for( int i = 0; i < N_UNITS; i++ ) {
			this.hero_army_icons[i] = new ArmyUnitIcon( HOME_X + SPACE_W * i, ROW2_Y );
			addActor( this.hero_army_icons[i] );
		}

		if( army != null )
			updateArmy( army, hero_army_icons );
	}

	/**
	 * Updates the army panel info with the new units that have been bought
	 * @param army castle army
	 */
	public void updateArmy( Army army, ArmyUnitIcon[] representation ) {
		for( int i = 0; i < army.getStacks().size(); i++ ) {
			if( representation[i].empty() )
				representation[i].addUnit( army.getStacks().get(i).getUnit(), color );

			representation[i].setNumber( army.getStacks().get(i).getNumber() );
			representation[i].showNumberLabel();
		}
	}

	public void setHeroArmy( Army army ) {
		hero_army = army;
		updateArmy( army, hero_army_icons );
	}

	private void loadButtons() {
		if( hero_army != null ) {
			pass_all = new Button(
					Assets.getFrame("btnPassAll", 1),
					Assets.getFrame("btnPassAll", 2) );

			pass_all.width = CastlePanel.BUTTONS_SIZE;
			pass_all.height = CastlePanel.BUTTONS_SIZE;
			pass_all.x = ALL_BUTTON_X;
			pass_all.y = ALL_BUTTON_Y;

			pass_all.setClickListener( new ClickListener() {
				public void click(Actor actor, float x, float y) {
					if( castle_army.getStacks().size() > 0 ) {
						Assets.playSound( "pass_all", false );
						passAllUnits();
					}
				}
			});

			addActor( pass_all );
		} else {
			pass_all_disabled = new Image( Assets.getFrame("btnPassAll" ,3) );
			pass_all_disabled.width = CastlePanel.BUTTONS_SIZE;
			pass_all_disabled.height = CastlePanel.BUTTONS_SIZE;
			pass_all_disabled.x = ALL_BUTTON_X;
			pass_all_disabled.y = ALL_BUTTON_Y;
			addActor( pass_all_disabled );
		}
	}

	private void loadIcons() {
		castle_icon = new Image( Assets.getTextureRegion("iconCastle") );

		castle_icon.x = ALL_BUTTON_X;
		castle_icon.y = ALL_BUTTON_Y + 100;
		castle_icon.width = CastlePanel.BUTTONS_SIZE;
		castle_icon.height = CastlePanel.BUTTONS_SIZE;

		addActor( castle_icon );

		hero_icon = new Image( Assets.getTextureRegion("iconHero") );

		hero_icon.x = ALL_BUTTON_X;
		hero_icon.y = ALL_BUTTON_Y - 100;
		hero_icon.width = CastlePanel.BUTTONS_SIZE;
		hero_icon.height = CastlePanel.BUTTONS_SIZE;

		addActor( hero_icon );
	}

	/**
	 * Pass all castle units to hero army
	 */
	public void passAllUnits() {
		int last = -1;

		List<Stack> remove_stacks = new ArrayList<Stack>();
		int count = 0;

		if( hero_army != null ) {
			for( Stack castle_stack : castle_army.getStacks() ) {
				for( Stack hero_stack : hero_army.getStacks() ) {
					if( castle_stack.getUnit() == hero_stack.getUnit() ) {
						hero_stack.addUnits( castle_stack.getNumber() );
						remove_stacks.add( castle_stack );
						last = -1;
						break;
					}
					else
						last++;
				}

				if( last > 0 && last < 4 ) {
					hero_army.addStack( castle_stack );
					remove_stacks.add( castle_stack );
				}

				castle_army_icons[count].removeUnit();
				count++;
			}

			for( Stack stack : remove_stacks )
				castle_army.deleteStack(stack);

			updateArmy( castle_army, castle_army_icons );
			updateArmy( hero_army, hero_army_icons );
		}
	}

	/**
	 * Reload castle army
	 * @param castle_army new castle army
	 */
	public void updateCastleArmy( Army castle_army ) {
		for( int i = 0; i < 5; i++ ) {
			castle_army_icons[i].removeUnit();
		}

		updateArmy( castle_army, castle_army_icons );
	}
}
