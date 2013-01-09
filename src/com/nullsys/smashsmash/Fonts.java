package com.nullsys.smashsmash;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Fonts {

    public static BitmapFont KRONIKA;
    public static BitmapFont KRONIKA_GRAYSTROKE;
    public static BitmapFont KRONIKA_BLACKSTROKE;

    public static BitmapFont actionJackson115;
    public static BitmapFont akaDylanCollage64;
    public static BitmapFont arialNarrow32Italic;
    public static BitmapFont bdCartoonShoutx42;
    public static BitmapFont bdCartoonShoutx50GrayStroke;
    public static BitmapFont bdCartoonShoutx23orange;
    public static BitmapFont bdCartoonShoutx32orange;
    public static BitmapFont berlinSansx32GrayStroke;
    public static BitmapFont berlinSansx32OrangeStroke;

    public static void load(AssetManager assetManager) {
	assetManager.load("data/fonts/KRONIKA.fnt", BitmapFont.class);
	assetManager.load("data/fonts/KRONIKA-GRAYSTROKE.fnt", BitmapFont.class);
	assetManager.load("data/fonts/KRONIKA-BLACKSTROKE.fnt", BitmapFont.class);

	assetManager.load("data/fonts/ACTION_JACKSON_115.fnt", BitmapFont.class);
	assetManager.load("data/fonts/AKA_DYLAN_COLLAGE_64.fnt", BitmapFont.class);
	assetManager.load("data/fonts/ARIAL_NARROW_32_ITALIC.fnt", BitmapFont.class);
	assetManager.load("data/fonts/ARIAL_NARROW_32_ITALIC.fnt", BitmapFont.class);
	assetManager.load("data/fonts/ACTION_JACKSON_115.fnt", BitmapFont.class);
	assetManager.load("data/fonts/AKA_DYLAN_COLLAGE_64.fnt", BitmapFont.class);
	assetManager.load("data/fonts/ARIAL_NARROW_32_ITALIC.fnt", BitmapFont.class);
	assetManager.load("data/fonts/BD_CARTOON_SHOUT_42.fnt", BitmapFont.class);
	assetManager.load("data/fonts/BD_CARTOON_SHOUT_23_ORANGE.fnt", BitmapFont.class);
	assetManager.load("data/fonts/BD_CARTOON_SHOUT_32_ORANGE.fnt", BitmapFont.class);
	assetManager.load("data/fonts/BD-CARTOON-32-GRAYSTROKE.fnt", BitmapFont.class);
	assetManager.load("data/fonts/BERLIN-SANS-32-GRAYSTROKE.fnt", BitmapFont.class);
	assetManager.load("data/fonts/BERLIN-SANS-32-ORANGESTROKE.fnt", BitmapFont.class);
    }

    public static void retrieve(AssetManager assetManager) {
	KRONIKA = assetManager.get("data/fonts/KRONIKA.fnt", BitmapFont.class);
	KRONIKA.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

	KRONIKA_GRAYSTROKE = assetManager.get("data/fonts/KRONIKA-GRAYSTROKE.fnt", BitmapFont.class);
	KRONIKA_GRAYSTROKE.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

	KRONIKA_BLACKSTROKE = assetManager.get("data/fonts/KRONIKA-BLACKSTROKE.fnt", BitmapFont.class);
	KRONIKA_BLACKSTROKE.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

	actionJackson115 = assetManager.get("data/fonts/ACTION_JACKSON_115.fnt", BitmapFont.class);
	actionJackson115.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	akaDylanCollage64 = assetManager.get("data/fonts/AKA_DYLAN_COLLAGE_64.fnt", BitmapFont.class);
	akaDylanCollage64.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	arialNarrow32Italic = assetManager.get("data/fonts/ARIAL_NARROW_32_ITALIC.fnt", BitmapFont.class);
	arialNarrow32Italic.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	bdCartoonShoutx23orange = assetManager.get("data/fonts/BD_CARTOON_SHOUT_23_ORANGE.fnt", BitmapFont.class);
	bdCartoonShoutx23orange.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	bdCartoonShoutx42 = assetManager.get("data/fonts/BD_CARTOON_SHOUT_42.fnt", BitmapFont.class);
	bdCartoonShoutx42.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	bdCartoonShoutx32orange = assetManager.get("data/fonts/BD_CARTOON_SHOUT_32_ORANGE.fnt", BitmapFont.class);
	bdCartoonShoutx32orange.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	bdCartoonShoutx50GrayStroke = assetManager.get("data/fonts/BD-CARTOON-32-GRAYSTROKE.fnt", BitmapFont.class);
	bdCartoonShoutx50GrayStroke.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	berlinSansx32GrayStroke = assetManager.get("data/fonts/BERLIN-SANS-32-GRAYSTROKE.fnt", BitmapFont.class);
	berlinSansx32GrayStroke.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	berlinSansx32OrangeStroke = assetManager.get("data/fonts/BERLIN-SANS-32-ORANGESTROKE.fnt", BitmapFont.class);
	berlinSansx32OrangeStroke.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }
}
