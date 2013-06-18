package com.noobs2d.smashsmash.alien;

import java.util.ArrayList;

import com.noobs2d.smashsmash.screen.SmashSmashStageCallback;
import com.noobs2d.tweenengine.utils.DynamicAnimation;
import com.noobs2d.tweenengine.utils.DynamicDisplay;
import com.noobs2d.tweenengine.utils.DynamicDisplay.DynamicRegistration;
import com.noobs2d.tweenengine.utils.DynamicDisplayGroup;

public class Diabolic extends Alien {

    public Diabolic(SmashSmashStageCallback stage) {
	super.stage = stage;
	initAttackingState();
	initHidingState();
	initRisingState();
	initSmashedState();
	initStunnedState();
	initWaitingState();
    }

    @Override
    public int getScore() {
	return state == AlienState.ATTACKING ? 1 : state == AlienState.RISING ? 2 : 1;
    }

    @Override
    public void rise(float delay, float volume) {
	//	hitPoints = 2;
	super.rise(delay, volume);
    }

    private void initAttackingState() {
	ArrayList<DynamicDisplay> list = new ArrayList<DynamicDisplay>();
	list.add(new DynamicAnimation(AliensArt.diabolicAttack));
	attackingState = new DynamicDisplayGroup(list);
	attackingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initHidingState() {
	ArrayList<DynamicDisplay> list = new ArrayList<DynamicDisplay>();
	list.add(new DynamicAnimation(AliensArt.diabolicHiding));
	hidingState = new DynamicDisplayGroup(list);
	hidingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initRisingState() {
	ArrayList<DynamicDisplay> list = new ArrayList<DynamicDisplay>();
	list.add(new DynamicAnimation(AliensArt.diabolicRising));
	risingState = new DynamicDisplayGroup(list);
	risingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initSmashedState() {
	ArrayList<DynamicDisplay> list = new ArrayList<DynamicDisplay>();
	list.add(new DynamicAnimation(AliensArt.diabolicSmashed));
	smashedState = new DynamicDisplayGroup(list);
	smashedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initStunnedState() {
	ArrayList<DynamicDisplay> list = new ArrayList<DynamicDisplay>();
	list.add(new DynamicAnimation(AliensArt.diabolicStunned));
	stunnedState = new DynamicDisplayGroup(list);
	stunnedState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    private void initWaitingState() {
	ArrayList<DynamicDisplay> list = new ArrayList<DynamicDisplay>();
	list.add(new DynamicAnimation(AliensArt.diabolicWaiting));
	waitingState = new DynamicDisplayGroup(list);
	waitingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }
}
