package com.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;


import com.google.gson.Gson;
import com.level.Level;
import com.modules.map.ui.MiniMap;
import com.races.humans.units.*;

/**
 * Contain all textures of game and skins
 */
public class Assets {

	// Battle textures
	static TextureAtlas atlas;

	// Units
	static public Map<Integer, Unit> units;

	// Skin with font
	public static Skin skin;
	public static LabelStyle font2;
	public static LabelStyle font_black;

	// Levels
	int level1[][];

	// MiniMap textures
	public static Map<Integer, Texture> minimap_textures;

	// Sounds and music
	public static Map<String, Sound> sounds;
	public static Map<String, Music> music;


	public static void load() {
		atlas = new TextureAtlas( Gdx.files.internal( "images/game.atlas" ) );
		loadFonts();
		loadMiniMapTextures();
		loadMusic();
		loadSounds();
	}

	public static TextureRegion getTextureRegion( String name ) {
		return atlas.findRegion(name);
	}

	public static TextureRegion getFrame( String name, int frame ) {
		return atlas.findRegion(name, frame);
	}

	public static void loadFonts() {
		skin = new Skin( Gdx.files.internal( "skin/uiskin.json" ) );

		BitmapFont aux = new BitmapFont( Gdx.files.internal( "skin/default.fnt" ),
				Gdx.files.internal("skin/default.png"), false);

		font_black = new LabelStyle( aux, new Color( 0.0f,0.0f,0.0f,1 ) );

		BitmapFont aux2 = new BitmapFont( Gdx.files.internal( "fonts/fontBlack.fnt" ),
				Gdx.files.internal("fonts/fontBlack.png"), false);

		font2 = new LabelStyle( aux2, aux.getColor() );
	}

	private static void loadMiniMapTextures() {
		minimap_textures = new HashMap<Integer, Texture>();

		Pixmap pixmap = new Pixmap( 2, 2, Format.RGBA8888 );

        pixmap.setColor( Color.BLACK );
        pixmap.fillRectangle(0, 0, 2, 2);
		minimap_textures.put( MiniMap.FOG,  new Texture( pixmap, Format.RGB888, false ) );

        pixmap.setColor( Color.WHITE );
        pixmap.fillRectangle(0, 0, 2, 2);
		minimap_textures.put( MiniMap.WHITE,  new Texture( pixmap, Format.RGB888, false ) );

        pixmap.setColor( new Color(0.68f, 0.25f, 0.13f, 1) );
        pixmap.fillRectangle(0, 0, 2, 2);
		minimap_textures.put( MiniMap.ROAD,  new Texture( pixmap, Format.RGB888, false ) );

        pixmap.setColor( new Color(0.3f, 0.6f, 1f, 1) );
        pixmap.fillRectangle(0, 0, 2, 2);
        minimap_textures.put( MiniMap.WHATER,  new Texture( pixmap, Format.RGB888, false ) );

        pixmap.setColor( new Color(0.3f, 1, 0.3f, 1));
        pixmap.fillRectangle(0, 0, 2, 2);
        minimap_textures.put( MiniMap.GRASS,  new Texture( pixmap, Format.RGB888, false ) );

        pixmap.setColor( Color.GRAY );
        pixmap.fillRectangle(0, 0, 2, 2);
        minimap_textures.put( MiniMap.GREY,  new Texture( pixmap, Format.RGB888, false ) );

        pixmap.setColor( Color.BLUE );
        pixmap.fillRectangle(0, 0, 2, 2);
        minimap_textures.put( MiniMap.BLUE,  new Texture( pixmap, Format.RGB888, false ) );

        pixmap.setColor( Color.RED );
        pixmap.fillRectangle(0, 0, 2, 2);
        minimap_textures.put( MiniMap.RED,  new Texture( pixmap, Format.RGB888, false ) );
	}

	private static void loadMusic() {
		music = new HashMap<String, Music>();

		music.put( "map_music",
			Gdx.audio.newMusic(Gdx.files.internal("sounds/map_music.ogg")) );
	}

	public static void playMusic( String name ) {
		if( music.containsKey( name ) ) {
			music.get( name ).setLooping( true );
			music.get( name ).play();
		}
	}

	public static void stopMusic( String name ) {
		if( music.containsKey( name ) )
			music.get( name ).stop();
	}

	private static void loadSounds() {
		sounds = new HashMap<String, Sound>();

		sounds.put( "hero_walk",
			Gdx.audio.newSound(Gdx.files.internal("sounds/hero_walk.ogg")) );

		sounds.put( "square_selected",
			Gdx.audio.newSound(Gdx.files.internal("sounds/square_selected.ogg")) );

		sounds.put( "stone_pile",
				Gdx.audio.newSound(Gdx.files.internal("sounds/stone_pile.ogg")) );

		sounds.put( "wood_pile",
				Gdx.audio.newSound(Gdx.files.internal("sounds/wood_pile.ogg")) );

		sounds.put( "gold_pile",
				Gdx.audio.newSound(Gdx.files.internal("sounds/gold_pile.ogg")) );

		sounds.put( "capture_structure",
				Gdx.audio.newSound(Gdx.files.internal("sounds/capture_structure.ogg")) );

		sounds.put( "clock",
				Gdx.audio.newSound(Gdx.files.internal("sounds/clock.ogg")) );
	}

	public static void playSound( String name, boolean loop ) {
		if( sounds.containsKey( name ) ) {
			if( loop )
				sounds.get( name ).loop();
			else
				sounds.get( name ).play();
		}
	}

	public static void stopSound( String name ) {
		if( sounds.containsKey( name ) )
			sounds.get( name ).stop();
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


	/* public static void saveLevel() {
		Gson gson = new Gson();
		Gdx.files.local( "save.txt" ).writeString( "level", true );
	} */

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

	public static void loadUnits() {
		units = new HashMap<Integer, Unit>();
		units.put( Constants.VILLAGER, new Villager() );
		units.put( Constants.ARCHER, new Archer() );
		units.put( Constants.CRUSADER, new Crusader() );
		units.put( Constants.WIZARD, new Wizard() );
		units.put( Constants.KNIGHT, new Knight() );
	}

	public static Unit getUnit( int id ) {
		return units.get(id);
	}
}
