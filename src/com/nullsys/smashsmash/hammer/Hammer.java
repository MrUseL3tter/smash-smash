package com.nullsys.smashsmash.hammer;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.nullsys.smashsmash.Particles;
import com.nullsys.smashsmash.Settings;
import com.nullsys.smashsmash.User;
import com.nullsys.smashsmash.bonuseffect.BonusEffect;

public class Hammer {

    private static final int NORMAL_DIAMETER = 100;
    private static final int HAMMER_TIME_DIAMETER = 160;

    public static final int BUBBLES = 0x01;
    public static final int DUSK = 0x02;
    public static final int FIERY_SMASH = 0x03;
    public static final int FROST_BITE = 0x04;
    public static final int GOLD_DIGGER = 0x05;
    public static final int METAL_HEAD = 0x06;
    public static final int MJOLNIR = 0x07;
    public static final int SPACE_BLASTER = 0x08;
    public static final int STARRY_NIGHT = 0x09;
    public static final int TWILIGHT = 0x0a;
    public static final int WOODEN_HAMMER = 0x0b;

    ParticleEffect normalEffect;
    ParticleEffect hammerTimeEffect;

    public Hammer() {
	switch (Settings.hammerType) {
	    case BUBBLES:
		break;
	    case DUSK:
		break;
	    case FIERY_SMASH:
		normalEffect = Particles.fierySmashNormal;
		hammerTimeEffect = Particles.fierySmashHammerTime;
		break;
	    case FROST_BITE:
		normalEffect = Particles.frostBiteNormal;
		hammerTimeEffect = Particles.frostBiteHammerTime;
		break;
	    case GOLD_DIGGER:
		break;
	    case METAL_HEAD:
		break;
	    case MJOLNIR:
		break;
	    case SPACE_BLASTER:
		break;
	    case STARRY_NIGHT:
		break;
	    case TWILIGHT:
		break;
	    case WOODEN_HAMMER:
		break;
	    default:
		assert false;
		break;
	}
    }

    public int getDiameter() {
	if (User.hasEffect(BonusEffect.HAMMER_TIME))
	    return HAMMER_TIME_DIAMETER;
	else
	    return NORMAL_DIAMETER;
    }

    public ParticleEffect getEffect() {
	if (Settings.cheatHammerTime || User.hasEffect(BonusEffect.HAMMER_TIME))
	    return hammerTimeEffect;
	else
	    return normalEffect;
    }

    public HammerEffect getEffect(float x, float y) {
	return null;
    }
}
