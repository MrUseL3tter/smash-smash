package com.nullsys.smashsmash.bonuseffect;

import com.badlogic.gdx.math.Vector2;
import com.nullsys.smashsmash.User;

public class ScoreFrenzy extends BonusEffect {

    public ScoreFrenzy(Vector2 position) {
	type = SCORE_FRENZY;
    }

    public ScoreFrenzy(Vector2 position, float duration) {
	super.duration = duration;
	type = SCORE_FRENZY;
    }

    @Override
    public void trigger() {
	active = true;
	User.bonusEffects.add(this);
	secondsElapsed = 0;
    }
}
