package com.nullsys.smashsmash.stage;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.tweenengine.utils.DynamicAnimation;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.nullsys.smashsmash.Session;
import com.nullsys.smashsmash.Settings;
import com.nullsys.smashsmash.alien.Alien;
import com.nullsys.smashsmash.bonuseffect.BonusEffect;
import com.nullsys.smashsmash.hammer.HammerEffect;

/**
 * @author MrUseL3tter
 */
public abstract class SmashSmashStage extends DynamicScreen {

    /** maximum duration before the combo expires. */
    public static final int COMBO_MAX_DURATION = 3;
    /** seconds before being able to smash again after being puked on */
    public static final int RECOVERY_DISABILITY_DURATION = 10;

    public ArrayList<BonusEffect> bonusEffects = new ArrayList<BonusEffect>();
    public ArrayList<HammerEffect> hammerEffects = new ArrayList<HammerEffect>();
    public ArrayList<DynamicAnimation> coins = new ArrayList<DynamicAnimation>();
    public ArrayList<DynamicSprite> pukes = new ArrayList<DynamicSprite>();

    public final Alien[] aliens = new Alien[17];
    public final Vector2[] gridPoints = new Vector2[16];
    public final boolean[] pointers = new boolean[4];

    protected HeadsUpDisplay hud;
    protected Session session;

    /**
     * becomes RECOVERY_DISABILITY_DURATION and continuously decremented when an alien attacks the
     * player successfully. player can only smash when recoverDelay is below 1.
     */
    public float recoveryDelay = 0;
    public int alienAppearanceDelay = 0;
    public int alienAppearanceRate = 0;

    public SmashSmashStage(Game game) {
	super(game, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
	session = new Session();
    }

    public boolean aliensMayAttack() {
	return recoveryDelay <= RECOVERY_DISABILITY_DURATION / 2;
    }

    public abstract void onAlienAttack(Alien alien);

    public abstract void onAlienSmashed(Alien alien);

    @Override
    public void onTouchMove(float x, float y) {
	super.onTouchMove(x, y);
	recoveryDelay -= Gdx.graphics.getDeltaTime();
	for (int i = 0; i < pukes.size(); i++)
	    pukes.get(i).tweenSpeed += .025f;
    }

    protected abstract boolean inputToAliens(float x, float y, int pointer);

    protected abstract boolean inputToCoins(float x, float y, int pointer);
}
