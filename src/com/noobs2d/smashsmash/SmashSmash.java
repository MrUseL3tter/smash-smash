package com.noobs2d.smashsmash;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noobs2d.smashsmash.screen.StageLoadingScreen;
import com.noobs2d.tweenengine.utils.DynamicDisplay;

public class SmashSmash extends Game {

    private BitmapFont debugFeed;
    private SpriteBatch spriteBatch;
    private float timeElapsed = 0f;

    @Override
    public void create() {
	debugFeed = new BitmapFont();
	debugFeed.setScale(2f);
	spriteBatch = new SpriteBatch();
	initTween();
	init();
    }

    @Override
    public void render() {
	timeElapsed += Gdx.graphics.getDeltaTime() * Settings.getGameSpeed();
	while (timeElapsed > 1.0f / 60.0f)
	    timeElapsed -= 1.0f / 60.0f;

	if (getScreen() != null) {
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    getScreen().render(Gdx.graphics.getDeltaTime() * Settings.getGameSpeed());
	}

	if (Settings.DEBUG_MODE) {
	    spriteBatch.begin();
	    debugFeed.drawWrapped(spriteBatch, Gdx.graphics.getFramesPerSecond() + "fps\n", 0f, 800f, 600, HAlignment.LEFT);
	    spriteBatch.end();
	}
    }

    @Override
    public void resume() {
	timeElapsed = 1.0f / 60.f + Gdx.graphics.getDeltaTime();
	if (getScreen() != null)
	    getScreen().resume();
    }

    private void init() {
	setScreen(new StageLoadingScreen(this, Settings.getAssetManager()));
    }

    private void initTween() {
	DynamicDisplay.register();
    }
}
