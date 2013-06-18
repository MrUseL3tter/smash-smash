package com.noobs2d.smashsmash.alien;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.noobs2d.smashsmash.buffeffect.BuffEffect;
import com.noobs2d.smashsmash.screen.SmashSmashStageCallback;

public class HammerTimeJelly extends Jelly {

    private float spawnDelay = 0;

    public HammerTimeJelly(SmashSmashStageCallback stage) {
	super(stage);
	risingState.setColor(Color.GREEN);
	waitingState.setColor(Color.GREEN);
	attackingState.setColor(Color.GREEN);
	stunnedState.setColor(Color.GREEN);
	smashedState.setColor(Color.GREEN);
	hidingState.setColor(Color.GREEN);
	setHostile(false);
    }

    @Override
    public void rise(float delay, float volume) {
	if (spawnDelay <= 0 && state == AlienState.HIDDEN) {
	    spawnDelay = 15f + MathUtils.random(25f);
	    risingStateTime = 1 + delay / 1000;
	    waitingStateTime = 1.25f;

	    state = AlienState.RISING;

	    setVisible(true);
	    reset();
	    interpolateRising(delay);
	}
    }

    @Override
    public void smash() {
	stage.onBonusEffectTrigger(this, BuffEffect.HAMMER_TIME);
	super.smash();
    }

    @Override
    public void update(float delta) {
	super.update(delta);
	spawnDelay -= delta;
    }

}
