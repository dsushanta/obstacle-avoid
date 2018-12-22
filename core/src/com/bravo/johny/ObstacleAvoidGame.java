package com.bravo.johny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Logger;
import com.bravo.johny.screens.game.GameScreen;
import com.bravo.johny.screens.loading.LoadingScreen;

public class ObstacleAvoidGame extends Game {

	private AssetManager assetManager;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		assetManager = new AssetManager();
		assetManager.getLogger().setLevel(Logger.DEBUG);

		setScreen(new LoadingScreen(this));
	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
