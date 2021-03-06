package com.noobs2d.smashsmash;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Art {

    static AssetManager assetManager;

    public static Texture lawnBackground1;
    public static Texture lawnBackground2;
    public static TextureAtlas menu;
    public static TextureAtlas modes;
    public static TextureAtlas pukes;
    public static TextureAtlas hammers;
    public static TextureAtlas result;
    public static TextureAtlas goldbar;

    public static Texture coins;

    public static Texture readyPrompt;
    public static Texture blackFill;
    public static Texture whiteFill;
    public static Texture pinwheel;
    public static Texture pinwheel2;

    public static void load(AssetManager assetManager) {
	assetManager.load("data/gfx/SAMPLE_COINS.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_BACKGROUND1.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_BACKGROUND2.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_READY_PROMPT.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_PINWHEEL.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_PINWHEEL2.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_BLACKFILL.png", Texture.class);
	assetManager.load("data/gfx/SAMPLE_WHITEFILL.png", Texture.class);

	assetManager.load("data/gfx/MENU.pack", TextureAtlas.class);
	assetManager.load("data/gfx/MODES.pack", TextureAtlas.class);
	assetManager.load("data/gfx/HAMMERS.pack", TextureAtlas.class);
	assetManager.load("data/gfx/PUKES.pack", TextureAtlas.class);
	assetManager.load("data/gfx/RESULT.pack", TextureAtlas.class);
	assetManager.load("data/gfx/GOLDBAR.pack", TextureAtlas.class);
    }

    public static void retrieve(AssetManager assetManager) {
	lawnBackground1 = assetManager.get("data/gfx/SAMPLE_BACKGROUND1.png", Texture.class);
	lawnBackground1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	lawnBackground2 = assetManager.get("data/gfx/SAMPLE_BACKGROUND2.png", Texture.class);
	lawnBackground2.setFilter(TextureFilter.Linear, TextureFilter.Linear);

	menu = assetManager.get("data/gfx/MENU.pack", TextureAtlas.class);
	modes = assetManager.get("data/gfx/MODES.pack", TextureAtlas.class);
	pukes = assetManager.get("data/gfx/PUKES.pack", TextureAtlas.class);
	result = assetManager.get("data/gfx/RESULT.pack", TextureAtlas.class);
	goldbar = assetManager.get("data/gfx/GOLDBAR.pack", TextureAtlas.class);
	hammers = assetManager.get("data/gfx/HAMMERS.pack", TextureAtlas.class);

	coins = assetManager.get("data/gfx/SAMPLE_COINS.png", Texture.class);

	readyPrompt = assetManager.get("data/gfx/SAMPLE_READY_PROMPT.png", Texture.class);
	readyPrompt.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	pinwheel = assetManager.get("data/gfx/SAMPLE_PINWHEEL.png", Texture.class);
	pinwheel.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	pinwheel2 = assetManager.get("data/gfx/SAMPLE_PINWHEEL2.png", Texture.class);
	pinwheel2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	blackFill = assetManager.get("data/gfx/SAMPLE_BLACKFILL.png", Texture.class);
	whiteFill = assetManager.get("data/gfx/SAMPLE_WHITEFILL.png", Texture.class);
    }
}
