package com.nullsys.smashsmash;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.graphics.Texture;
import com.nullsys.smashsmash.hammer.Hammer;
import com.nullsys.smashsmash.hammer.Hammer.Prices;

public class Settings {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 800;

    public static int arcadeHighscore = 0;
    public static int endlessHighscore = 0;

    public static int gold = 0;//90000;

    public static int hammerType = Hammer.WOODEN_HAMMER;

    public static boolean musicEnabled = false;
    public static boolean soundEnabled = false;
    public static boolean debugMode = true;

    public static boolean cheatHammerTime = false;
    public static boolean cheatInvulnerability = false;
    public static boolean cheatScoreFrenzy = false;

    public static boolean hasWoodenHammer = true;
    public static boolean hasMetalhead = false;
    public static boolean hasFierySmash = false;
    public static boolean hasFrostBite = false;
    public static boolean hasDusk = false;
    public static boolean hasTwilight = false;
    public static boolean hasStarryNight = false;
    public static boolean hasSpaceBlaster = false;
    public static boolean hasBubbles = false;
    public static boolean hasGoldDigger = false;
    public static boolean hasMjolnir = false;

    public static boolean buyHammer(int hammerType) {
	boolean bought = false;
	switch (hammerType) {
	    case Hammer.BUBBLES:
		if (gold >= Prices.BUBBLES && !hasBubbles) {
		    gold -= Prices.BUBBLES;
		    bought = true;
		}
		break;
	    case Hammer.DUSK:
		if (gold >= Prices.DUSK && !hasDusk) {
		    gold -= Prices.DUSK;
		    bought = true;
		}
		break;
	    case Hammer.FIERY_SMASH:
		if (gold >= Prices.FIERY_SMASH && !hasFierySmash) {
		    gold -= Prices.FIERY_SMASH;
		    bought = true;
		}
		break;
	    case Hammer.FROST_BITE:
		if (gold >= Prices.FROST_BITE && !hasFrostBite) {
		    gold -= Prices.FROST_BITE;
		    bought = true;
		}
		break;
	    case Hammer.METAL_HEAD:
		if (gold >= Prices.METAL_HEAD && !hasMetalhead) {
		    gold -= Prices.METAL_HEAD;
		    bought = true;
		}
		break;
	    case Hammer.SPACE_BLASTER:
		if (gold >= Prices.SPACE_BLASTER && !hasSpaceBlaster) {
		    gold -= Prices.SPACE_BLASTER;
		    bought = true;
		}
		break;
	    case Hammer.STARRY_NIGHT:
		if (gold >= Prices.STARRY_NIGHT && !hasStarryNight) {
		    gold -= Prices.STARRY_NIGHT;
		    bought = true;
		}
		break;
	    case Hammer.TWILIGHT:
		if (gold >= Prices.TWILIGHT && !hasTwilight) {
		    gold -= Prices.TWILIGHT;
		    bought = true;
		}
		break;
	    case Hammer.MJOLNIR:
		if (gold >= Prices.MJOLNIR && !hasMjolnir) {
		    gold -= Prices.MJOLNIR;
		    bought = true;
		}
		break;
	    default:
		assert false;
	}
	return bought;
    }

    public static AssetManager getAssetManager() {

	Resolution[] resolutions = { new Resolution(480, 320, ".480320"), new Resolution(800, 480, ".800480"), new Resolution(856, 480, ".856480"), new Resolution(1280, 800, ".1280800") };
	ResolutionFileResolver resolver = new ResolutionFileResolver(new InternalFileHandleResolver(), resolutions);

	AssetManager assetManager = new AssetManager();
	assetManager.setLoader(Texture.class, new TextureLoader(resolver));

	Texture.setAssetManager(assetManager);

	return assetManager;
    }

}
