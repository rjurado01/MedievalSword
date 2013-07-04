package com.modules.map.terrain;


import com.utils.Vector2i;

public class PathItem {
	public int F, G, H;
	public boolean visited = false;
	public PathItem father;
	public Vector2i number;
	public int status;
	
	public PathItem( Vector2i number, int status ) {
		G = 0;
		F = 0;
		H = 0;
		
		father = null;
		visited = false;
		
		this.status = status;
		this.number = number;
	}
	
	public void calculateH( Vector2i number_square ) {
		H = ( Math.abs( number_square.x - number.x ) +
				Math.abs( number_square.y - number.y ) ) * 10;
	}
	
	public void calculateF() {
		F = H + G;
	}
	
	public boolean isFree() {
		return status == 0;
	}
 }
