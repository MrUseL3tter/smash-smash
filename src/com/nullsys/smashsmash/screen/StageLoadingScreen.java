package com.nullsys.smashsmash.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.noobs2d.tweenengine.utils.DynamicDisplay.DynamicRegistration;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;
import com.nullsys.smashsmash.Art;
import com.nullsys.smashsmash.Fonts;
import com.nullsys.smashsmash.Particles;
import com.nullsys.smashsmash.Sounds;
import com.nullsys.smashsmash.alien.AliensArt;

public class StageLoadingScreen extends DynamicScreen {

    private AssetManager assetManager;
    private SmashSmashStage stageScreen;
    private DynamicSprite hammerIcon;
    private DynamicText label;
    private DynamicText percentage;
    private DynamicText percentageSymbol;
    private float elapsed;

    public StageLoadingScreen(Game game, AssetManager assetManager) {
	super(game);
	this.assetManager = assetManager;

	BitmapFont font = new BitmapFont(Gdx.files.internal("data/fonts/KRONIKA.fnt"), false);
	font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

	hammerIcon = new DynamicSprite(new TextureRegion(new Texture(Gdx.files.internal("data/gfx/loading/HAMMER-ICON.png"))), 15, 15);
	hammerIcon.setRegistration(DynamicRegistration.BOTTOM_LEFT);

	label = new DynamicText(font, "Now Loading");
	label.setRegistration(DynamicRegistration.CENTER_LEFT);
	label.setScale(.61f);
	label.setPosition(88, 47);

	percentage = new DynamicText(font, "");
	percentage.setRegistration(DynamicRegistration.CENTER_RIGHT);
	percentage.setScale(.61f);
	percentage.setPosition(465, 47);

	percentageSymbol = new DynamicText(font, "%");
	percentageSymbol.setScale(.61f);
	percentageSymbol.setPosition(492, 47);

	Art.load(assetManager);
	AliensArt.load(assetManager);
	Fonts.load(assetManager);
	Particles.load();
	Sounds.load(assetManager);
    }

    @Override
    public void dispose() {
	hammerIcon.dispose();
	label.dispose();
	percentage.dispose();
	percentageSymbol.dispose();
    }

    @Override
    public void render(float delta) {
	elapsed += delta;
	updateAssets();
	batch.begin();
	hammerIcon.render(batch);
	label.render(batch);
	percentage.setText("" + (int) (assetManager.getProgress() * 100));
	percentage.render(batch);
	percentageSymbol.render(batch);
	batch.end();

	if (elapsed > .15f) {
	    if (label.getText().equals("Now Loading"))
		label.setText("Now Loading.");
	    else if (label.getText().equals("Now Loading."))
		label.setText("Now Loading..");
	    else if (label.getText().equals("Now Loading.."))
		label.setText("Now Loading...");
	    else if (label.getText().equals("Now Loading..."))
		label.setText("Now Loading");
	    elapsed = 0f;
	}
    }

    private void updateAssets() {
	assetManager.update();
	if (assetManager.getProgress() == 1.0) { // Loading is finished.
	    Art.retrieve(assetManager);
	    AliensArt.retrieve(assetManager);
	    Fonts.retrieve(assetManager);
	    Sounds.retrieve(assetManager);

	    if (stageScreen == null)
		game.setScreen(new MainMenuScreen(game));
	    else
		game.setScreen(stageScreen);
	}
    }

}
