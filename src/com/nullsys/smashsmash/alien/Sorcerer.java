package com.nullsys.smashsmash.alien;

import java.util.ArrayList;

import com.noobs2d.tweenengine.utils.DynamicAnimation;
import com.noobs2d.tweenengine.utils.DynamicAnimationGroup;
import com.nullsys.smashsmash.Sounds;
import com.nullsys.smashsmash.stage.SmashSmashStageCallback;

public class Sorcerer extends Alien {

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
    public void rise(float delay, float volume) {
	super.rise(delay, volume);
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
