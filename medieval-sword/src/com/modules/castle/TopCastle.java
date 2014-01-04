package com.modules.castle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.game.Army;
import com.game.Assets;
import com.game.Constants;
import com.game.Player;
import com.game.Stack;
import com.game.Unit;
import com.modules.map.terrain.Structure;
import com.utils.Vector2i;

/**
 * Top representation of castle.
 * Includes the castle type and the Image representation.
 */
public class TopCastle {

	Castle castle;
	Player owner;
	Army army;

	TopCastleView castle_view;

	List<TopCastleBuilding> buildings;
	List<TopCastleUnit> units;

	boolean built; 	// show whether this turn the player has constructed a building
	float production_percent; // the percent that units are produced in a week (1 == 100%)


	public TopCastle( Castle castle, Vector2i square_number, Player owner ) {
		this.castle = castle;
		this.owner = owner;
		this.army = new Army();
		this.built = false;
		this.production_percent = 1;
		this.castle_view = new TopCastleView( this, square_number );

		loadBuildings();
		loadUnits();
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

	public String getIconTextureName() {
		return castle.texture_name;
	}

	public Vector2i getUseSquareNumber() {
		return castle_view.getUseSquareNumber();
	}

	public TextureRegion getIconTextureRegion() {
		return Assets.getTextureRegion( castle.icon_name );
	}

	public TextureRegion getLargeIconTextureRegion() {
		return Assets.getTextureRegion( castle.icon_name + "Large" );
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
			if( owner == null )
				army.addStack( new Stack(unit, amount, Constants.GREY ) );
			else
				army.addStack( new Stack(unit, amount, owner.color ) );
		}
		else {
			boolean new_stack = true;

			// check if there is an army of this unit type
			for( Stack stack : army.getStacks() )
				if( stack.getUnit().systemName() == unit.systemName() ) {
					new_stack = false;
					stack.addUnits( amount );
					break;
				}

			// if there is not any army of this unit type
			// and there is a hole create new stack
			if( new_stack && army.getStacks().size() < 5  ) {
				if( owner == null )
					army.addStack( new Stack(unit, amount, Constants.GREY ) );
				else
					army.addStack( new Stack(unit, amount, owner.color ) );
			}
		}
	}

	public Army getArmy() {
		return army;
	}

	public void setNumberUnits( int position, int number ) {
		if( number > 0 )
			units.get( position ).setNumberUnits(number);
	}

	public void addNumberUnits( int id, int number ) {
		for( TopCastleUnit unit : units )
			if( unit.getUnitId() == id ) {
				unit.addUnits( number );
				return;
			}
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

	public float getProductionPercent() {
		return production_percent;
	}

	public String getName() {
		return castle.name;
	}

	public Actor getView() {
		return castle_view.getActor();
	}

	public Structure getStructure() {
		return castle_view;
	}

	/**
	 * Called when player captured it
	 * @param player the player who captured it
	 */
	public void use( Player player ) {
		owner = player;
		castle_view.showFlag( player.color );
	}

	public void setProductionPercent( float percent ) {
		production_percent = percent;
	}
}
