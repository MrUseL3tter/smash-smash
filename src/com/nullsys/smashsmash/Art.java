package com.nullsys.smashsmash;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Art {

    static AssetManager assetManager;

    public static Texture bonusEffects;
    public static Texture lawnBackground1;
    public static Texture lawnBackground2;

    public static Texture hud;
    public static TextureRegion hudBuffEffectCoinRain;
    public static TextureRegion hudBuffEffectHammerTime;
    public static TextureRegion hudBuffEffectScoreFrenzy;
    public static TextureRegion hudLifePoint;

    public static Texture hammers;
    public static TextureRegion hammerWooden;
    public static TextureRegion hammerMetal;
    public static TextureRegion hammerStarryNight;
    public static TextureRegion hammerGoldDigger;
    public static TextureRegion hammerMjolnir;

    public static Texture coins;

    public static Texture readyPrompt;
    public static Texture blackFill;
    public static Texture pinwheel;

    public static void load(AssetManager assetManager) {
	assetManager.load("data/gfx/SAMPLE_HUD.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_COINS.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_BACKGROUND1.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_BACKGROUND2.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_READY_PROMPT.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_PINWHEEL.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_BONUS_EFFECTS.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_BLACKFILL.png", Texture.class);
    }

    public static void retrieve(AssetManager assetManager) {
	bonusEffects = assetManager.get("data/gfx/SAMPLE_BONUS_EFFECTS.png", Texture.class);
	bonusEffects.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	lawnBackground1 = assetManager.get("data/gfx/SAMPLE_BACKGROUND1.png", Texture.class);
	lawnBackground1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	lawnBackground2 = assetManager.get("data/gfx/SAMPLE_BACKGROUND2.png", Texture.class);
	lawnBackground2.setFilter(TextureFilter.Linear, TextureFilter.Linear);

	hud = assetManager.get("data/gfx/SAMPLE_HUD.png", Texture.class);
	hud.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	hudBuffEffectScoreFrenzy = new TextureRegion(hud, 0, 99, 99, 99);
	hudBuffEffectHammerTime = new TextureRegion(hud, 0, 0, 99, 99);
	hudBuffEffectCoinRain = new TextureRegion(hud, 99, 0, 99, 99);
	hudLifePoint = new TextureRegion(hud, 99, 99, 85, 78);

	hammerWooden = new TextureRegion(hud, 297, 0, 182, 182);

	coins = assetManager.get("data/gfx/SAMPLE_COINS.png", Texture.class);

	readyPrompt = assetManager.get("data/gfx/SAMPLE_READY_PROMPT.png", Texture.class);
	readyPrompt.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	pinwheel = assetManager.get("data/gfx/SAMPLE_PINWHEEL.png", Texture.class);
	pinwheel.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	blackFill = assetManager.get("data/gfx/SAMPLE_BLACKFILL.png", Texture.class);
	blackFill.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }

    private static AssetManager getAssetManager() {
	if (assetManager == null) {
	    Resolution[] resolutions = { new Resolution(480, 320, ".480320"), new Resolution(800, 480, ".800480"), new Resolution(856, 480, ".856480"), new Resolution(1280, 800, ".1280800") };
	    ResolutionFileResolver resolver = new ResolutionFileResolver(new InternalFileHandleResolver(), resolutions);

	    assetManager = new AssetManager();
	    assetManager.setLoader(Texture.class, new TextureLoader(resolver));

	    Texture.setAssetManager(assetManager);

	    return assetManager;
	} else
	    return assetManager;
    }
}
