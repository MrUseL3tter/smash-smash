package com.nullsys.smashsmash.alien;

import java.util.ArrayList;

import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Bounce;

import com.badlogic.gdx.math.MathUtils;
import com.noobs2d.tweenengine.utils.DynamicAnimation;
import com.noobs2d.tweenengine.utils.DynamicAnimationGroup;
import com.noobs2d.tweenengine.utils.DynamicCallback.ReturnValues;
import com.noobs2d.tweenengine.utils.DynamicDisplay.DynamicRegistration;
import com.nullsys.smashsmash.Sounds;
import com.nullsys.smashsmash.User;
import com.nullsys.smashsmash.buffeffect.BuffEffect;
import com.nullsys.smashsmash.screen.SmashSmashStageCallback;

public class Sorcerer extends Alien {

    private static final int WAITING_STATE_TIME = 7;
    private float spawnDelay = 0;

    public Sorcerer(SmashSmashStageCallback stage) {
	super.stage = stage;
	SFXspawn = Sounds.jellySpawn;
	SFXsmash = Sounds.jellySmash;
	initAttackingState();
	initHidingState();
	initRisingState();
	initSmashedState();
	initStunnedState();
	initWaitingState();
	waitingStateTime = 500;
    }

    @Override
    public int getScore() {
	return 0;
    }

    @Override
    public void rise(float delay, float volume) {
	boolean sorcererShouldAppear = !User.hasBuffEffect(BuffEffect.HAMMER_TIME) && !User.hasBuffEffect(BuffEffect.INVULNERABILITY);
	sorcererShouldAppear = spawnDelay <= 0 && sorcererShouldAppear && !User.hasBuffEffect(BuffEffect.SCORE_FRENZY);
	if (sorcererShouldAppear && state == AlienState.HIDDEN) {
	    spawnDelay = 15f + MathUtils.random(25f);
	    risingStateTime = 1 + delay / 1000;
	    waitingStateTime = WAITING_STATE_TIME;
	    upElapsedTime = 0;

	    reset();
	    setVisible(true);
	    state = AlienState.RISING;

	    for (int i = 0; i < risingState.displays.size(); i++) {
		risingState.displays.get(i).interpolateXY(risingState.displays.get(i).position.x, risingState.displays.get(i).position.y, Bounce.OUT, 1000, true);
		risingState.displays.get(i).tween.setCallback(new ReturnValues(risingState.displays.get(i)));
		risingState.displays.get(i).tween.setCallbackTriggers(TweenCallback.COMPLETE);
		risingState.displays.get(i).tween.delay(delay);
		risingState.displays.get(i).interpolateScaleXY(1f, 1f, Bounce.OUT, 1000, true);
		risingState.displays.get(i).tween.setCallback(new ReturnValues(risingState.displays.get(i)));
		risingState.displays.get(i).tween.setCallbackTriggers(TweenCallback.COMPLETE);
		risingState.displays.get(i).tween.delay(delay);
		risingState.displays.get(i).position.set(0, 0);
		risingState.displays.get(i).scale.y = 0f;
	    }
	    //	    SFXspawn.play(volume);
	}
    }

    @Override
    public void update(float delta) {
	super.update(delta);
	spawnDelay -= delta;
    }

    private void initAttackingState() {
	ArrayList<DynamicAnimation> list = new ArrayList<DynamicAnimation>();
	list.add(new DynamicAnimation(AliensArt.sorcererAttack));
	attackingState = new DynamicAnimationGroup(list);
	attackingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initHidingState() {
	ArrayList<DynamicAnimation> list = new ArrayList<DynamicAnimation>();
	list.add(new DynamicAnimation(AliensArt.sorcererHiding));
	hidingState = new DynamicAnimationGroup(list);
	hidingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initRisingState() {
	ArrayList<DynamicAnimation> list = new ArrayList<DynamicAnimation>();
	list.add(new DynamicAnimation(AliensArt.sorcererRising));
	risingState = new DynamicAnimationGroup(list);
	risingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initSmashedState() {
	ArrayList<DynamicAnimation> list = new ArrayList<DynamicAnimation>();
	list.add(new DynamicAnimation(AliensArt.sorcererSmashed));
	smashedState = new DynamicAnimationGroup(list);
	smashedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initStunnedState() {
	ArrayList<DynamicAnimation> list = new ArrayList<DynamicAnimation>();
	list.add(new DynamicAnimation(AliensArt.sorcererStunned));
	stunnedState = new DynamicAnimationGroup(list);
	stunnedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initWaitingState() {
	ArrayList<DynamicAnimation> list = new ArrayList<DynamicAnimation>();
	list.add(new DynamicAnimation(AliensArt.sorcererWaiting));
	waitingState = new DynamicAnimationGroup(list);
	waitingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void updateWaiting(float deltaTime) {
	waitingState.update(deltaTime);
	if (upElapsedTime >= waitingStateTime) {
	    stage.onBonusEffectTrigger(this, BuffEffect.HAMMER_TIME, BuffEffect.INVULNERABILITY, BuffEffect.SCORE_FRENZY);
	    state = AlienState.HIDING;
	    upElapsedTime = 0;
	}
    }
}
