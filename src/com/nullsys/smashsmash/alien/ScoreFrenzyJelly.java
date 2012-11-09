package com.nullsys.smashsmash.alien;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.nullsys.smashsmash.bonuseffect.BonusEffect;
import com.nullsys.smashsmash.bonuseffect.ScoreFrenzy;
import com.nullsys.smashsmash.screen.SmashSmashStageCallback;

public class ScoreFrenzyJelly extends Jelly {

    private float spawnDelay = 0;

    public ScoreFrenzyJelly(SmashSmashStageCallback stage) {
	super(stage);
	risingState.setColor(Color.BLUE);
	waitingState.setColor(Color.BLUE);
	attackingState.setColor(Color.BLUE);
	stunnedState.setColor(Color.BLUE);
	smashedState.setColor(Color.BLUE);
	hidingState.setColor(Color.BLUE);
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
	new ScoreFrenzy(new Vector2(0, 0), 10).trigger();
	stage.onBonusEffectTrigger(BonusEffect.SCORE_FRENZY);
	super.smash();
    }

    @Override
    public void update(float delta) {
	super.update(delta);
	spawnDelay -= delta;
    }

}
