package com.modules.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdxgame.Assets;
import com.mygdxgame.Constants;
import com.utils.Vector2i;

public class MiniMap extends Group {
	
	public static final int GRASS = 0;
	public static final int WHATER = 1;
	public static final int ROAD = 2;
	public static final int BLUE = 3;
	public static final int RED = 4;
	public static final int GREY = 5;
	
	final float WIDTH = 75.2f;
	final float HEIGHT = 80;
	
	Terrain terrain;
	Image mini_map [][];
	
	float pixel_width;
	float pixel_height;
	
	Vector2 position;
	
	public MiniMap( Stage stage, Terrain terrain ) {
		this.terrain = terrain;
		
		pixel_width = WIDTH / terrain.SQUARES_X;
		pixel_height = HEIGHT / terrain.SQUARES_Y;
		position = new Vector2( 2.5f, Constants.HUD_HEIGHT - ( pixel_height * terrain.SQUARES_Y ) - 12f );
		
		createMiniMap( terrain );
	}
	
	private void createMiniMap( Terrain terrain ) {
		mini_map = new Image[ terrain.getHeight() ][];
		
		for( int i = 0; i < terrain.SQUARES_Y; i++ ) {
			mini_map[i] = new Image[ terrain.SQUARES_X ];
			
			for( int j = 0; j < terrain.SQUARES_X; j++ ) {
				mini_map[i][j] = createItemImage( terrain.getSquareTerrain( new Vector2i( j, i ) ) );
			}
		}
	}

	private Image createItemImage( SquareTerrain square ) {
		Image image = new Image( Assets.minimap_textures.get( square.getMiniMapColor() ) );
		image.width = pixel_width;
		image.height = pixel_height;
		image.x = position.x + ( pixel_width * square.getNumber().x );
		image.y = position.y + ( pixel_height * square.getNumber().y );
		
		addActor( image );
		
		return image;
	}

	public void updatePosition( Vector2i number ) {
		SquareTerrain square = terrain.getSquareTerrain( number );
		
		mini_map[number.y][number.x].setRegion(
		  new TextureRegion( Assets.minimap_textures.get( square.getMiniMapColor() ) ) );
	}
}
