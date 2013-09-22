package com.castles;

import com.game.Assets;
import com.game.Constants;
import com.modules.castle.Castle;
import com.races.humands.castle.Archery;
import com.races.humands.castle.Barracks;
import com.races.humands.castle.Farm;
import com.races.humands.castle.Fort;
import com.races.humands.castle.Monastery;
import com.races.humands.castle.Stables;
import com.races.humands.castle.Tavern;
import com.races.humands.castle.TownHall;
import com.utils.Vector2i;

public class Castle1 extends Castle {

	public Castle1() {
		buildings.add( new TownHall() );
		buildings.add( new Tavern() );
		buildings.add( new Fort() );
		buildings.add( new Farm() );
		buildings.add( new Barracks() );
		buildings.add( new Archery() );
		buildings.add( new Stables() );
		buildings.add( new Monastery() );

		units.add( Assets.getUnit( Constants.VILLAGER ) );
		units.add( Assets.getUnit( Constants.ARCHER ) );

		texture = "castle1";
		size = new Vector2i( 200, 200 );
		square_use_number = new Vector2i( 1, -1 );
	}

}
