package com.nullsys.smashsmash.alien;

import java.util.ArrayList;

import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Bounce;

import com.noobs2d.tweenengine.utils.DynamicAnimation;
import com.noobs2d.tweenengine.utils.DynamicAnimationGroup;
import com.noobs2d.tweenengine.utils.DynamicCallback.ReturnValues;
import com.nullsys.smashsmash.Particles;
import com.nullsys.smashsmash.Sounds;
import com.nullsys.smashsmash.hammer.HammerEffect;
import com.nullsys.smashsmash.screen.SmashSmashStageCallback;

public class Sorcerer extends Alien {

    private static final int WAITING_STATE_TIME = 7;

    public Sorcerer(SmashSmashStageCallback stage) {
	super.stage = stage;
	setRegistration(DynamicRegistration.BOTTOM_CENTER);
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
	if (state == AlienState.HIDDEN) {
	    risingStateTime = 1 + delay / 1000;
	    waitingStateTime = WAITING_STATE_TIME;
	    upElapsedTime = 0;

	    reset();
	    visible = true;
	    state = AlienState.RISING;
	    spawnEffectDelay = delay / 1000;
	    spawnEffect = new HammerEffect(Particles.leafSpawn, position, 1.0f, spawnEffectDelay);

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
	if (upElapsedTime * tweenSpeed >= waitingStateTime) {
	    stage.onBonusEffectSpawn(this);
	    state = AlienState.HIDING;
	    upElapsedTime = 0;
	}
    }
}
