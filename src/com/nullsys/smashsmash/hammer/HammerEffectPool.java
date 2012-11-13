package com.nullsys.smashsmash.hammer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.nullsys.smashsmash.Particles;
import com.nullsys.smashsmash.Settings;
import com.nullsys.smashsmash.User;
import com.nullsys.smashsmash.bonuseffect.BonusEffect;

public class HammerEffectPool {

    private static Pool<HammerEffect> normal = new Pool<HammerEffect>(20) {

	@Override
	protected HammerEffect newObject() {
	    HammerEffect he = null;
	    float duration = 0;
	    switch (Settings.hammerType) {
		case Hammer.BUBBLES:
		    break;
		case Hammer.DUSK:
		    break;
		case Hammer.FIERY_SMASH:
		    duration = Particles.fierySmashNormal.getEmitters().get(0).getDuration().getLowMax() / 1000;
		    he = new HammerEffect(Particles.fierySmashNormal, new Vector2(0, 0), duration, 0);
		    break;
		case Hammer.FROST_BITE:
		    duration = Particles.frostBiteNormal.getEmitters().get(0).getDuration().getLowMax() / 1000;
		    he = new HammerEffect(Particles.frostBiteNormal, new Vector2(0, 0), duration, 0);
		    break;
		case Hammer.GOLD_DIGGER:
		    break;
		case Hammer.METAL_HEAD:
		    break;
		case Hammer.MJOLNIR:
		    break;
		case Hammer.SPACE_BLASTER:
		    break;
		case Hammer.STARRY_NIGHT:
		    break;
		case Hammer.TWILIGHT:
		    break;
		case Hammer.WOODEN_HAMMER:
		    break;
		default:
		    assert false;
		    break;
	    }
	    return he;
	}
    };

    private static Pool<HammerEffect> hammerTime = new Pool<HammerEffect>(20) {

	@Override
	protected HammerEffect newObject() {
	    HammerEffect he = null;
	    float duration = 0;
	    switch (Settings.hammerType) {
		case Hammer.BUBBLES:
		    break;
		case Hammer.DUSK:
		    break;
		case Hammer.FIERY_SMASH:
		    duration = Particles.fierySmashHammerTime.getEmitters().get(0).getDuration().getLowMax() / 1000;
		    he = new HammerEffect(Particles.fierySmashHammerTime, new Vector2(0, 0), duration, 0);
		    break;
		case Hammer.FROST_BITE:
		    duration = Particles.frostBiteHammerTime.getEmitters().get(0).getDuration().getLowMax() / 1000;
		    he = new HammerEffect(Particles.frostBiteHammerTime, new Vector2(0, 0), duration, 0);
		    break;
		case Hammer.GOLD_DIGGER:
		    break;
		case Hammer.METAL_HEAD:
		    break;
		case Hammer.MJOLNIR:
		    break;
		case Hammer.SPACE_BLASTER:
		    break;
		case Hammer.STARRY_NIGHT:
		    break;
		case Hammer.TWILIGHT:
		    break;
		case Hammer.WOODEN_HAMMER:
		    break;
		default:
		    assert false;
		    break;
	    }
	    return he;
	}
    };

    public static void clear() {
	if (User.hasEffect(BonusEffect.HAMMER_TIME))
	    hammerTime.clear();
	else
	    normal.clear();
    }

    public static void free(Array<HammerEffect> objects) {
	if (User.hasEffect(BonusEffect.HAMMER_TIME))
	    hammerTime.free(objects);
	else
	    normal.free(objects);
    }

    public static void free(HammerEffect object) {
	if (User.hasEffect(BonusEffect.HAMMER_TIME))
	    hammerTime.free(object);
	else
	    normal.free(object);
    }

    public static HammerEffect obtain() {
	if (User.hasEffect(BonusEffect.HAMMER_TIME)) {
	    HammerEffect he = hammerTime.obtain();
	    he.setPool(hammerTime);
	    return he;
	} else {
	    HammerEffect he = normal.obtain();
	    he.setPool(normal);
	    return he;
	}
    }

    private HammerEffectPool() {
	// TODO Auto-generated constructor stub
    }

}
