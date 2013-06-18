package com.noobs2d.smashsmash.alien;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.noobs2d.smashsmash.buffeffect.BuffEffect;
import com.noobs2d.smashsmash.screen.SmashSmashStageCallback;

public class ScoreFrenzyJelly extends Jelly {

    private float spawnDelay = 0;

    public ScoreFrenzyJelly(SmashSmashStageCallback stage) {
	super(stage);
	risingState.setColor(new Color(0f, 0f, .5f, 1f));
	waitingState.setColor(new Color(0f, 0f, .5f, 1f));
	attackingState.setColor(new Color(0f, 0f, .5f, 1f));
	stunnedState.setColor(new Color(0f, 0f, .5f, 1f));
	smashedState.setColor(new Color(0f, 0f, .5f, 1f));
	hidingState.setColor(new Color(0f, 0f, .5f, 1f));
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
	stage.onBonusEffectTrigger(this, BuffEffect.SCORE_FRENZY);
	super.smash();
    }

    @Override
    public void update(float delta) {
	super.update(delta);
	spawnDelay -= delta;
    }

}
