package com.noobs2d.smashsmash.alien;

import com.noobs2d.smashsmash.screen.AlienEventListener;

public class Tortoise extends Alien {

    public Tortoise(AlienEventListener listener) {
	setAlienEventListener(listener);

	initAttackingState();
	initHidingState();
	initRisingState();
	initSmashedState();
	initStunnedState();
	initWaitingState();
    }

    @Override
    public int getScore() {
	return state == AlienState.RISING ? 1 : state == AlienState.WAITING || state == AlienState.ATTACKING ? 2 : 1;
    }

    @Override
    protected void initAttackingState() {
	// TODO add impl
    }

    @Override
    protected void initExplodeState() {
	// TODO add impl
    }

    @Override
    protected void initHidingState() {
	// TODO add impl
    }

    @Override
    protected void initRisingState() {
	// TODO add impl
    }

    @Override
    protected void initSmashedState() {
	// TODO add impl
    }

    @Override
    protected void initStunnedState() {
	// TODO add impl
    }

    @Override
    protected void initWaitingState() {
	// TODO add impl
    }

    @Override
    protected void interpolateAttacking() {
	// TODO Auto-generated method stub

    }

    @Override
    protected void interpolateExplode() {
	// TODO Auto-generated method stub

    }

    @Override
    protected void interpolateHiding() {
	// TODO Auto-generated method stub

    }

    @Override
    protected void interpolateRising(float delay) {
	// TODO Auto-generated method stub

    }

    @Override
    protected void interpolateSmashed(boolean dead) {
	// TODO Auto-generated method stub

    }

    @Override
    protected void interpolateStunned() {
	// TODO Auto-generated method stub

    }

    @Override
    protected void interpolateWaiting() {
	// TODO Auto-generated method stub

    }
}
