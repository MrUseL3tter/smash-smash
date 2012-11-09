package com.nullsys.smashsmash.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.nullsys.smashsmash.Art;
import com.nullsys.smashsmash.Fonts;
import com.nullsys.smashsmash.Particles;
import com.nullsys.smashsmash.Settings;
import com.nullsys.smashsmash.Sounds;
import com.nullsys.smashsmash.alien.AliensArt;

public class StageLoadingScreen extends DynamicScreen {

    AssetManager assetManager;
    BitmapFont label;
    ShapeRenderer shapeRenderer;
    String text = "NOW LOADING...";
    EndlessStageScreen stageScreen;

    public StageLoadingScreen(Game game, AssetManager assetManager) {
	super(game);
	this.assetManager = assetManager;
	label = new BitmapFont(Gdx.files.internal("data/fonts/ARIAL_NARROW_32_ITALIC.fnt"), false);
	shapeRenderer = new ShapeRenderer();
	Art.load(assetManager);
	AliensArt.load(assetManager);
	Fonts.load(assetManager);
	Particles.load();
	Sounds.load(assetManager);

    }

    public StageLoadingScreen(Game game, EndlessStageScreen stageScreen, AssetManager assetManager) {
	super(game);
	this.assetManager = assetManager;
	label = new BitmapFont(Gdx.files.internal("data/fonts/ARIAL_NARROW_32_ITALIC.fnt"), false);
	shapeRenderer = new ShapeRenderer();
	this.stageScreen = stageScreen;
	Art.load(assetManager);
	AliensArt.load(assetManager);
	Fonts.load(assetManager);
	Particles.load();
	Sounds.load(assetManager);

    }

    @Override
    public void render(float delta) {
	updateAssets();
	spriteBatch.begin();
	label.draw(spriteBatch, text, 0, 100 + label.getCapHeight());
	spriteBatch.end();
	shapeRenderer.begin(ShapeType.FilledRectangle);
	shapeRenderer.filledRect(0, 20, Settings.SCREEN_WIDTH, 70, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
	shapeRenderer.filledRect(0, 25, Settings.SCREEN_WIDTH, 60, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
	shapeRenderer.filledRect(0, 30, Settings.SCREEN_WIDTH * assetManager.getProgress(), 50);
	shapeRenderer.end();
	if (text.equals("NOW LOADING"))
	    text = "NOW LOADING.";
	else if (text.equals("NOW LOADING."))
	    text = "NOW LOADING..";
	else if (text.equals("NOW LOADING.."))
	    text = "NOW LOADING...";
	else if (text.equals("NOW LOADING..."))
	    text = "NOW LOADING";
    }

    @Override
    public void resume() {
	game.setScreen(new StageLoadingScreen(game, stageScreen, assetManager));
    }

    private void updateAssets() {
	assetManager.update();
	if (assetManager.getProgress() == 1.0) { // Loading is finished.
	    Art.retrieve(assetManager);
	    AliensArt.retrieve(assetManager);
	    Fonts.retrieve(assetManager);
	    Sounds.retrieve(assetManager);

	    if (stageScreen == null)
		game.setScreen(new ArcadeStageScreen(game));//MainMenuScreen(game));
	    else
		game.setScreen(stageScreen);
	}
    }

}
