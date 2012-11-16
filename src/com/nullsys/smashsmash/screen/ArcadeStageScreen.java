package com.nullsys.smashsmash.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;
import com.noobs2d.tweenengine.utils.DynamicValue;

public class ArcadeStageScreen extends SmashSmashStage implements TweenCallback {

    private static final int TIME_LIMIT = 90;
    protected DynamicValue elapsed;

    public ArcadeStageScreen(Game game) {
	super(game);
	elapsed = new DynamicValue(0, TIME_LIMIT, TIME_LIMIT * 1000, 0);
	elapsed.tween.setCallback(this);
    }

    public String[] getTimerValues() {
	float left = TIME_LIMIT - elapsed.value;
	String one = "0", two = "0", three = "0", four = "0";
	if (left >= 60) {
	    two = "1";
	    three = left - 60 >= 10 ? Float.toString(left - 60).charAt(0) + "" : "0";
	    four = left - 60 >= 10 ? Float.toString(left - 60).charAt(1) + "" : Float.toString(left - 60).charAt(0) + "";
	} else {
	    two = "0";
	    three = left >= 10 ? Float.toString(left).charAt(0) + "" : "0";
	    four = left >= 10 ? Float.toString(left).charAt(1) + "" : Float.toString(left).charAt(0) + "";
	}
	return new String[] { one, two, ":", three, four };
    }

    @Override
    public boolean keyUp(int keycode) {
	if (keycode == Keys.CONTROL_LEFT || keycode == Keys.MENU)
	    game.setScreen(new ArcadeStageScreen(game));
	return super.keyUp(keycode);
    }

    @Override
    public void onEvent(int type, BaseTween<?> source) {
	if (type == COMPLETE) {
	    game.setScreen(new ResultScreen(game, this));
	    setAliensHostile(false);
	}
    }

    @Override
    public void pause() {
	super.pause();
	elapsed.tweenManager.pause();
    }

    @Override
    public void render(float delta) {
	super.render(delta);
	elapsed.update(isPaused() ? 0 : delta);
    }

    @Override
    public void resume() {
	super.resume();
	elapsed.tweenManager.resume();
    }
}
