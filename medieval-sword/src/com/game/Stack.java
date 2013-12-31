package com.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.modules.battle.UnitIcon;
import com.modules.battle.SquareBoard;
import com.modules.battle.StackView;
import com.utils.CallBack;
import com.utils.CustomAnimation;

/**
 * Stack represents a set of units of the same type.
 */
public class Stack {

	Unit unit;
	UnitIcon summary;
	SquareBoard square;
	StackView view;

	int number_units;
	int number_units_dead;
	int battle_side;
	int color;
	int first_life; 	// life of first unit of stack
	int orientation;

	/* Attributes modifier */
	int defense = 0;
	int attack = 0;
	int range = 0;
	int mobility = 0;
	int damage = 0;

	protected ArrayList<CustomAnimation> actions_queue;

	public Stack( Unit unit, int number, int color ) {
		this.unit = unit;
		this.number_units = number;
		this.color = color;

		actions_queue = new ArrayList<CustomAnimation>();
		first_life = unit.getLife();
		view = new StackView( 40f, 40f, number );
	}

	public void receiveDamage(int damage) {
		if( damage >= ( first_life + unit.getDefense() ) ) 
			killUnits( damage );
		else if ( damage > unit.getDefense() )
			first_life -= ( damage - unit.getDefense() );
	}

	public boolean isDead() {
		if( number_units > 0 )
			return false;
		else
			return true;
	}

	private void killUnits( int damage ) {
		int deaths = 1;	// dead first unit
		damage -= ( first_life + unit.getDefense() );

		// Dead units while damage > unit life + shield
		while( damage >  ( unit.getLife() + unit.getDefense() ) ) {
			damage -= ( unit.getLife() + unit.getDefense() );
			deaths++;
		}

		updateLastUnitWoundLife( damage );
		updateNumberWithDeaths( deaths );

		if( number_units > 0 )	
			view.updateTextNumber( number_units );
	}

	private void updateLastUnitWoundLife( int damage ) {
		if( damage > unit.getDefense() )
			first_life = unit.getLife() - ( damage - unit.getDefense() );
		else
			first_life = unit.getLife();
	}

	private void updateNumberWithDeaths( int deaths ) {
		if( deaths >= number_units ) {
			summary.addDeaths( number_units );
			number_units = 0;
		}
		else {
			number_units -= deaths;
			summary.addDeaths( deaths );
		}
	}

	public void updateActions( float time ) {
		if( actions_queue.size() > 0 ) {
			updateCurrentAction( time );
			updateActionFrame();
		}
	}

	private void updateCurrentAction( float time ) {
		if( actions_queue.get( 0 ).isFinished() )
			actions_queue.remove(0);
		else
			actions_queue.get( 0 ).increaseTime( time );
	}

	private void updateActionFrame() {
		if( actions_queue.size() > 0 )
			view.setFrame( actions_queue.get(0).getCurrentFrame() );
		else
			setFrameSide();
	}

	public void setSummary( UnitIcon summary_stack ) {
		this.summary = summary_stack;
		this.summary.addIcon( unit.getTexture( "icon" + color ) );
	}

	public SquareBoard getSquare() {
		return square;
	}

	public void setSquare( SquareBoard sq ) {
		square = sq;
	}

	public int getAttackDamage() {
		return number_units * unit.getDamage();
	}

	public int getNumber() {
		return number_units;
	}

	public void setNumber( int number ) {
		this.number_units = number;
	}
	
	public void setBattleSide( int side ) {
		this.battle_side = side;
	}

	public int getRange() {
		return unit.getRange() + range;
	}

	public int getMovility() {
		return unit.getMobility() + mobility;
	}

	/**
	 * Add attack action to actions queue (implemented in unit type class)
	 * @param orientation action orientation
	 * @param callback function to execute when action will finish
	 */
	public void addAttackAction( int orientation, CallBack callback ) {
		if( orientation == Constants.UNDEFINED )
			unit.attackAction( this, this.orientation, callback );
		else
			unit.attackAction( this, orientation, callback );
	}

	/**
	 * Add walk action to actions queue (implemented in unit type class)
	 */
	public void addWalkAction() {
		unit.walkAction( this, orientation );
	}

	public StackView getView() {
		return view;
	}

	public void addAction( CustomAnimation action ) {
		actions_queue.add( action );
	}

	public int getBattleSide() {
		return battle_side;
	}

	public int getColor() {
		return color;
	}

	public void setOrientation( int orientation ) {
		this.orientation = orientation;
	}

	public void setOrientationByFocus( Vector2 focus ) {
		if( focus.x - view.getPosition().x < 0)
			setOrientation( Constants.LEFT );
		else if( focus.x - view.getPosition().x > 0)
			setOrientation( Constants.RIGHT );
	}

	public void setFrameSide() {
		orientation = battle_side == Constants.LEFT_SIDE ? Constants.RIGHT : Constants.LEFT;
		view.setFrame( unit.getTexture( "normal" + orientation + color ) );
	}

	public TextureRegion getIcon() {
		return unit.getTexture( "icon" + color );
	}

	public Unit getUnit() {
		return unit;
	}

	public void addUnits( int amount ) {
		number_units += amount;
	}
}
