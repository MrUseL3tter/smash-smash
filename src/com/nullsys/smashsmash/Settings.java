package com.nullsys.smashsmash;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.graphics.Texture;
import com.nullsys.smashsmash.hammer.Hammer;

public class Settings {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 800;

    public static int arcadeHighscore = 0;
    public static int endlessHighscore = 0;

    public static int hammerType = Hammer.WOODEN_HAMMER;

    public static boolean musicEnabled = false;
    public static boolean soundEnabled = false;

    public static boolean cheatHammerTime = false;
    public static boolean cheatInvulnerability = false;
    public static boolean cheatScoreFrenzy = false;

    public static AssetManager getAssetManager() {

	Resolution[] resolutions = { new Resolution(480, 320, ".480320"), new Resolution(800, 480, ".800480"), new Resolution(856, 480, ".856480"), new Resolution(1280, 800, ".1280800") };
	ResolutionFileResolver resolver = new ResolutionFileResolver(new InternalFileHandleResolver(), resolutions);

	AssetManager assetManager = new AssetManager();
	assetManager.setLoader(Texture.class, new TextureLoader(resolver));

	Texture.setAssetManager(assetManager);

	return assetManager;
    }

}
