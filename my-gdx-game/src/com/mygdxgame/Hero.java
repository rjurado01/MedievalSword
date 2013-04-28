package com.mygdxgame;

/**
 * Each army of player should be associated to an Hero for move it by the map.
 * In the battle, hero enhance units skills and let player to throw spells.
 * Hero can up level that let player enhance his attributes and learn new spells.
 */
public class Hero {

	String name;
	
	int level;
	
	/* Attributes */
	int mana;
	int attack;
	int defense;
	int power;
	int movility;
	
	Army army;
	
	public Hero() {
		army = new Army();
		army.setHero( this ); 
	}
	
	public Army getArmy() {
		return army;
	}
	
}
