package com.noobs2d.smashsmash.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.smashsmash.Art;
import com.noobs2d.smashsmash.Fonts;
import com.noobs2d.smashsmash.Settings;
import com.noobs2d.tweenengine.utils.DynamicButton;
import com.noobs2d.tweenengine.utils.DynamicButton.DynamicButtonCallback;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;
import com.noobs2d.tweenengine.utils.DynamicToggleButton;

public class PauseScreen extends DynamicScreen implements DynamicButtonCallback, TweenCallback {

    SmashSmashStage stage;
    DynamicButton resume;
    DynamicButton retry;
    DynamicButton menu;
    DynamicText pauseLabel;
    DynamicToggleButton music;
    DynamicToggleButton sound;
    DynamicSprite blackFill;

    public PauseScreen(Game game, SmashSmashStage stage) {
	super(game, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
	this.stage = stage;
	this.stage.pause();
	this.stage.setUIVisible(false);

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.result.findRegion("PAUSE-RESUME-UP");
	downstate = Art.result.findRegion("PAUSE-RESUME-DOWN");
	hoverstate = Art.result.findRegion("PAUSE-RESUME-UP");
	resume = new DynamicButton(upstate, hoverstate, downstate, new Vector2(Settings.SCREEN_WIDTH / 2 - 200, Settings.SCREEN_HEIGHT / 2));
	resume.interpolateX(resume.getX(), 250, true);
	resume.setX(resume.getX() - Settings.SCREEN_WIDTH);
	resume.setCallback(this);

	upstate = Art.result.findRegion("PAUSE-RETRY-UP");
	downstate = Art.result.findRegion("PAUSE-RETRY-DOWN");
	hoverstate = Art.result.findRegion("PAUSE-RETRY-UP");
	retry = new DynamicButton(upstate, hoverstate, downstate, new Vector2(Settings.SCREEN_WIDTH / 2 + 200, Settings.SCREEN_HEIGHT / 2));
	retry.interpolateX(retry.getX(), 250, true);
	retry.setX(retry.getX() + Settings.SCREEN_WIDTH);
	retry.setCallback(this);

	upstate = Art.result.findRegion("PAUSE-MENU");
	downstate = Art.result.findRegion("PAUSE-MENU-DOWN");
	hoverstate = Art.result.findRegion("PAUSE-MENU");
	menu = new DynamicButton(upstate, hoverstate, downstate, new Vector2(Settings.SCREEN_WIDTH - 85, 85));
	menu.interpolateY(menu.getY(), 250, true);
	menu.setY(menu.getY() - 250);
	menu.setCallback(this);

	upstate = Art.result.findRegion("MUSIC");
	downstate = Art.result.findRegion("MUSIC-DISABLED");
	hoverstate = Art.result.findRegion("MUSIC");
	music = new DynamicToggleButton(upstate, hoverstate, downstate, new Vector2(85, 85));
	music.interpolateY(music.getY(), 250, true);
	music.setY(music.getY() - 250);
	music.setCallback(this);
	music.state = Settings.musicEnabled ? DynamicButton.State.UP : DynamicButton.State.DOWN;

	upstate = Art.result.findRegion("SOUND");
	downstate = Art.result.findRegion("SOUND-DISABLED");
	hoverstate = Art.result.findRegion("SOUND");
	sound = new DynamicToggleButton(upstate, hoverstate, downstate, new Vector2(85 * 2 + 75, 85));
	sound.interpolateY(sound.getY(), 250, true);
	sound.setY(sound.getY() - 250);
	sound.setCallback(this);
	sound.state = Settings.soundEnabled ? DynamicButton.State.UP : DynamicButton.State.DOWN;

	pauseLabel = new DynamicText(Fonts.actionJackson115, "PAUSED");
	pauseLabel.setPosition(Settings.SCREEN_WIDTH / 2, 640);
	pauseLabel.setColor(1f, 1f, 1f, 0f);
	pauseLabel.interpolateAlpha(1f, 250, true);

	blackFill = new DynamicSprite(new TextureRegion(Art.blackFill), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	blackFill.setScale(15f, 15f);
	blackFill.setColor(new Color(1f, 1f, 1f, 0f));
	blackFill.interpolateAlpha(.75f, 250, true);

    }

    @Override
    public boolean keyUp(int keycode) {
	if (keycode == Keys.BACKSPACE || keycode == Keys.ENTER || keycode == Keys.BACK) {
	    //	    game.setScreen(stage);
	    //	    stage.setUIVisible(true);
	    //	    stage.resume();
	    interpolateEnd();
	    resume.setTweenCallback(this);
	}
	return super.keyDown(keycode);
    }

    @Override
    public void onButtonEvent(DynamicButton button, int eventType) {
	if (eventType == DynamicButtonCallback.UP && button.equals(resume)) {
	    interpolateEnd();
	    resume.setTweenCallback(this);
	} else if (eventType == DynamicButtonCallback.UP && button.equals(retry)) {
	    interpolateEnd();
	    retry.setTweenCallback(this);
	} else if (eventType == DynamicButtonCallback.UP && button.equals(menu)) {
	    interpolateEnd();
	    menu.setTweenCallback(this);
	} else if (eventType == DynamicButtonCallback.DOWN && button.equals(music))
	    Settings.musicEnabled = !Settings.musicEnabled;
	else if (eventType == DynamicButtonCallback.DOWN && button.equals(sound))
	    Settings.soundEnabled = !Settings.soundEnabled;
    }

    @Override
    public void onEvent(int type, BaseTween<?> source) {
	if (type == COMPLETE)
	    if (source.equals(resume.getTween())) {
		stage.resume();
		stage.setUIVisible(true);
		game.setScreen(stage);
	    } else if (source.equals(retry.getTween())) {
		if (stage instanceof FuryStageScreen)
		    game.setScreen(new FuryStageScreen(game, true));
		else if (stage instanceof ArcadeStageScreen)
		    game.setScreen(new ArcadeStageScreen(game, true));
		else
		    game.setScreen(new EndlessStageScreen(game));
	    } else if (source.equals(menu.getTween()))
		game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void onTouchDown(float x, float y, int pointer, int button) {
	x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();
	resume.inputDown(x, y);
	retry.inputDown(x, y);
	menu.inputDown(x, y);
	music.inputDown(x, y);
	sound.inputDown(x, y);
    }

    @Override
    public void onTouchUp(float x, float y, int pointer, int button) {
	x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();
	resume.inputUp(x, y);
	retry.inputUp(x, y);
	menu.inputUp(x, y);
	music.inputUp(x, y);
	sound.inputUp(x, y);
    }

    @Override
    public void render(float delta) {
	super.render(delta);
	stage.render(delta);

	batch.begin();
	blackFill.render(batch);
	blackFill.update(delta);
	resume.render(batch);
	resume.update(delta);
	retry.render(batch);
	retry.update(delta);
	menu.render(batch);
	menu.update(delta);
	music.render(batch);
	music.update(delta);
	sound.render(batch);
	sound.update(delta);
	pauseLabel.render(batch);
	pauseLabel.update(delta);
	batch.end();
    }

    private void interpolateEnd() {
	resume.interpolateAlpha(0f, 150, true);
	retry.interpolateAlpha(0f, 150, true);
	menu.interpolateAlpha(0f, 150, true);
	music.interpolateAlpha(0f, 150, true);
	sound.interpolateAlpha(0f, 150, true);
	blackFill.interpolateAlpha(0f, 150, true);
	pauseLabel.interpolateAlpha(0f, 150, true);
    }

}
