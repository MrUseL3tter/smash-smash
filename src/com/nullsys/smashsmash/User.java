package com.nullsys.smashsmash;

import java.util.ArrayList;

import com.nullsys.smashsmash.bonuseffect.BuffEffect;
import com.nullsys.smashsmash.hammer.Hammer;

public class User {

    public static int gold = 0;

    public static Hammer hammer;

    public static ArrayList<BuffEffect> bonusEffects = new ArrayList<BuffEffect>();

    public static boolean hasEffect(int itemEffectType) {
	for (int itemEffectIndex = 0; itemEffectIndex < bonusEffects.size(); itemEffectIndex++)
	    if (bonusEffects.get(itemEffectIndex).getType() == itemEffectType)
		return true;

	return false;
    }

    public static void init() {
	hammer = new Hammer();
	bonusEffects = new ArrayList<BuffEffect>();
    }
}
