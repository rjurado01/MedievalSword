package com.modules.castle;

import com.game.Unit;

/**
 * Info about an unit of one castle.
 * The units of a castle represent the units that player can buy in this castle.
 * Units are blocked until the player build the building of this unit.
 */
public class TopCastleUnit {

	Unit unit;
	TopCastle castle;

	// number of units available for buy
	int number_units;

	// show if player can buy this unit
	boolean available;


	TopCastleUnit( TopCastle castle, Unit unit ) {
		this.castle = castle;
		this.unit = unit;

		number_units = 0;
		available = false;
	}

	public void setNumberUnits( int number ) {
		this.number_units = number;
	}

	public int getNumberUnits() {
		return number_units;
	}

	public boolean isAvailable() {
		return available;
	}

	/**
	 * @return unit position number for show it in castle panel
	 */
	public int getPositionNumber() {
		return unit.getPositionNumber();
	}

	/**
	 * Check if player can buy a certain number of this units
	 * @param amount number of units that player want to buy
	 */
	public boolean canBuy( int amount ) {
		int price = amount * unit.getPrice();
		return number_units >= amount && castle.getOwner().gold >= price;
	}

	/**
	 * Buy 'x' number of units and add it to castle army
	 * @param amount number of units to buy
	 */
	public void buy( int amount ) {
		if( canBuy( amount ) ) {
			castle.getOwner().gold -= amount * unit.getPrice();
			number_units -= amount;
			castle.addUnitToArmy(unit, amount);
		}
	}

	public void buyAll() {
		int amount = castle.getOwner().gold / unit.getPrice();

		if( amount >= number_units )
			buy( number_units );
		else
			buy( amount );
	}

	public void addUnits( int number ) {
		number_units += number;
	}

	public int getUnitId() {
		return unit.getId();
	}

	public String getEnableDescription( String language ) {
		return unit.getEnableDescription( language );
	}

	public int getAvailableTotalPrice() {
		if( number_units == 0 || unit.getPrice() == 0 )
			return 0;

		int amount = castle.getOwner().gold / unit.getPrice();

		if( amount < number_units )
			return amount * unit.getPrice();
		else
			return number_units * unit.getPrice();
	}

	public Unit getUnit() {
		return unit;
	}

	public boolean anyToBuy() {
		return available && number_units > 0;
	}
}
