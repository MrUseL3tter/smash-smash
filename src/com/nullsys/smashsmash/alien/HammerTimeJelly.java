package com.nullsys.smashsmash.alien;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.nullsys.smashsmash.buffeffect.BuffEffect;
import com.nullsys.smashsmash.screen.SmashSmashStageCallback;

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
	if (spawnDelay <= 0) {
	    spawnDelay = 15f + MathUtils.random(25f);
	    super.rise(delay, volume);
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
