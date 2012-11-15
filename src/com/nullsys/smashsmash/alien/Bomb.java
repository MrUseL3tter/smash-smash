package com.nullsys.smashsmash.alien;

import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Bounce;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.noobs2d.tweenengine.utils.DynamicCallback.ReturnValues;
import com.nullsys.smashsmash.Settings;
import com.nullsys.smashsmash.User;
import com.nullsys.smashsmash.bonuseffect.BonusEffect;
import com.nullsys.smashsmash.screen.SmashSmashStageCallback;

public class Bomb extends Jelly {

    private float spawnDelay = 0;

    public Bomb(SmashSmashStageCallback stage) {
	super(stage);
	risingState.setColor(Color.RED);
	waitingState.setColor(Color.RED);
	attackingState.setColor(Color.RED);
	stunnedState.setColor(Color.RED);
	smashedState.setColor(Color.RED);
	hidingState.setColor(Color.RED);
	setHostile(false);
    }

    @Override
    public void rise(float delay, float volume) {
	if (spawnDelay <= 0) {
	    spawnDelay = 5f + MathUtils.random(15f);
	    super.rise(delay, volume);
	}
    }

    @Override
    public void smash() {
	stage.onAlienAttack(this);
	upElapsedTime = 0;
	reset();
	if (Settings.soundEnabled)
	    SFXsmash.play();
	hitPoints--;
	if (User.hasEffect(BonusEffect.HAMMER_TIME) || hitPoints <= 0 && state != AlienState.SMASHED) { //alien dead
	    upElapsedTime = 0;
	    hitPoints = hitPointsTotal;
	    state = AlienState.SMASHED;
	    for (int i = 0; i < smashedState.displays.size(); i++) {
		smashedState.displays.get(i).interpolateScaleXY(1f, 0f, Bounce.IN, 1000, true);
		smashedState.displays.get(i).tween.setCallback(new ReturnValues(smashedState.displays.get(i)));
		smashedState.displays.get(i).tween.setCallbackTriggers(TweenCallback.COMPLETE);
		smashedState.displays.get(i).interpolateXY(smashedState.displays.get(i).position.x, 0, Bounce.IN, 1000, true);
		smashedState.displays.get(i).tween.setCallback(new ReturnValues(smashedState.displays.get(i)));
		smashedState.displays.get(i).tween.setCallbackTriggers(TweenCallback.COMPLETE);
	    }
	} else
	    for (int i = 0; i < getStateAnimation().displays.size(); i++) {
		getStateAnimation().displays.get(i).interpolateScaleXY(1f, .75f, Linear.INOUT, 150, true);
		float x = getStateAnimation().displays.get(i).position.x;
		float y = getStateAnimation().displays.get(i).position.y * .75f;
		getStateAnimation().displays.get(i).interpolateXY(x, y, Linear.INOUT, 150, true);
		getStateAnimation().displays.get(i).interpolateScaleXY(1f, 1f, Bounce.OUT, 500, true);
		getStateAnimation().displays.get(i).tween.delay(150);
		x = getStateAnimation().displays.get(i).position.x;
		y = getStateAnimation().displays.get(i).position.y;
		getStateAnimation().displays.get(i).interpolateXY(x, y, Bounce.OUT, 500, true);
		getStateAnimation().displays.get(i).tween.delay(150);
	    }
    }

    @Override
    public void update(float delta) {
	super.update(delta);
	spawnDelay -= delta;
    }

}
