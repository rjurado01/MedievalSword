package com.modules.map.ui;

import com.game.Assets;
import com.modules.map.heroes.CreaturesGroup;
import com.utils.Vector2i;

/**
 * Show information about a creatures group.
 * Show the number of units that compounds the army an the type of unit.
 */
public class CreaturesGroupPanel extends ArmyInfoPanel {

	final static int SIZE_W = 395;
	final static int SIZE_H = 340;

	public CreaturesGroupPanel( CreaturesGroup group ) {
		super( new Vector2i( SIZE_W, SIZE_H ) );

		createGroupInfo( group );
		createExitButton();
	}

	private void createGroupInfo( CreaturesGroup group ) {
		createIcon( group.getIconTextureRegion(), new Vector2i( 70, 70 ) );

		ResourceIndicator name_label = new ResourceIndicator( 190, 200 );
		name_label.setIcon( Assets.getTextureRegion( "iconHero2" ) );
		name_label.updateText( group.getUnitName() );

		ResourceIndicator amount_label = new ResourceIndicator( 190, 150 );
		amount_label.setIcon( Assets.getTextureRegion( "iconUnits" ) );
		amount_label.updateText( group.getAmount() );

		addActor( name_label );
		addActor( amount_label );
	}
}
