package com.modules.castle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Army;
import com.game.Assets;
import com.game.Player;
import com.game.Stack;
import com.game.Unit;
import com.modules.map.MapConstants;
import com.modules.map.MapController;
import com.utils.Vector2i;

/**
 * Top representation of castle.
 * Includes the castle type and the Image representation.
 */
public class TopCastle {

	Castle castle;
	Player owner;
	Army army;

	Image image;
	Vector2i square_position;

	List<TopCastleBuilding> buildings;
	List<TopCastleUnit> units;

	boolean built; 	// show whether this turn the player has constructed a building

	public TopCastle( Castle castle, Vector2i position, Player owner ) {
		this.castle = castle;
		this.square_position = position;
		this.owner = owner;
		this.army = new Army();
		this.built = false;

		loadImage();
		loadBuildings();
		loadUnits();
	}

	private void loadImage() {
		image = new Image( Assets.getTextureRegion( castle.texture_name ));
		image.width = castle.size.x;
		image.height = castle.size.y;
		image.x = MapConstants.SQUARE_TERRAIN_W *
				square_position.x + castle.position_correction.x;
		image.y = MapConstants.SQUARE_TERRAIN_H *
				square_position.y + castle.position_correction.y;

		image.setClickListener( new ClickListener() {
			public void click(Actor actor, float x, float y) { clicked(); }
		});
	}

	private void clicked() {
		MapController.addEvent( MapConstants.CASTLE, this );
	}

	private void loadBuildings() {
		buildings = new ArrayList<TopCastleBuilding>();

		for( CastleBuilding building : castle.buildings )
			this.buildings.add( new TopCastleBuilding( this, building ) );
	}

	private void loadUnits() {
		units = new ArrayList<TopCastleUnit>();

		for( Unit unit : castle.units )
			this.units.add( new TopCastleUnit( this, unit ) );
	}

	public Image getImage() {
		return image;
	}

	public String getIconTextureName() {
		return castle.texture_name;
	}

	public Vector2i getUseSquareNumber() {
		Vector2i square_number = null;

		if( square_position != null && castle.square_use_number != null )
			square_number = new Vector2i(
					square_position.x + castle.square_use_number.x,
					square_position.y + castle.square_use_number.y );

		return square_number;
	}

	public TextureRegion getIconTextureRegion() {
		return Assets.getTextureRegion( castle.icon_name );
	}

	public Player getOwner() {
		return owner;
	}

	/**
	 * Add bought units to castle army
	 *
	 * @param unit type of unit
	 * @param amount number of units of this type
	 */
	public void addUnitToArmy( Unit unit, int amount ) {
		// if the army of castle is empty create new and add it
		if( army.getStacks().size() == 0 ) {
			army.addStack( new Stack(unit, amount, owner.color) );
		}
		else {
			boolean new_stack = true;

			// check if there is an army of this unit type
			for( Stack stack : army.getStacks() )
				if( stack.getUnit().getName() == unit.getName() ) {
					new_stack = false;
					stack.addUnits( amount );
					break;
				}

			// if there is not any army of this unit type
			// and there is a hole create new stack
			if( new_stack && army.getStacks().size() < 5  )
				army.addStack( new Stack(unit, amount, owner.color) );
		}
	}

	public Army getArmy() {
		return army;
	}

	public void setNumberUnits( int position, int number ) {
		if( number > 0 )
			units.get( position ).setNumberUnits(number);
	}

	public void addNumberUnits( int position, int number ) {
		units.get( position ).addUnits( number );
	}

	public void enableUnit( int unit_id ) {
		for( TopCastleUnit unit : units )
			if( unit.getUnitId() == unit_id )
				unit.available = true;
	}

	public void initializeBuilding( int position, int level ) {
		buildings.get( position ).initialize( level);
	}

	public void setBuilt() {
		built = true;
	}

	public boolean isBuilt() {
		return built;
	}

	public void passTurn( int day ) {
		built = false;

		for( TopCastleBuilding building : buildings )
			building.passDay( day );
	}
}
