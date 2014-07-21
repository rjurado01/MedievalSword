package com.modules.map.ui;

import com.game.Assets;
import com.modules.castle.TopCastle;
import com.utils.Vector2i;

public class CastleInfoPanel extends ArmyInfoPanel {
	final static int SIZE_W = 415;
	final static int SIZE_H = 340;

	public CastleInfoPanel( TopCastle castle ) {
		super( new Vector2i( SIZE_W, SIZE_H ) );

		createCastleInfo( castle );
		createExitButton();
	}

	private void createCastleInfo( TopCastle castle ) {
		createIcon( castle.getLargeIconTextureRegion(), new Vector2i( 90, 90 ) );

		ResourceIndicator name_label = new ResourceIndicator( 210, 200 );
		name_label.setIcon( Assets.getTextureRegion( "iconCastleMini" ) );
		name_label.updateText( castle.getName() );

		ResourceIndicator amount_label = new ResourceIndicator( 210, 150 );
		amount_label.setIcon( Assets.getTextureRegion( "iconUnits" ) );
		amount_label.updateText( castle.getArmy().getAmount() );

		addActor( name_label );
		addActor( amount_label );
	}
}
