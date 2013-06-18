package com.noobs2d.smashsmash.screen;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.noobs2d.smashsmash.Art;
import com.noobs2d.smashsmash.Fonts;
import com.noobs2d.smashsmash.Settings;
import com.noobs2d.tweenengine.utils.DynamicButton;
import com.noobs2d.tweenengine.utils.DynamicButton.DynamicButtonCallback;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;

public class PromptScreen extends DynamicScreen implements DynamicButtonCallback, TweenCallback {

    public static final int OK = 0;

    public static final int YES_NO = 1;

    public interface PromptScreenCallback {

	public void onNoSelected();

	public void onYesSelected();
    }

    private final PromptScreenCallback callback;
    private final ArrayList<DynamicButton> buttons;
    private final DynamicSprite pane;
    private final DynamicText text;
    private final DynamicScreen previousScreen;

    private final int type;

    public PromptScreen(Game game, PromptScreenCallback callback, DynamicScreen previousScreen, int type, String message) {
	super(game, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
	if (callback != null)
	    this.callback = callback;
	else
	    this.callback = null;

	previousScreen.pause();
	this.previousScreen = previousScreen;
	this.type = type;

	buttons = new ArrayList<DynamicButton>();
	pane = new DynamicSprite(Art.menu.findRegion("PROMPT-PANE"), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	text = new DynamicText(Fonts.bdCartoonShoutx50GrayStroke, message);
	text.setPosition(Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2 + 50);
	text.setAlpha(0f);

	TextureRegion upstate, downstate, hoverstate;

	if (type == OK) {
	    upstate = Art.menu.findRegion("OK-UPSTATE");
	    downstate = Art.menu.findRegion("OK-DOWNSTATE");
	    hoverstate = Art.menu.findRegion("OK-UPSTATE");
	    DynamicButton ok = new DynamicButton(upstate, hoverstate, downstate, Settings.SCREEN_WIDTH / 2, 267);
	    ok.setName("OK");
	    ok.setCallback(this);
	    buttons.add(ok);
	} else {
	    upstate = Art.menu.findRegion("YES-UPSTATE");
	    downstate = Art.menu.findRegion("YES-DOWNSTATE");
	    hoverstate = Art.menu.findRegion("YES-UPSTATE");
	    DynamicButton yes = new DynamicButton(upstate, hoverstate, downstate, 516, 267);
	    yes.setName("YES");
	    yes.setCallback(this);

	    upstate = Art.menu.findRegion("OK-UPSTATE");
	    downstate = Art.menu.findRegion("OK-DOWNSTATE");
	    hoverstate = Art.menu.findRegion("OK-UPSTATE");
	    DynamicButton no = new DynamicButton(upstate, hoverstate, downstate, 763, 267);
	    no.setName("NO");
	    no.setCallback(this);
	    buttons.add(yes);
	    buttons.add(no);
	}

	Gdx.input.setCatchBackKey(true);
	interpolateBegin();
    }

    public int getType() {
	return type;
    }

    @Override
    public boolean keyUp(int keycode) {
	if (keycode == Keys.BACK)
	    callback.onNoSelected();
	return super.keyUp(keycode);
    }

    @Override
    public void onButtonEvent(DynamicButton button, int eventType) {
	if (eventType == DynamicButtonCallback.UP)
	    if (button.getName().equals("OK")) {
		previousScreen.resume();
		game.setScreen(previousScreen);
	    } else if (button.getName().equals("YES"))
		callback.onYesSelected();
	    else if (button.getName().equals("NO"))
		callback.onNoSelected();
    }

    @Override
    public void onEvent(int type, BaseTween<?> source) {
	if (type == COMPLETE) {

	}
    }

    @Override
    public void onTouchDown(float x, float y, int pointer, int button) {
	x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();

	for (int i = 0; i < buttons.size(); i++)
	    buttons.get(i).inputDown(x, y);
	super.onTouchDown(x, y, pointer, button);
    }

    @Override
    public void onTouchUp(float x, float y, int pointer, int button) {
	x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();
	for (int i = 0; i < buttons.size(); i++)
	    buttons.get(i).inputUp(x, y);
	super.onTouchUp(x, y, pointer, button);
    }

    @Override
    public void render(float delta) {
	super.render(delta);

	previousScreen.render(delta);

	batch.begin();
	pane.render(batch);
	text.render(batch);

	for (int i = 0; i < buttons.size(); i++) {
	    buttons.get(i).render(batch);
	    buttons.get(i).update(delta);
	}

	pane.update(delta);
	text.update(delta);
	batch.end();
    }

    private void interpolateBegin() {
	pane.setAlpha(0f);
	pane.interpolateAlpha(1f, Linear.INOUT, 250, true);
	text.setAlpha(0f);
	text.interpolateAlpha(1f, Linear.INOUT, 250, true);

	for (int i = 0; i < buttons.size(); i++) {
	    buttons.get(i).setAlpha(0f);
	    buttons.get(i).interpolateAlpha(1f, 250, true);
	}
    }

}
