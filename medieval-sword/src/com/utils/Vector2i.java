package com.utils;

/**
 * Vector2 class of integers
 */
public class Vector2i {
	public int x;
	public int y;

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "[" + x + ":" + y + "]";
	}

	public boolean equal( Vector2i number ) {
		return number.x == x && number.y == y;
	}
}
