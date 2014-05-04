package com.noobs2d.smashsmash.screen;

import aurelienribon.tweenengine.BaseTween;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;
import com.noobs2d.smashsmash.Settings;
import com.noobs2d.smashsmash.User;
import com.noobs2d.smashsmash.alien.Diabolic;
import com.noobs2d.smashsmash.alien.Fluff;
import com.noobs2d.smashsmash.alien.Golem;
import com.noobs2d.smashsmash.alien.Jelly;
import com.noobs2d.smashsmash.buffeffect.BuffEffect;
import com.noobs2d.tweenengine.utils.DynamicValue;

public class FuryStageScreen extends ArcadeStageScreen {

    private static final int TIME_LIMIT = 120;

    public FuryStageScreen(Game game, boolean restart) {
	super(game, restart);
	// invokes onEvent(int,BaseTween<?>) which transitions to results screen
	elapsed = new DynamicValue(0, TIME_LIMIT, TIME_LIMIT * 1000, 0);
	elapsed.setTweenCallback(this);
	User.addBuffEffect(BuffEffect.HAMMER_TIME, TIME_LIMIT * 2);
    }

    @Override
    public int getComboMultiplier() {
	return 1;
    }

    @Override
    public String[] getTimerValues() {
	float left = TIME_LIMIT - elapsed.getValue();
	String one = "0", two = "0", three = "0", four = "0";
	if (left >= TIME_LIMIT)
	    two = "2";
	else if (left >= 60 && left < 120) {
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
	// restarts the game if the user presses left CTRL on keyboard or MENU on phone
	// DEBUG MODE only
	if (Settings.DEBUG_MODE && keycode == Keys.CONTROL_LEFT || keycode == Keys.MENU)
	    game.setScreen(new FuryStageScreen(game, true));
	return super.keyUp(keycode);
    }

    /** will be invoked due to the timer tween on the constructor */
    @Override
    public void onEvent(int type, BaseTween<?> source) {
	if (type == COMPLETE) {
	    game.setScreen(new ResultScreen(game, this));
	    setAllowSpawn(false);
	    setAliensHostile(false);
	}
    }

    @Override
    protected void checkComboTime() {
	getSession().resetCurrentCombo();
    }

    @Override
    protected void checkStreaks() {

    }

    @Override
    protected void initAliens() {
	aliens.add(new Diabolic(this));
	aliens.add(new Diabolic(this));
	aliens.add(new Diabolic(this));
	aliens.add(new Fluff(this));
	aliens.add(new Fluff(this));
	aliens.add(new Fluff(this));
	aliens.add(new Golem(this));
	aliens.add(new Golem(this));
	aliens.add(new Golem(this));
	aliens.add(new Jelly(this));
	aliens.add(new Jelly(this));
	aliens.add(new Jelly(this));
	for (int i = 0; i < aliens.size(); i++)
	    aliens.get(i).setVisible(false);
	setAliensHostile(false);
    }

    @Override
    protected void setSpawnRate() {
	if (isSpawnAllowed())
	    spawnRate = aliens.size() / 2;
    }
}
