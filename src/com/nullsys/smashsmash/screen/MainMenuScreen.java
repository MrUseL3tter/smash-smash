package com.nullsys.smashsmash.screen;

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
import com.noobs2d.tweenengine.utils.DynamicButton.DynamicButtonCallback;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;
import com.noobs2d.tweenengine.utils.DynamicToggleButton;
import com.nullsys.smashsmash.Art;
import com.nullsys.smashsmash.Settings;

public class MainMenuScreen extends DynamicScreen implements DynamicButtonCallback, TweenCallback {

    DynamicButton stats;
    DynamicButton hammers;
    DynamicButton arcade;
    DynamicButton endless;
    DynamicToggleButton music;
    DynamicToggleButton sound;
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

	upstate = Art.result.findRegion("MUSIC");
	downstate = Art.result.findRegion("MUSIC-DISABLED");
	hoverstate = Art.result.findRegion("MUSIC");
	music = new DynamicToggleButton(upstate, hoverstate, downstate, new Vector2(85, 85));
	music.interpolateY(music.getY(), 250, true);
	//	music.setY(music.getY() - 250);
	music.setCallback(this);
	music.state = Settings.musicEnabled ? DynamicButton.State.UP : DynamicButton.State.DOWN;

	upstate = Art.result.findRegion("SOUND");
	downstate = Art.result.findRegion("SOUND-DISABLED");
	hoverstate = Art.result.findRegion("SOUND");
	sound = new DynamicToggleButton(upstate, hoverstate, downstate, new Vector2(85 * 2 + 75, 85));
	sound.interpolateY(sound.getY(), 250, true);
	//	sound.setY(sound.getY() - 250);
	sound.setCallback(this);
	sound.state = Settings.soundEnabled ? DynamicButton.State.UP : DynamicButton.State.DOWN;

	title = new DynamicSprite(Art.menu.findRegion("TITLE"), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);

	prompt = new DynamicText(new BitmapFont(), "TAP THE SCREEN");
	prompt.setPosition(Settings.SCREEN_WIDTH / 2, 120);
	prompt.setScale(2f, 2f);
	prompt.interpolateAlpha(.5f, 150, false).repeatYoyo(Tween.INFINITY, 150).start(prompt.tweenManager);

	Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void onButtonEvent(DynamicButton button, int eventType) {
	if (eventType == DynamicButtonCallback.DOWN && button.equals(music))
	    Settings.musicEnabled = !Settings.musicEnabled;
	else if (eventType == DynamicButtonCallback.DOWN && button.equals(sound))
	    Settings.soundEnabled = !Settings.soundEnabled;
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
	else if (showPlayOptions && arcade.getBounds().contains(x, Settings.SCREEN_HEIGHT - y)) {
	    interpolateEnd();
	    arcade.tween.setCallback(this);
	} else if (showPlayOptions && endless.getBounds().contains(x, Settings.SCREEN_HEIGHT - y)) {
	    interpolateEnd();
	    endless.tween.setCallback(this);
	} else if (music.getBounds().contains(x, y))
	    music.inputDown(x, y);
	else if (sound.getBounds().contains(x, y))
	    sound.inputDown(x, y);
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
	    music.interpolateAlpha(0f, 150, true);
	    sound.interpolateAlpha(0f, 150, true);
	    prompt.visible = false;
	}
    }

    @Override
    public void onTouchUp(float x, float y, int pointer, int button) {
	x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();
	music.inputUp(x, y);
	sound.inputUp(x, y);
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
	music.render(spriteBatch);
	music.update(delta);
	sound.render(spriteBatch);
	sound.update(delta);
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
