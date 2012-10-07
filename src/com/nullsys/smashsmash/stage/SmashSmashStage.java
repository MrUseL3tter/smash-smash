package com.nullsys.smashsmash.stage;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.tweenengine.utils.DynamicAnimation;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.nullsys.smashsmash.Session;
import com.nullsys.smashsmash.alien.Alien;
import com.nullsys.smashsmash.bonuseffect.BonusEffect;
import com.nullsys.smashsmash.hammer.HammerEffect;

public abstract class SmashSmashStage extends DynamicScreen {

    /** The maximum duration before the combo expires. */
    public static final int COMBO_MAX_DURATION = 3;

    public ArrayList<BonusEffect> bonusEffects = new ArrayList<BonusEffect>();
    public ArrayList<HammerEffect> hammerEffects = new ArrayList<HammerEffect>();
    public ArrayList<DynamicAnimation> coins = new ArrayList<DynamicAnimation>();

    public final Alien[] aliens = new Alien[2];
    public final Vector2[] gridPoints = new Vector2[16];
    public final boolean[] pointers = new boolean[4];

    protected HeadsUpDisplay hud;
    protected Session session;

    public int alienAppearanceDelay = 0;
    public int alienAppearanceRate = 0;

    public SmashSmashStage(Game game) {
	super(game);
	session = new Session();
    }

    public abstract void onAlienAttack(Alien alien);

    public abstract void onAlienSmashed(Alien alien);

    protected abstract boolean inputToAliens(float x, float y, int pointer);

    protected abstract boolean inputToCoins(float x, float y, int pointer);
}
