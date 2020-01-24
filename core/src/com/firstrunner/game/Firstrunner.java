package com.firstrunner.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.firstrunner.game.Screens.GameScreen;
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

		manager.load(BOX_BREAKING, Sound.class);
		manager.finishLoading();


		setScreen(new GameScreen(this));
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
