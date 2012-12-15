package com.me.mygdxgame;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Contain all textures of game and skins
 */
public class Assets {

	// Texture atlas with battle textures
	static TextureAtlas atlas;
	
	// Save inverses textures, for next petition
	static protected Map<String, TextureRegion> inverses;
	
	// Skin with font
	public static Skin skin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );
	
	/**
	 * Class constructor
	 */
	public Assets() {
		load();
	}
	
	/**
	 * Load atlas and skin
	 */
	public void load() {
		atlas = new TextureAtlas(Gdx.files.internal("images/pack"));
		
		inverses = new HashMap<String, TextureRegion>();
		
		skin.getFont( "default-font" ).setScale( 0.6f, 0.6f);
	}
	
	/**
	 * Return 'name' TextureRegion
	 * @param name TextureRegion name
	 * @return TextureRegion
	 */
	public static TextureRegion getTextureRegion(String name) {
		return atlas.findRegion(name);
	}
	
	/**
	 * Flip horizontally and return TextureRegion
	 * @param name texture name
	 * @return TextureRegion
	 */
	public static TextureRegion getFlipTextureRegion(String name) {
		if( inverses.get( name ) == null ) {

			TextureRegion aux = new TextureRegion( atlas.findRegion(name) );
			aux.flip(true, false);
			
			inverses.put( name, aux );
		}
		
		return inverses.get( name );
	}
	
	/**
	 * Get frame texture region
	 * @param name texture name
	 * @param frame frame number of texture
	 * @return TextureRegion
	 */
	public static TextureRegion getFrame(String name, int frame) {
		return atlas.findRegion(name, frame);
	}
	
	/**
	 * Get flip frame texture region
	 * @param name texture name
	 * @param frame texture frame
	 * @return texture region
	 */
	public static TextureRegion getFlipFrame(String name, int frame) {
		if( inverses.get( name + "_" + frame ) == null ) {

			TextureRegion aux = new TextureRegion( atlas.findRegion(name, frame) );
			aux.flip(true, false);
			
			inverses.put( name + "_" + frame, aux );
		}
		
		return inverses.get( name + "_" + frame );
	}
	
	/**
	 * Get skin
	 * @return skin
	 */
	public Skin getSkin() {
		return skin;
	}
}
