package com.noobs2d.smashsmash.hammer;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.noobs2d.smashsmash.Particles;
import com.noobs2d.smashsmash.Settings;
import com.noobs2d.smashsmash.User;
import com.noobs2d.smashsmash.buffeffect.BuffEffect;

public class Hammer {

    private static final int NORMAL_DIAMETER = 3;

    private static final int HAMMER_TIME_DIAMETER = 160;
    public static final int BUBBLES = 0x01;

    public static final int DUSK = 0x02;
    public static final int FIERY_SMASH = 0x03;
    public static final int FROST_BITE = 0x04;
    public static final int METAL_HEAD = 0x06;
    public static final int MJOLNIR = 0x07;
    public static final int SPACE_BLASTER = 0x08;
    public static final int STARRY_NIGHT = 0x09;
    public static final int TWILIGHT = 0x0a;
    public static final int WOODEN_HAMMER = 0x0b;

    public static final class Descriptions {

	public static final String BUBBLES = "Not really sure if a hammer";
	public static final String DUSK = "Pure darkness from within";
	public static final String FIERY_SMASH = "So hot even you can't handle it";
	public static final String FROST_BITE = "Ice, ice, baaaaaby~";
	public static final String METAL_HEAD = "Smash 'em to smithereens!";
	public static final String MJOLNIR = "Made in China";
	public static final String SPACE_BLASTER = "Chub! Chew! Bugsh! Zzub!";
	public static final String STARRY_NIGHT = "Twinkle, twinkle, crazy stars...";
	public static final String TWILIGHT = "Not as gay as the movie";
	public static final String WOODEN_HAMMER = "Just your ordinary hammer";
    }

    public static final class Prices {

	public static final int BUBBLES = 35000;
	public static final int DUSK = 25000;
	public static final int FIERY_SMASH = 15000;
	public static final int FROST_BITE = 15000;
	public static final int METAL_HEAD = 10000;
	public static final int MJOLNIR = 99999;
	public static final int SPACE_BLASTER = 35000;
	public static final int STARRY_NIGHT = 35000;
	public static final int TWILIGHT = 25000;
    }

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
	if (User.hasBuffEffect(BuffEffect.HAMMER_TIME))
	    return HAMMER_TIME_DIAMETER;
	else
	    return NORMAL_DIAMETER;
    }

    public ParticleEffect getEffect() {
	if (Settings.cheatHammerTime || User.hasBuffEffect(BuffEffect.HAMMER_TIME))
	    return hammerTimeEffect;
	else
	    return normalEffect;
    }

    public HammerEffect getEffect(float x, float y) {
	return null;
    }
}
