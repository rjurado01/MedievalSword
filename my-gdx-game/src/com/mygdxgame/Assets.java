package com.mygdxgame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.google.gson.Gson;
import com.level.Level;

/**
 * Contain all textures of game and skins
 */
public class Assets {

	// Battle textures
	static TextureAtlas atlas;
	
	// Skin with font
	public static Skin skin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );	
	public static LabelStyle font2;
	
	// Levels
	int level1[][];
	
	
	public static void load() {
		atlas = new TextureAtlas( Gdx.files.internal( "images/pack" ) );
		skin.getFont( "default-font" ).setScale( 0.6f, 0.6f );
		loadFont();
	}
	
	public static TextureRegion getTextureRegion( String name ) {
		return atlas.findRegion(name);
	}
	
	public static TextureRegion getFrame( String name, int frame ) {
		return atlas.findRegion(name, frame);
	}	
	
	public static void loadFont() {
		BitmapFont aux = new BitmapFont( Gdx.files.internal( "skin/default.fnt" ),
         Gdx.files.internal("skin/default.png"), false);
		
		font2 = new LabelStyle( aux, Assets.skin.getFont( "default-font" ).getColor() );
		font2.font.setScale( 1f );
	}
	
	public Skin getSkin() {
		return skin;
	}
	
	public static Level getLevel( int level_number ) {
		Gson gson = new Gson();
		
		String file = "levels/level" + Integer.toString( level_number ) + ".json";
		Level level = gson.fromJson( Gdx.files.internal( file ).readString(), Level.class ); 

		return level;
	}
	
	public static String readFile( String path ) {
		String content = "";
		
        try {
			FileReader input = new FileReader( path );
			BufferedReader bufRead = new BufferedReader(input);
			
            String line = bufRead.readLine();
            
            while (line != null){
            	content += line;
                line = bufRead.readLine();
            }
            
            bufRead.close();			
        } catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Usage: java ReadFile filename\n");			
		} catch (IOException e){
            e.printStackTrace();
        }
        
        return content;
	}
}
