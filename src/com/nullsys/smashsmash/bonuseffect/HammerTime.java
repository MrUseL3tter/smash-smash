package com.nullsys.smashsmash.bonuseffect;

import com.nullsys.smashsmash.User;

public class HammerTime extends BuffEffect {

    public HammerTime() {
	type = HAMMER_TIME;
    }

    public HammerTime(float duration) {
	type = HAMMER_TIME;
	this.duration = duration;
    }

    @Override
    public void trigger() {
	active = true;
	User.bonusEffects.add(this);
	secondsElapsed = 0;
    }
}
