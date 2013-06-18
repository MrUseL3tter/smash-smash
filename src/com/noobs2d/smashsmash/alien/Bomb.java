package com.noobs2d.smashsmash.alien;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.noobs2d.smashsmash.screen.SmashSmashStageCallback;

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
	super.setHostile(false);
    }

    @Override
    public void rise(float delay, float volume) {
	if (spawnDelay <= 0) {
	    spawnDelay = 5f + MathUtils.random(15f);
	    super.rise(delay, volume);
	}
    }

    @Override
    public void setHostile(boolean hostile) {
	// negate
    }

    @Override
    public void update(float delta) {
	super.update(delta);
	spawnDelay -= delta;
    }

}
