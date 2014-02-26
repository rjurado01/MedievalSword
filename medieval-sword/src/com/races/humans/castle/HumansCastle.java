package com.races.humans.castle;

import com.game.Assets;
import com.game.Constants;
import com.modules.castle.Castle;
import com.races.humans.castle.Archery;
import com.races.humans.castle.Barracks;
import com.races.humans.castle.Farm;
import com.races.humans.castle.Fort;
import com.races.humans.castle.Monastery;
import com.races.humans.castle.Stables;
import com.races.humans.castle.Tavern;
import com.races.humans.castle.TownHall;
import com.utils.Vector2i;

public class HumansCastle extends Castle {

	public HumansCastle() {
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
		units.add( Assets.getUnit( Constants.CRUSADER ));
		units.add( Assets.getUnit( Constants.WIZARD ));
		units.add( Assets.getUnit( Constants.KNIGHT ));

		texture_name = "humans-castle";
		icon_name = "humans-castleIcon";
		size = new Vector2i( 496, 436 );
		square_use_number = new Vector2i( 2, -1 );
		position_correction = new Vector2i( -1, -2 );
		flag_position = new Vector2i( 5, 300 );
		name = "Clifton";
		vision = 8;
	}
}
