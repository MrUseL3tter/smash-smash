package com.nullsys.smashsmash.bonuseffect;

import com.nullsys.smashsmash.User;

public abstract class BonusEffect {

    protected static final int DEFAULT_DURATION = 1;
    public static final int HAMMER_TIME = 1;
    public static final int SCORE_FRENZY = 2;
    public static final int INVULNERABILITY = 3;

    protected int type;
    protected float duration = DEFAULT_DURATION;
    protected float secondsElapsed = 0;
    protected float secondsCounter = 0;
    protected boolean active = false;

    public int getType() {
	return type;
    }

    public abstract void trigger();

    public void update(float deltaTime) {
	if (active && secondsElapsed >= duration)
	    User.bonusEffects.remove(this);

	if (active && secondsCounter >= 1 && (int) secondsCounter % 1 == 0) {
	    secondsElapsed++;
	    secondsCounter = 0;
	}
	secondsCounter += deltaTime;
    }
}
