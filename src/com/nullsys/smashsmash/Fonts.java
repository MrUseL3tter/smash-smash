package com.nullsys.smashsmash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Fonts {

    public static BitmapFont actionJackson115;
    public static BitmapFont akaDylanCollage64;
    public static BitmapFont arialNarrow32Italic;
    public static BitmapFont goodGirlx32;
    public static BitmapFont bdCartoonShoutx42;
    public static BitmapFont bdCartoonShoutx23orange;
    public static BitmapFont bdCartoonShoutx32orange;

    public static void load(AssetManager assetManager) {
	assetManager.load("data/fonts/ACTION_JACKSON_115.png", Texture.class);
	assetManager.load("data/fonts/AKA_DYLAN_COLLAGE_64.png", Texture.class);
	assetManager.load("data/fonts/ARIAL_NARROW_32_ITALIC.png", Texture.class);
	assetManager.load("data/fonts/GOODGIRL_32.png", Texture.class);
	assetManager.load("data/fonts/BD_CARTOON_SHOUT_42.png", Texture.class);
	assetManager.load("data/fonts/BD_CARTOON_SHOUT_23_ORANGE.png", Texture.class);
	assetManager.load("data/fonts/BD_CARTOON_SHOUT_32_ORANGE.png", Texture.class);
    }

    public static void retrieve(AssetManager assetManager) {
	Texture texture;
	texture = assetManager.get("data/fonts/ACTION_JACKSON_115.png", Texture.class);
	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	actionJackson115 = new BitmapFont(Gdx.files.internal("data/fonts/ACTION_JACKSON_115.fnt"), new TextureRegion(texture), false);

	texture = assetManager.get("data/fonts/AKA_DYLAN_COLLAGE_64.png", Texture.class);
	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	akaDylanCollage64 = new BitmapFont(Gdx.files.internal("data/fonts/AKA_DYLAN_COLLAGE_64.fnt"), new TextureRegion(texture), false);

	texture = assetManager.get("data/fonts/ARIAL_NARROW_32_ITALIC.png", Texture.class);
	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	arialNarrow32Italic = new BitmapFont(Gdx.files.internal("data/fonts/ARIAL_NARROW_32_ITALIC.fnt"), new TextureRegion(texture), false);

	texture = assetManager.get("data/fonts/GOODGIRL_32.png", Texture.class);
	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	goodGirlx32 = new BitmapFont(Gdx.files.internal("data/fonts/GOODGIRL_32.fnt"), new TextureRegion(texture), false);

	texture = assetManager.get("data/fonts/BD_CARTOON_SHOUT_42.png", Texture.class);
	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	bdCartoonShoutx42 = new BitmapFont(Gdx.files.internal("data/fonts/BD_CARTOON_SHOUT_42.fnt"), new TextureRegion(texture), false);

	texture = assetManager.get("data/fonts/BD_CARTOON_SHOUT_23_ORANGE.png", Texture.class);
	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	bdCartoonShoutx23orange = new BitmapFont(Gdx.files.internal("data/fonts/BD_CARTOON_SHOUT_23_ORANGE.fnt"), new TextureRegion(texture), false);

	texture = assetManager.get("data/fonts/BD_CARTOON_SHOUT_32_ORANGE.png", Texture.class);
	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	bdCartoonShoutx32orange = new BitmapFont(Gdx.files.internal("data/fonts/BD_CARTOON_SHOUT_32_ORANGE.fnt"), new TextureRegion(texture), false);
    }
}
