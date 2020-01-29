package com.firstrunner.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.firstrunner.game.Screens.GameScreen;
import com.firstrunner.game.Screens.MainMenu;
import com.firstrunner.game.Screens.NewGameScreen;

import static com.firstrunner.game.Globals.*;


public class Firstrunner extends Game {

	public static final int FR_WIDTH = 400;
	public static final int FR_HEIGHT = 208;

	private AssetManager manager;

	public SpriteBatch batch;

	public AssetManager getManager(){
		return manager;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		// load sound and textures
		manager.load(BACKGROUND_MENU, Texture.class);
		manager.load(PLAYER_BALL, Texture.class);
		manager.load(TEXTURE_CRATE, Texture.class);
		manager.load(TEXTURE_PLATFORM1,Texture.class);
		manager.load(TEXTURE_CLICKTOGO,Texture.class);
		manager.load(TEXTURE_BACKGROUNDENDLESS,Texture.class);
		manager.load(TEXTURE_EXPLOSION, Texture.class);
		manager.load(TEXTURE_ARROW, Texture.class);
		manager.load(TEXTURE_RETRYBUTTON, Texture.class);
		manager.load(TEXTURE_MAINMENU, Texture.class);


		manager.load(MUSIC_LOOP, Music.class);
		manager.load(SOUND_BALL_ROLLING, Sound.class);
		manager.load(SOUND_JUMP, Sound.class);
		manager.load(BOX_BREAKING, Sound.class);
		manager.load(SOUND_SPEEDUP, Sound.class);
		manager.load(SOUND_LANDING, Sound.class);


		manager.finishLoading();


		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
	super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
	}
}
