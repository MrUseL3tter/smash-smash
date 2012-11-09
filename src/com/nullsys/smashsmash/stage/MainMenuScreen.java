package com.nullsys.smashsmash.stage;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Back;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.tweenengine.utils.DynamicButton;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;
import com.nullsys.smashsmash.Art;
import com.nullsys.smashsmash.Settings;

public class MainMenuScreen extends DynamicScreen implements TweenCallback {

    DynamicButton stats;
    DynamicButton hammers;
    DynamicButton arcade;
    DynamicButton endless;
    DynamicSprite title;
    DynamicText prompt;

    boolean showPlayOptions;

    public MainMenuScreen(Game game) {
	super(game, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
	TextureRegion upstate, hoverstate, downstate;
	upstate = Art.menu.findRegion("STATS");
	hoverstate = Art.menu.findRegion("STATS");
	downstate = Art.menu.findRegion("STATS");
	stats = new DynamicButton(upstate, hoverstate, downstate, new Vector2(984, 41));

	upstate = Art.menu.findRegion("HAMMERS");
	hoverstate = Art.menu.findRegion("HAMMERS");
	downstate = Art.menu.findRegion("HAMMERS");
	hammers = new DynamicButton(upstate, hoverstate, downstate, new Vector2(1182, 41));

	upstate = Art.menu.findRegion("ARCADE");
	hoverstate = Art.menu.findRegion("ARCADE");
	downstate = Art.menu.findRegion("ARCADE");
	arcade = new DynamicButton(upstate, hoverstate, downstate, new Vector2(320, 420));
	arcade.visible = false;

	upstate = Art.menu.findRegion("SURVIVAL");
	hoverstate = Art.menu.findRegion("SURVIVAL");
	downstate = Art.menu.findRegion("SURVIVAL");
	endless = new DynamicButton(upstate, hoverstate, downstate, new Vector2(938, 420));
	endless.visible = false;

	title = new DynamicSprite(Art.menu.findRegion("TITLE"), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);

	prompt = new DynamicText(new BitmapFont(), "TAP THE SCREEN");
	prompt.setPosition(Settings.SCREEN_WIDTH / 2, 120);
	prompt.setScale(2f, 2f);
	prompt.interpolateAlpha(.5f, 150, false).repeatYoyo(Tween.INFINITY, 150).start(prompt.tweenManager);

	Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void onEvent(int type, BaseTween<?> source) {
	if (type == COMPLETE && source.equals(arcade.tween))
	    game.setScreen(new ArcadeStageScreen(game));
	else if (type == COMPLETE && source.equals(endless.tween))
	    game.setScreen(new EndlessStageScreen(game));

    }

    @Override
    public void onTouchDown(float x, float y, int pointer, int button) {
	x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();
	if (!showPlayOptions && stats.getBounds().contains(x, Settings.SCREEN_HEIGHT - y))
	    // TODO show stats screen
	    ;
	else if (!showPlayOptions && hammers.getBounds().contains(x, Settings.SCREEN_HEIGHT - y))
	    // TODO show hammers screen
	    ;
	else if (!showPlayOptions) {
	    showPlayOptions = true;
	    arcade.visible = true;
	    arcade.setScale(0f, 0f);
	    arcade.interpolateScaleXY(1f, 1f, Back.OUT, 250, true);
	    endless.visible = true;
	    endless.setScale(0f, 0f);
	    endless.interpolateScaleXY(1f, 1f, Back.OUT, 250, true).delay(150);
	    title.interpolateAlpha(0f, 100, true);
	    stats.interpolateY(stats.getY() - 200, 100, true);
	    hammers.interpolateY(hammers.getY() - 200, 100, true);
	    prompt.visible = false;
	} else if (showPlayOptions && arcade.getBounds().contains(x, Settings.SCREEN_HEIGHT - y)) {
	    interpolateEnd();
	    arcade.tween.setCallback(this);
	} else if (showPlayOptions && endless.getBounds().contains(x, Settings.SCREEN_HEIGHT - y)) {
	    interpolateEnd();
	    endless.tween.setCallback(this);
	}
    }

    @Override
    public void render(float delta) {
	super.render(delta);
	spriteBatch.begin();
	spriteBatch.draw(Art.lawnBackground1, 0, -(1024 - 800));
	spriteBatch.draw(Art.lawnBackground2, 1280 - 256, -(1024 - 800));
	stats.render(spriteBatch);
	stats.update(delta);
	hammers.render(spriteBatch);
	hammers.update(delta);
	arcade.render(spriteBatch);
	arcade.update(delta);
	endless.render(spriteBatch);
	endless.update(delta);
	title.render(spriteBatch);
	title.update(delta);
	prompt.render(spriteBatch);
	prompt.update(delta);
	spriteBatch.end();
	spriteBatch.setColor(1f, 1f, 1f, 1f);
    }

    private void interpolateEnd() {
	arcade.interpolateScaleXY(.5f, .5f, Back.IN, 500, true);
	endless.interpolateScaleXY(.5f, .5f, Back.IN, 500, true);
    }

}
