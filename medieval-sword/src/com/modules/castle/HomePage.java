package com.modules.castle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.game.Army;
import com.game.Assets;
import com.game.Stack;

public class HomePage extends Group {

	final int N_UNITS = 5;
	final int BX_SIZE = 50; // space between two buildings squares of castle units

	Army castle_army;
	Army hero_army;

	ArmyUnitIcon[] castle_army_icons;	 // units that are inside of castle army
	ArmyUnitIcon[] hero_army_icons;  // units that are inside of hero army

	Button pass_all;

	public HomePage( Army castle_army, Army hero_army ) {
		this.castle_army = castle_army;
		this.hero_army = hero_army;

		castle_army_icons = new ArmyUnitIcon[5];
		hero_army_icons = new ArmyUnitIcon[5];

		loadCastleArmy( castle_army );
		loadHeroArmy( hero_army );
		loadButtons();
	}

	private void loadCastleArmy( Army army ) {
		for( int i = 0; i < N_UNITS; i++ ) {
			this.castle_army_icons[i] = new ArmyUnitIcon( 75 + 38 * i, 100 );
			addActor( this.castle_army_icons[i] );
		}

		updateArmy( army, castle_army_icons );
	}

	private void loadHeroArmy( Army army ) {
		for( int i = 0; i < N_UNITS; i++ ) {
			this.hero_army_icons[i] = new ArmyUnitIcon( 75 + 38 * i, 30 );
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
			if( representation[i].empty() ) {
				representation[i].addUnit( army.getStacks().get(i).getUnit() );
				representation[i].updateNumber( army.getStacks().get(i).getNumber() );
			}
			else
				representation[i].updateNumber( army.getStacks().get(i).getNumber() );
		}
	}

	public void setHeroArmy( Army army ) {
		hero_army = army;
		updateArmy( army, hero_army_icons );
	}


	private void loadButtons() {
		pass_all = new Button(
				Assets.getTextureRegion("stats"),
				Assets.getTextureRegion("number") );

		pass_all.x = 280;
		pass_all.y = 70;
		pass_all.width = 30;
		pass_all.height = 50;

		pass_all.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) {
				passAllUnits();
			}
		});

		addActor( pass_all );
	}

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

	public void updateCastleArmy( Army army ) {
		for( int i = 0; i < 5; i++ ) {
			castle_army_icons[i].removeUnit();
		}

		updateArmy( army, castle_army_icons );
	}
}
