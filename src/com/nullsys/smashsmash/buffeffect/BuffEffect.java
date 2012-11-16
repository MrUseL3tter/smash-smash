package com.nullsys.smashsmash.buffeffect;

import com.nullsys.smashsmash.User;

public class BuffEffect {

    protected static final int DEFAULT_DURATION = 10;
    public static final int HAMMER_TIME = 1;
    public static final int SCORE_FRENZY = 2;
    public static final int INVULNERABILITY = 3;

    protected int type;
    protected float duration = DEFAULT_DURATION;
    protected float secondsElapsed = 0;
    protected float secondsCounter = 0;
    protected boolean active = false;

    public BuffEffect(int type) {
	this.type = type;
    }

    public BuffEffect(int type, float duration) {
	this.type = type;
	this.duration = duration;
    }

    protected BuffEffect() {

    }

    public int getType() {
	return type;
    }

    public BuffEffect trigger() {
	active = true;
	//	User.buffEffects.add(this);
	secondsElapsed = 0;
	return this;
    }

    public void update(float deltaTime) {
	if (active && secondsElapsed >= duration)
	    User.buffEffects.remove(this);

	if (active && secondsCounter >= 1 && (int) secondsCounter % 1 == 0) {
	    secondsElapsed++;
	    secondsCounter = 0;
	}
	secondsCounter += deltaTime;
    }
}
