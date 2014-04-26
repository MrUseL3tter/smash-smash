package com.noobs2d.smashsmash.alien;

import com.noobs2d.smashsmash.screen.AlienEventListener;
import com.noobs2d.tweenengine.utils.DynamicDisplay.DynamicRegistration;
import com.noobs2d.tweenengine.utils.DynamicSprite;

/**
 * An alien that must only be hit while sleeping (eyes closed). 
 * Hitting it while its eyes are open will cause it to attack.
 * 
 * @author Julious Cious Igmen <jcigmen@gmail.com>
 */
public class Golem extends Alien {

    public static final String RESOURCE = "data/gfx/ALIENS.pack";
    public static final String SHAPE = "GOLEM-SHAPE";
    public static final String ATTACK_EYES = "GOLEM-ATTACK-EYES";
    public static final String ATTACK_MOUTH = "GOLEM-ATTACK-MOUTH";
    public static final String IDLE_EYES = "GOLEM-IDLE-EYES";
    public static final String IDLE_MOUTH = "GOLEM-IDLE-MOUTH";
    public static final String BLINK_EYES = "GOLEM-BLINK-EYES";
    public static final String SMASHED_MOUTH = "GOLEM-SMASHED-MOUTH";
    public static final String SMASHED_SHAPE = "GOLEM-SMASHED-SHAPE";
    public static final String STUNNED_LEFT_EYE = "GOLEM-STUNNED-LEFTEYE";
    public static final String STUNNED_RIGHT_EYE = "GOLEM-STUNNED-RIGHTEYE";
    public static final String STUNNED_SHAPE = "GOLEM-STUNNED-SHAPE";
    public static final String STUNNED_MOUTH = "GOLEM-STUNNED-MOUTH";

    public Golem(AlienEventListener stage) {
	super.callback = stage;
	initAttackingState();
	initHidingState();
	initRisingState();
	initSmashedState();
	initStunnedState();
	initWaitingState();
    }

    /** Golem doesn't have an explosion state so we just divert explode into smashed state. */
    @Override
    public void explode() {
	state = AlienState.SMASHED;
	interpolateSmashed(true);
    }

    @Override
    public int getScore() {
	return state == AlienState.RISING ? 1 : state == AlienState.ATTACKING ? 2 : 1;
    }

    @Override
    protected void initAttackingState() {
	DynamicSprite shape, eyes, mouth;
	shape = new DynamicSprite(AliensArt.golemShape, 0, 0, DynamicRegistration.BOTTOM_CENTER);
	shape.setName(SHAPE);
	eyes = new DynamicSprite(AliensArt.golemAttackEyes, 0, 143);
	eyes.setName(ATTACK_EYES);
	mouth = new DynamicSprite(AliensArt.golemAttackMouth, 0, 131);
	mouth.setName(ATTACK_MOUTH);

	attackingState.add(shape);
	attackingState.add(eyes);
	attackingState.add(mouth);
	attackingState.setRegistration(DynamicRegistration.BOTTOM_CENTER);
    }

    @Override
    protected void initExplodeState() {
	// golem doesn't have explode state
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
