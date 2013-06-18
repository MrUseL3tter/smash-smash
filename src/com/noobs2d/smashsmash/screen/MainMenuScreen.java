package com.noobs2d.smashsmash.screen;

import java.util.ArrayList;

import android.app.Activity;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.smashsmash.Art;
import com.noobs2d.smashsmash.Settings;
import com.noobs2d.tweenengine.utils.DynamicButton;
import com.noobs2d.tweenengine.utils.DynamicButton.DynamicButtonCallback;
import com.noobs2d.tweenengine.utils.DynamicDisplay.DynamicRegistration;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;
import com.noobs2d.tweenengine.utils.DynamicToggleButton;

public class MainMenuScreen extends DynamicScreen implements DynamicButtonCallback, TweenCallback {

    private ArrayList<DynamicButton> buttons = new ArrayList<DynamicButton>();
    private DynamicButton stats;
    private DynamicButton hammers;
    private DynamicButton arcade;
    private DynamicButton endless;

    private DynamicButton fury;

    private DynamicButton back;
    private DynamicToggleButton music;
    private DynamicToggleButton sound;
    private DynamicSprite title;
    private DynamicSprite selectMode;
    private DynamicText prompt;
    boolean showPlayOptions;

    public MainMenuScreen(Game game) {
	super(game, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
	TextureRegion upstate, hoverstate, downstate;
	upstate = Art.menu.findRegion("STATS-UPSTATE");
	hoverstate = Art.menu.findRegion("STATS-UPSTATE");
	downstate = Art.menu.findRegion("STATS-DOWNSTATE");
	stats = new DynamicButton(upstate, hoverstate, downstate, new Vector2(1255, 25));
	stats.setRegistration(DynamicRegistration.BOTTOM_RIGHT);
	stats.setName("STATS");
	stats.setCallback(this);
	buttons.add(stats);

	upstate = Art.menu.findRegion("HAMMERS-UPSTATE");
	hoverstate = Art.menu.findRegion("HAMMERS-UPSTATE");
	downstate = Art.menu.findRegion("HAMMERS-DOWNSTATE");
	hammers = new DynamicButton(upstate, hoverstate, downstate, new Vector2(stats.getX() - stats.getWidth() - 25, 25));
	hammers.setRegistration(DynamicRegistration.BOTTOM_RIGHT);
	hammers.setName("HAMMERS");
	hammers.setCallback(this);
	buttons.add(hammers);

	upstate = Art.modes.findRegion("ARCADE-UPSTATE");
	hoverstate = Art.modes.findRegion("ARCADE-UPSTATE");
	downstate = Art.modes.findRegion("ARCADE-DOWNSTATE");
	arcade = new DynamicButton(upstate, hoverstate, downstate, new Vector2(25, 409));
	arcade.setRegistration(DynamicRegistration.CENTER_LEFT);
	arcade.setEnabled(false);
	arcade.setCallback(this);
	arcade.setName("ARCADE");
	arcade.setAlpha(0f);
	buttons.add(arcade);

	upstate = Art.modes.findRegion("ENDLESS-UPSTATE");
	hoverstate = Art.modes.findRegion("ENDLESS-UPSTATE");
	downstate = Art.modes.findRegion("ENDLESS-DOWNSTATE");
	endless = new DynamicButton(upstate, hoverstate, downstate, new Vector2(Settings.SCREEN_WIDTH / 2, 409));
	endless.setEnabled(false);
	endless.setCallback(this);
	endless.setName("ENDLESS");
	endless.setAlpha(0f);
	buttons.add(endless);

	upstate = Art.modes.findRegion("FURY-UPSTATE");
	hoverstate = Art.modes.findRegion("FURY-UPSTATE");
	downstate = Art.modes.findRegion("FURY-DOWNSTATE");
	fury = new DynamicButton(upstate, hoverstate, downstate, new Vector2(1255, 409));
	fury.setRegistration(DynamicRegistration.CENTER_RIGHT);
	fury.setName("FURY");
	fury.setEnabled(false);
	fury.setCallback(this);
	fury.setAlpha(0f);
	buttons.add(fury);

	upstate = Art.menu.findRegion("BACK-UPSTATE");
	downstate = Art.menu.findRegion("BACK-DOWNSTATE");
	hoverstate = Art.menu.findRegion("BACK-UPSTATE");
	back = new DynamicButton(upstate, hoverstate, downstate, 25, 25);
	back.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	back.setName("BACK");
	back.setEnabled(false);
	back.setCallback(this);
	back.setAlpha(0f);
	buttons.add(back);

	upstate = Art.menu.findRegion("SOUND-UPSTATE");
	downstate = Art.menu.findRegion("SOUND-DOWNSTATE");
	hoverstate = Art.menu.findRegion("SOUND-UPSTATE");
	sound = new DynamicToggleButton(upstate, hoverstate, downstate, new Vector2(25f, 25f));
	sound.interpolateY(sound.getY(), 250, true);
	sound.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	sound.setCallback(this);
	sound.setName("SOUND");
	sound.state = Settings.soundEnabled ? DynamicButton.State.UP : DynamicButton.State.DOWN;
	buttons.add(sound);

	upstate = Art.menu.findRegion("MUSIC-UPSTATE");
	downstate = Art.menu.findRegion("MUSIC-DOWNSTATE");
	hoverstate = Art.menu.findRegion("MUSIC-UPSTATE");
	music = new DynamicToggleButton(upstate, hoverstate, downstate, new Vector2(50 + sound.getWidth(), 25));
	music.interpolateY(music.getY(), 250, true);
	music.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	music.setCallback(this);
	music.setName("MUSIC");
	music.state = Settings.musicEnabled ? DynamicButton.State.UP : DynamicButton.State.DOWN;
	buttons.add(music);

	title = new DynamicSprite(Art.menu.findRegion("TITLE"), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	selectMode = new DynamicSprite(Art.modes.findRegion("SELECT-MODE"), Settings.SCREEN_WIDTH / 2, 775);
	selectMode.setAlpha(0f);
	selectMode.setRegistration(DynamicRegistration.TOP_CENTER);

	prompt = new DynamicText(new BitmapFont(), "TAP THE SCREEN");
	prompt.setPosition(Settings.SCREEN_WIDTH / 2, 120);
	prompt.setScale(2f, 2f);
	prompt.interpolateAlpha(.5f, 150, false).repeatYoyo(Tween.INFINITY, 150).start(prompt.getTweenManager());

	Gdx.input.setCatchBackKey(true);
    }

    @Override
    public boolean keyUp(int keycode) {
	if (keycode == Keys.BACK && Gdx.app.getType() == ApplicationType.Android)
	    ((Activity) Gdx.app).showDialog(1);
	return super.keyUp(keycode);
    }

    @Override
    public void onButtonEvent(DynamicButton button, int eventType) {
	if (eventType == UP)
	    if (button.getName().equals("STATS")) {

	    } else if (button.getName().equals("HAMMERS"))
		game.setScreen(new HammerSelectionScreen(game));
	    else if (button.getName().equals("ARCADE")) {
		interpolateEnd();
		arcade.setTweenCallback(this);
	    } else if (button.getName().equals("ENDLESS")) {
		interpolateEnd();
		endless.setTweenCallback(this);
	    } else if (button.getName().equals("FURY")) {
		interpolateEnd();
		fury.setTweenCallback(this);
	    } else if (button.getName().equals("MUSIC"))
		Settings.musicEnabled = !Settings.musicEnabled;
	    else if (button.getName().equals("SOUND"))
		Settings.soundEnabled = !Settings.soundEnabled;
	    else if (button.getName().equals(back.getName()))
		interpolateShow(false);

    }

    @Override
    public void onEvent(int type, BaseTween<?> source) {
	if (type == COMPLETE && source.equals(arcade.getTween()))
	    game.setScreen(new ArcadeStageScreen(game, false));
	else if (type == COMPLETE && source.equals(endless.getTween()))
	    game.setScreen(new EndlessStageScreen(game));
	else if (type == COMPLETE && source.equals(fury.getTween()))
	    game.setScreen(new FuryStageScreen(game, false));

    }

    @Override
    public void onTouchDown(float x, float y, int pointer, int button) {
	x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();

	boolean hit = false;
	for (int i = 0; i < buttons.size(); i++)
	    if (buttons.get(i).inputDown(x, y)) {
		hit = true;
		break;
	    }

	if (!showPlayOptions && !hit)
	    interpolateShow(true);

	//	if (!showPlayOptions && stats.getBounds().contains(x, y))
	//	    // TODO show stats screen
	//	    ;
	//	else if (!showPlayOptions && hammers.getBounds().contains(x, y))
	//	    // TODO show hammers screen
	//	    ;
	//	else if (showPlayOptions && arcade.getBounds().contains(x, y)) {
	//	    interpolateEnd();
	//	    arcade.setTweenCallback(this);
	//	} else if (showPlayOptions && endless.getBounds().contains(x, y)) {
	//	    interpolateEnd();
	//	    endless.setTweenCallback(this);
	//	} else if (showPlayOptions && fury.getBounds().contains(x, y)) {
	//	    interpolateEnd();
	//	    fury.setTweenCallback(this);
	//	} else if (music.getBounds().contains(x, y))
	//	    music.inputDown(x, y);
	//	else if (sound.getBounds().contains(x, y))
	//	    sound.inputDown(x, y);
	//	else if (!showPlayOptions) {
	//	    showPlayOptions = true;
	//	    arcade.setVisible(true);
	//	    arcade.setScale(0f, 0f);
	//	    arcade.interpolateScaleXY(1f, 1f, Back.OUT, 250, true);
	//	    endless.setVisible(true);
	//	    endless.setScale(0f, 0f);
	//	    endless.interpolateScaleXY(1f, 1f, Back.OUT, 250, true).delay(100);
	//	    fury.setVisible(true);
	//	    fury.setScale(0f, 0f);
	//	    fury.interpolateScaleXY(1f, 1f, Back.OUT, 250, true).delay(150);
	//	    title.interpolateAlpha(0f, 100, true);
	//	    stats.interpolateY(stats.getY() - 200, 100, true);
	//	    hammers.interpolateY(hammers.getY() - 200, 100, true);
	//	    music.interpolateAlpha(0f, 150, true);
	//	    sound.interpolateAlpha(0f, 150, true);
	//	    prompt.setVisible(false);
	//	}
    }

    @Override
    public void onTouchUp(float x, float y, int pointer, int button) {
	x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();
	for (int i = 0; i < buttons.size(); i++)
	    if (buttons.get(i).inputUp(x, y))
		break;
    }

    @Override
    public void render(float delta) {
	super.render(delta);
	batch.begin();
	batch.draw(Art.lawnBackground1, 0, -(1024 - 800));
	batch.draw(Art.lawnBackground2, 1280 - 256, -(1024 - 800));
	stats.render(batch);
	stats.update(delta);
	hammers.render(batch);
	hammers.update(delta);
	arcade.render(batch);
	arcade.update(delta);
	endless.render(batch);
	endless.update(delta);
	fury.render(batch);
	fury.update(delta);
	back.render(batch);
	back.update(delta);
	music.render(batch);
	music.update(delta);
	sound.render(batch);
	sound.update(delta);
	title.render(batch);
	title.update(delta);
	selectMode.render(batch);
	selectMode.update(delta);
	prompt.render(batch);
	prompt.update(delta);
	batch.end();
	batch.setColor(1f, 1f, 1f, 1f);
    }

    private void interpolateEnd() {
	interpolateKill();
	arcade.interpolateAlpha(0f, 250, true);
	endless.interpolateAlpha(0f, 250, true);
	fury.interpolateAlpha(0f, 250, true);
	arcade.interpolateAlpha(0f, 100, true);
	endless.interpolateAlpha(0f, 100, true);
	fury.interpolateAlpha(0f, 100, true);
    }

    private void interpolateKill() {
	stats.getTweenManager().update(1000);
	hammers.getTweenManager().update(1000);
	arcade.getTweenManager().update(1000);
	endless.getTweenManager().update(1000);
	fury.getTweenManager().update(1000);
	back.getTweenManager().update(1000);
	music.getTweenManager().update(1000);
	sound.getTweenManager().update(1000);
	title.getTweenManager().update(1000);
	selectMode.getTweenManager().update(1000);
    }

    private void interpolateShow(boolean show) {
	interpolateKill();
	if (show) {
	    showPlayOptions = true;
	    arcade.setEnabled(true);
	    arcade.setAlpha(1f);
	    arcade.interpolateX(arcade.getX(), 250, true);
	    arcade.setX(arcade.getX() + Settings.SCREEN_WIDTH);
	    back.setEnabled(true);
	    back.setAlpha(1f);
	    back.interpolateX(back.getX(), 250, true);
	    back.setX(back.getX() - 200);
	    back.setEnabled(true);
	    endless.setEnabled(true);
	    endless.setAlpha(1f);
	    endless.interpolateX(endless.getX(), 250, true).delay(100);
	    endless.setX(endless.getX() + Settings.SCREEN_WIDTH);
	    fury.setEnabled(true);
	    fury.setAlpha(1f);
	    fury.interpolateX(fury.getX(), 250, true).delay(150);
	    fury.setX(fury.getX() + Settings.SCREEN_WIDTH);
	    title.interpolateAlpha(0f, 100, true);
	    selectMode.setAlpha(1f);
	    selectMode.interpolateAlpha(1f, 100, true);
	    stats.interpolateAlpha(0f, 100, true);
	    hammers.interpolateAlpha(0f, 100, true);
	    music.interpolateAlpha(0f, 150, true);
	    sound.interpolateAlpha(0f, 150, true);
	    prompt.setVisible(false);
	} else {
	    showPlayOptions = false;
	    arcade.interpolateAlpha(0f, 250, true);
	    arcade.setEnabled(false);
	    back.interpolateAlpha(0f, 250, true);
	    back.setEnabled(false);
	    endless.interpolateAlpha(0f, 250, true);
	    endless.setEnabled(false);
	    fury.interpolateAlpha(0f, 250, true);
	    fury.setEnabled(false);
	    title.interpolateAlpha(1f, 250, true);
	    selectMode.interpolateAlpha(0f, 250, true);
	    stats.interpolateAlpha(1f, 150, true);
	    hammers.interpolateAlpha(1f, 150, true);
	    music.interpolateAlpha(1f, 150, true);
	    sound.interpolateAlpha(1f, 150, true);
	    prompt.setVisible(true);
	}
    }

}
