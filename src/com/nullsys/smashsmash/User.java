package com.nullsys.smashsmash;

import java.util.ArrayList;

import com.nullsys.smashsmash.buffeffect.BuffEffect;
import com.nullsys.smashsmash.hammer.Hammer;

public class User {

    public static int gold = 0;

    public static Hammer hammer;

    public static ArrayList<BuffEffect> buffEffects = new ArrayList<BuffEffect>();

    public static void addBuffEffect(int buffEffect) {
	buffEffects.add(new BuffEffect(buffEffect).trigger());
    }

    public static void addBuffEffect(int buffEffect, float duration) {
	buffEffects.add(new BuffEffect(buffEffect, duration).trigger());
    }

    public static boolean hasBuffEffect(int itemEffectType) {
	for (int itemEffectIndex = 0; itemEffectIndex < buffEffects.size(); itemEffectIndex++)
	    if (buffEffects.get(itemEffectIndex).getType() == itemEffectType)
		return true;

	return false;
    }

    public static void init() {
	hammer = new Hammer();
	buffEffects = new ArrayList<BuffEffect>();
    }
}
