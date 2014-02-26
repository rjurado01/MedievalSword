package com.modules.castle;

import java.util.ArrayList;
import java.util.List;

import com.game.Player;
import com.game.Unit;
import com.utils.Vector2i;

/**
 * Save information about a castle (buildings, units, texture...)
 * and its status in the game (owner, square...)
 */
public class Castle {
	private Player owner;

	protected List<CastleBuilding> buildings;
	protected List<Unit> units;

	public String name;
	protected String texture_name;
	protected String icon_name;
	protected Vector2i size;
	protected Vector2i square_use_number;
	protected Vector2i flag_position;
	protected int vision;
	public Vector2i position_correction;

	public Castle() {
		buildings = new ArrayList<CastleBuilding>();
		units = new ArrayList<Unit>();
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
}
